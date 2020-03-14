package rh.study.knowledge.service.jiufang;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.jiufang.*;
import rh.study.knowledge.entity.jiufang.*;

import java.util.*;

@Service
public class JiuFangService {

    // 进入酒坊的人数（不包括经销商）不超过多少人
    @Value("${jfPerNum}")
    private Integer jfPerNum;

    @Autowired
    private JiuFangMapper jiuFangMapper;

    @Autowired
    private JiuFangYouKeLogMapper jiuFangYouKeLogMapper;

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private YkSuccessMapper ykSuccessMapper;

    @Autowired
    private JpConfigService jpConfigService;

    @Transactional
    public Result save(JiuFang jiuFang) {
        FangZhu fz = new FangZhu();
        fz.setOpenid(jiuFang.getOpenid());
        fz = fangZhuMapper.selectOne(fz);
        YouKe yk = new YouKe();
        yk.setOpenid(jiuFang.getOpenid());
        yk = youKeMapper.selectOne(yk);
        if (fz == null && yk == null) {
            throw new ServiceException(500, "身份不存在，无法创建酒坊");
        }
        if (fz != null) {// 判断经销商剩余酒票
            if (fz.getNum() - fz.getFcNum() < jiuFang.getNum()) {
                throw new ServiceException(500, "经销商剩余酒票不足，无法创建酒坊");
            }
            // 经销商创建房间自己不算房间人数
            jiuFang.setPerNum(0);
        } else if (yk != null) {// 判断游客剩余酒票
            if (jiuFang.getNum() != 1) {
                throw new ServiceException(500, "游客只能用一张酒票创建酒坊");
            }
            if (yk.getJpNum() < jiuFang.getNum()) {
                throw new ServiceException(500, "剩余酒票不足，无法创建酒坊");
            }
            // 游客自己创建房间自己算房间人数
            jiuFang.setPerNum(1);
        }
        int i = jiuFangMapper.insertSelective(jiuFang);
        if (i > 0) {
            if (fz != null) {// 减去经销商剩余酒票
                // 增加已发出酒票数
                fz.setFcNum(fz.getFcNum() + jiuFang.getNum());
                fangZhuMapper.updateByPrimaryKey(fz);
            } else if (yk != null) {// 减去游客剩余酒票
                // 游客减少酒票
                yk.setJpNum(yk.getJpNum() - jiuFang.getNum());
                youKeMapper.updateByPrimaryKey(yk);
                /**
                 * 游客需要进入游客列表，参与游戏分酒票
                 */
                JiuFangYouKeLog jiuFangYouKeLog = new JiuFangYouKeLog();
                jiuFangYouKeLog.setJfId(jiuFang.getId());
                jiuFangYouKeLog.setOpenid(yk.getOpenid());
                // 1:经销商；2:游客
                jiuFangYouKeLog.setJfTp(jiuFang.getTp());
                // 1:筛子;2:病毒大作战
                jiuFangYouKeLog.setYxTp(jiuFang.getYxTp());
                jiuFangYouKeLog.setCreateTime(new Date());
                jiuFangYouKeLogMapper.insertSelective(jiuFangYouKeLog);
            }
            return Result.success(jiuFang.getId());
        } else {
            return Result.failure(500, "创建失败");
        }
    }

    /**
     * 进入酒坊
     */
    @Transactional
    public Result getInto(JiuFangYouKeLog jiuFangYouKeLog) {
        JiuFang jiuFang = jiuFangMapper.selectByPrimaryKey(jiuFangYouKeLog.getJfId());
        if (jiuFang == null) {
            throw new ServiceException(500, "酒坊不存在");
        }
        int perNum = jiuFang.getPerNum();
        if (perNum >= jfPerNum) {
            throw new ServiceException(500, "酒坊人数已满");
        }
        // 酒坊创建者，1:经销商；2:游客
        int tp = jiuFang.getTp();
        YouKe yk = new YouKe();
        yk.setOpenid(jiuFangYouKeLog.getOpenid());
        yk = youKeMapper.selectOne(yk);
        if (yk == null) {
            throw new ServiceException(500, "游客未认证，不能进入酒坊");
        }
        if (yk.getJpNum() < 1) {
            throw new ServiceException(500, "游客没有酒票，不能进入酒坊");
        }
        // 1:经销商；2:游客
        jiuFangYouKeLog.setJfTp(jiuFang.getTp());
        // 1:筛子;2:病毒大作战
        jiuFangYouKeLog.setYxTp(jiuFang.getYxTp());
        jiuFangYouKeLog.setCreateTime(new Date());
        int i = jiuFangYouKeLogMapper.insertSelective(jiuFangYouKeLog);
        if (i > 0) {
            if (tp == 2) {
                // 游客进入酒坊需要扣除一张酒票
                yk.setJpNum(yk.getJpNum() - 1);
                // 游戏次数加一
                yk.setYxNum(yk.getYxNum() + 1);
                youKeMapper.updateByPrimaryKey(yk);
            }
            // 进入酒坊的人数加一
            jiuFang.setPerNum(perNum + 1);
            jiuFangMapper.updateByPrimaryKey(jiuFang);
            return Result.success(jiuFangYouKeLog.getId());
        } else {
            return Result.failure(500, "创建失败");
        }
    }

    public Result getGameStatus(Integer jfId) {
        JiuFang jiuFang = jiuFangMapper.selectByPrimaryKey(jfId);
        if (jiuFang == null) {
            throw new ServiceException(500, "酒坊不存在");
        }
        List<Map<String, Object>> ykList = jiuFangYouKeLogMapper.queryYoukeByJfId(jfId);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("status", jiuFang.getStat());
        resMap.put("createrOpenid", jiuFang.getOpenid());
        resMap.put("yxTp", jiuFang.getYxTp());
        resMap.put("tp", jiuFang.getTp());
        resMap.put("name", jiuFang.getName());
        resMap.put("perNum", jiuFang.getPerNum());
        resMap.put("list", ykList);
        return Result.success(resMap);
    }

    public Result playGame(JiuFangYouKeLog jiuFangYouKeLog) {
        JiuFang jiuFang = jiuFangMapper.selectByPrimaryKey(jiuFangYouKeLog.getJfId());
        if (jiuFang == null) {
            throw new ServiceException(500, "酒坊不存在");
        }
        List<Map<String, Object>> ykList = jiuFangYouKeLogMapper.queryYoukeByJfId(jiuFangYouKeLog.getJfId());
        if (CollectionUtils.isEmpty(ykList)) {
            throw new ServiceException(500, "必须邀请两人才可以开始游戏");
        }
        // 创建酒坊者：1:供应商；2:游客
        int tp = jiuFang.getTp();
        if (tp == 1 && ykList.size() < 2) {// 经销商不算游戏人数
            throw new ServiceException(500, "必须邀请两人才可以开始游戏");
        }
        if (tp == 2 && ykList.size() < 3) {// 游客创建房间创建者算游戏人数
            throw new ServiceException(500, "必须邀请两人才可以开始游戏");
        }

        // 1：开始，2，结束
//        int tp = jiuFangYouKeLog.getTp();
        jiuFang = new JiuFang();
        jiuFang.setId(jiuFangYouKeLog.getJfId());
        jiuFang.setStat(jiuFangYouKeLog.getTp());
        int i = jiuFangMapper.updateByPrimaryKeySelective(jiuFang);
        if (i > 0) {
            return Result.success(jiuFang.getId());
        } else {
            throw new ServiceException(500, "开始游戏失败");
        }
    }

    @Transactional
    public Result saveGameScore(JiuFangYouKeLog jiuFangYouKeLog) {
        JiuFang jiuFang = jiuFangMapper.selectByPrimaryKey(jiuFangYouKeLog.getJfId());
        if (jiuFang == null) {
            throw new ServiceException(500, "酒坊不存在");
        }
        YouKe yk = new YouKe();
        yk.setOpenid(jiuFangYouKeLog.getOpenid());
        yk = youKeMapper.selectOne(yk);
        if (yk == null) {
            throw new ServiceException(500, "游客未认证，不能进入酒坊");
        }
        JiuFangYouKeLog bean = new JiuFangYouKeLog();
        bean.setJfId(jiuFangYouKeLog.getJfId());
        bean.setOpenid(jiuFangYouKeLog.getOpenid());
        bean = jiuFangYouKeLogMapper.selectOne(bean);
        if (bean == null) {
            throw new ServiceException(500, "游客不在此酒坊");
        }
        // 1:筛子;2:病毒大作战
        int yxTp = jiuFang.getYxTp();
        // 只有筛子游戏是三局
        Integer ord = bean.getOrd();
        int result = 0;
        if (yxTp == 1) {// 筛子游戏
            if (ord == null || ord < 1) {// 第一局游戏
                bean.setOne(jiuFangYouKeLog.getScore());
                bean.setTotal(jiuFangYouKeLog.getScore());
                bean.setOrd(1);
                result = jiuFangYouKeLogMapper.updateByPrimaryKey(bean);
            } else if (ord == 1) {// 第二局
                bean.setTwo(jiuFangYouKeLog.getScore());
                bean.setTotal(jiuFangYouKeLog.getScore() + bean.getOne());
                bean.setOrd(2);
                result = jiuFangYouKeLogMapper.updateByPrimaryKey(bean);
            } else if (ord == 2) {// 第三局
                bean.setThree(jiuFangYouKeLog.getScore());
                bean.setTotal(jiuFangYouKeLog.getScore() + bean.getOne() + bean.getTwo());
                bean.setOrd(3);
                result = jiuFangYouKeLogMapper.updateByPrimaryKey(bean);
                // 结束游戏
                jiuFang.setStat(2);
                jiuFangMapper.updateByPrimaryKey(jiuFang);
            } else {
                throw new ServiceException(500, "筛子游戏最多玩三局");
            }
        } else if (yxTp == 2) {
            if (ord == null || ord < 1) {// 第一局游戏
                // 病毒大作战只需要玩一次，保持游戏结果到 one中
                bean.setOne(jiuFangYouKeLog.getScore());
                bean.setTotal(jiuFangYouKeLog.getScore());
                bean.setOrd(1);
                result = jiuFangYouKeLogMapper.updateByPrimaryKey(bean);
                // 结束游戏
                jiuFang.setStat(2);
                jiuFangMapper.updateByPrimaryKey(jiuFang);
            } else {
                throw new ServiceException(500, "病毒游戏最多玩一局");
            }
        } else {
            throw new ServiceException(500, "该游戏编码不存在");
        }
        if (result < 1) {
            throw new ServiceException(500, "上报得分失败");
        }
        return Result.success(result);
    }

    public Result getGameRank(Integer jfId) {
        JiuFang jiuFang = jiuFangMapper.selectByPrimaryKey(jfId);
        if (jiuFang == null) {
            throw new ServiceException(500, "酒坊不存在");
        }
//        if (jiuFang.getStat() != 2) {
//            throw new ServiceException(500, "酒坊内游戏未结束");
//        }
        // 创建酒坊者：1:供应商；2:游客
        int tp = jiuFang.getTp();
        // 1:筛子;2:病毒大作战
        int yxTp = jiuFang.getYxTp();
        // 酒坊内酒票数，根据规则分配这些酒票到对应游客
        int num = jiuFang.getNum();
        List<Map<String, Object>> scoreList = jiuFangYouKeLogMapper.queryScoreByJfId(jfId);
        if (CollectionUtils.isEmpty(scoreList)) {
            throw new ServiceException(500, "酒坊空空没有游客");
        }
        Map<String, Object> map = scoreList.get(0);
        // 判断游戏是否开始
        if (0 == MapUtils.getIntValue(map, "ord")) {
            throw new ServiceException(500, "游戏未开始");
        }
        if (tp == 1) {
            // 筛子游戏三局
            if (3 == MapUtils.getIntValue(map, "ord")) {
                // 计算分数
                calcScore(scoreList, jiuFang);
            } else if (2 == MapUtils.getIntValue(map, "ord")) {
                // 返回排行榜
                return Result.success(scoreList);
            } else {
                // 返回排行榜
                return Result.success(scoreList);
            }
        } else {
            // 病毒大作战游戏局
            if (1 != MapUtils.getIntValue(map, "ord")) {
                throw new ServiceException(500, "游戏未结束");
            }
        }


        /**
         * 记录每个游客玩游戏的酒票输赢记录  yx_log
         */
        return Result.success(scoreList);
    }

    private void calcScore(List<Map<String, Object>> scoreList, JiuFang jiuFang) {
        // 创建酒坊者：1:供应商；2:游客
        int tp = jiuFang.getTp();
        // 1:筛子;2:病毒大作战
        int yxTp = jiuFang.getYxTp();
        // 酒坊内酒票数，根据规则分配这些酒票到对应游客
        int num = jiuFang.getNum();
        Map<String, Object> map;
        /**
         * 游戏规则，酒票怎么分
         * 1、酒票数 按 631 分为三份，向下取整，多余酒票单独保存
         */
        int one = num * 6 / 10;
        int two = num * 3 / 10;
        int three = num * 1 / 10;
        int syNum = num - one - two - three;
        // 至少两个游客
        if (scoreList.size() == 1) {

        } else if (scoreList.size() == 2) {
            // 两个游客赢者通吃
            for (int i = 0; i < scoreList.size(); i++) {
                map = scoreList.get(i);
                map.put("jpNum", 0);
                if (i == 0) {
                    map.put("jpNum", num);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, num, yxTp);
                } else {
                    // 经销商开的房间游客不可能输，所以不需要更新战绩
                    // 只有游客开的房间，游客才可能输，需要更新战绩
                    if (tp == 2) {
                        // 更新战绩
                        updateZjByYoukeOpenid(map, -1, yxTp);
                    }
                }
            }
        } else if (scoreList.size() == 3) {
            // 三个游客赢者，第一名吃掉剩余票
            for (int i = 0; i < scoreList.size(); i++) {
                map = scoreList.get(i);
                map.put("jpNum", 0);
                if (i == 0) {
                    map.put("jpNum", one + syNum);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, one + syNum, yxTp);
                } else if (i == 1) {
                    map.put("jpNum", two);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, two, yxTp);
                } else if (i == 2) {
                    map.put("jpNum", three);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, three, yxTp);
                } else {
                    // 经销商开的房间游客不可能输，所以不需要更新战绩
                    // 只有游客开的房间，游客才可能输，需要更新战绩
                    if (tp == 2) {
                        // 更新战绩
                        updateZjByYoukeOpenid(map, -1, yxTp);
                    }
                }
            }
        } else {
            // 大于三个游客 631比例分，第一名吃掉剩余票
            for (int i = 0; i < scoreList.size(); i++) {
                map = scoreList.get(i);
                map.put("jpNum", 0);
                if (i == 0) {
                    map.put("jpNum", one + syNum);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, one + syNum, yxTp);
                } else if (i == 1) {
                    map.put("jpNum", two);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, two, yxTp);
                } else if (i == 2) {
                    map.put("jpNum", three);
                    // 更新游客票数及战绩
                    updateScoreByYoukeOpenid(map, three, yxTp);
                } else {
                    // 经销商开的房间游客不可能输，所以不需要更新战绩
                    // 只有游客开的房间，游客才可能输，需要更新战绩
                    if (tp == 2) {
                        // 更新战绩
                        updateZjByYoukeOpenid(map, -1, yxTp);
                    }
                }
            }
        }
    }

    /**
     * 更新游客酒票及战绩
     *
     * @param map
     * @param num
     * @param yxTp // 1:筛子;2:病毒大作战
     */
    private void updateScoreByYoukeOpenid(Map<String, Object> map, int num, int yxTp) {
        String openid = MapUtils.getString(map, "openid");
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        youKe = youKeMapper.selectOne(youKe);
        if (youKe != null) {
            youKe.setJpNum(youKe.getJpNum() + num);
            // 更新游客赢得酒票
            youKeMapper.updateByPrimaryKey(youKe);
            // 更新游客战绩
            YkSuccess ykSuccess = new YkSuccess();
            ykSuccess.setYkOpenid(openid);
            ykSuccess.setYxTp(yxTp);
            ykSuccess = ykSuccessMapper.selectOne(ykSuccess);
            if (ykSuccess != null) {
                ykSuccess.setNum(ykSuccess.getNum() + num);
                ykSuccessMapper.updateByPrimaryKey(ykSuccess);
            }
        }
    }

    /**
     * 仅更新战绩
     *
     * @param map
     * @param num
     * @param yxTp // 1:筛子;2:病毒大作战
     */
    private void updateZjByYoukeOpenid(Map<String, Object> map, int num, int yxTp) {
        String openid = MapUtils.getString(map, "openid");
        // 更新游客战绩
        YkSuccess ykSuccess = new YkSuccess();
        ykSuccess.setYkOpenid(openid);
        ykSuccess.setYxTp(yxTp);
        ykSuccess = ykSuccessMapper.selectOne(ykSuccess);
        if (ykSuccess != null) {
            ykSuccess.setNum(ykSuccess.getNum() + num);
            ykSuccessMapper.updateByPrimaryKey(ykSuccess);
        }
    }


    /**
     * 分享接口
     *
     * @param shareOpenid 分享者，如果分享者是游客，则分享者增加最多两张票
     * @param openid      游客
     * @return
     */
    public Result share(String shareOpenid, String openid) {
        YouKe shareYk = new YouKe();
        shareYk.setOpenid(shareOpenid);
        shareYk = youKeMapper.selectOne(shareYk);
        if (shareYk == null) {
            throw new ServiceException(500, "分享者不存在");
        }
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        youKe = youKeMapper.selectOne(youKe);
        if (youKe == null) {
            throw new ServiceException(500, "被分享游客不存在");
        }
        /**
         * 判断总票数剩余
         */
        jpConfigService.checkJpNum(1);
        // 认证时免费获取一张，邀请其他游客进入酒坊每次获取一张酒票（最分享两次获取两张），总共三张免费酒票
        if (shareYk.getFreeNum() < 3) {
            // 免费获取次数加一
            shareYk.setFreeNum(shareYk.getFreeNum() + 1);
            shareYk.setJpNum(shareYk.getJpNum() + 1);
            int i = youKeMapper.updateByPrimaryKey(shareYk);
            jpConfigService.addJpNum(1);
            return Result.success(i);
        } else {
            // 获取免费酒票 0张，已经超过了所以0
            return Result.success(0);
        }
    }

    public Result myRecord(String openid) {
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        youKe = youKeMapper.selectOne(youKe);
        if (youKe == null) {
            throw new ServiceException(500, "游客不存在");
        }
        YkSuccess yl = new YkSuccess();
        yl.setYkOpenid(openid);
        List<YkSuccess> list = ykSuccessMapper.select(yl);
        List<Map<String, Object>> resList = new ArrayList<>();
        Map<String, Object> map;
        if (CollectionUtils.isNotEmpty(list)) {
            for (YkSuccess ykSuccess : list) {
                map = new HashMap<>();
                map.put("tp", ykSuccess.getYxTp());
                map.put("jpNum", ykSuccess.getNum());
            }
        }
        return Result.success(resList);
    }
}
