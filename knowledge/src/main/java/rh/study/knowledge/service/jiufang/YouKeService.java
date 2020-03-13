package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.*;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.entity.jiufang.YkSuccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YouKeService {

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private JiuFangYouKeLogMapper jiuFangYouKeLogMapper;

    @Autowired
    private YxLogMapper yxLogMapper;

    @Autowired
    private JpConfigService jpConfigService;

    @Autowired
    private YkSuccessMapper ykSuccessMapper;

    public PageResult listPagable(int current, int pageSize, Map<String, Object> params) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Map<String, Object>> list = youKeMapper.list(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public PageResult jpRankPagable(int current, int pageSize, Map<String, Object> params) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Map<String, Object>> list = youKeMapper.jpRankPagable(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    /**
     * 游客开始游戏时调用，保存游客信息
     *
     * @param youKe
     * @return
     */
    @Transactional
    public YouKe save(YouKe youKe) {
        YouKe youKe1 = queryByOpenid(youKe.getOpenid());
        if (youKe1 == null) {
            int i = youKeMapper.insertSelective(youKe);
            if (i > 0) {
                return youKeMapper.selectOne(youKe);
            }
        }
        return youKe1;
    }
//    @Transactional
//    public Result save(YouKe youKe) {
//        /**
//         * 邀请人：经销商 或 游客，如果是经销商则增加游客数量，如果是游客则增加该游客的游戏次数
//         */
//        String shareOpenid = youKe.getShareOpenid();
//        // 查询邀请人是否为经销商,经销商必須是已經認證過得
//        FangZhu fangZhuYqr = new FangZhu();
//        fangZhuYqr.setOpenid(shareOpenid);
//        fangZhuYqr.setStat(2);// 已认证
//        fangZhuYqr = fangZhuMapper.selectOne(fangZhuYqr);
//        // 查询邀请人是否为游客
//        YouKe ykYqr = new YouKe();
//        ykYqr.setOpenid(shareOpenid);
//        ykYqr = youKeMapper.selectOne(ykYqr);
//        if (fangZhuYqr == null && ykYqr == null) {
//            return Result.failure(500, "无此邀请人");
//        }
//        // 如果邀请人是经销商
//        if (fangZhuYqr != null) {
//            // 判断该游客是否已经被邀请过
//            JiuFangYouKeLog jiuFangYouKeLog = new JiuFangYouKeLog();
//            //被邀请游客openid
//            jiuFangYouKeLog.setYkOpenid(youKe.getOpenid());
//            jiuFangYouKeLog = jiuFangYouKeLogMapper.selectOne(jiuFangYouKeLog);
//            if (jiuFangYouKeLog != null) {
//                return Result.failure(500, "已被邀请过");
//            }
//            youKe.setCreateTime(new Date());
//            // 保存游客信息
//            int i = youKeMapper.insertSelective(youKe);
//            if (i > 0) {
//                /**
//                 * 游客和经销商关系表，
//                 */
//                jiuFangYouKeLog = new JiuFangYouKeLog();
//                jiuFangYouKeLog.setFxNum(0);//默认分享次数0
//                jiuFangYouKeLog.setYxNum(5);//默认游戏次数5
//                jiuFangYouKeLog.setFzId(fangZhuYqr.getId());// 经销商id
//                jiuFangYouKeLog.setFzOpenid(fangZhuYqr.getOpenid());// 微信唯一标识
//                jiuFangYouKeLog.setJpNum(0);//默认酒票0
//                jiuFangYouKeLog.setYkId(youKe.getId());
//                jiuFangYouKeLog.setYkOpenid(youKe.getOpenid());// 微信唯一标识
//                int j = jiuFangYouKeLogMapper.insertSelective(jiuFangYouKeLog);
//                if (j > 0) {
//                    // 更新经销商的游客数
//                    fangZhuYqr.setUpdateTime(new Date());
//                    fangZhuYqr.setYkNum(fangZhuYqr.getYkNum() == null ? 0 : fangZhuYqr.getYkNum() + 1);
//                    fangZhuMapper.updateByPrimaryKeySelective(fangZhuYqr);
//                    return Result.success("创建成功");
//                } else {
//                    return Result.failure(500, "游客创建失败");
//                }
//            } else {
//                return Result.failure(500, "游客创建失败");
//            }
//        } else {// 游客邀请游客
//            // 判断该游客是否已经被邀请过
//            JiuFangYouKeLog jiuFangYouKeLog = new JiuFangYouKeLog();
//            //被邀请游客openid
//            jiuFangYouKeLog.setYkOpenid(youKe.getOpenid());
//            jiuFangYouKeLog = jiuFangYouKeLogMapper.selectOne(jiuFangYouKeLog);
//            if (jiuFangYouKeLog != null) {
//                return Result.failure(500, "已被邀请过");
//            }
//
//            // 保存游客信息
//            youKe.setCreateTime(new Date());
//            int i = youKeMapper.insertSelective(youKe);
//            if (i > 0) {
//                /**
//                 * 游客和经销商关系表，
//                 */
//                jiuFangYouKeLog = new JiuFangYouKeLog();
//                jiuFangYouKeLog.setFxNum(0);//默认分享次数0
//                jiuFangYouKeLog.setYxNum(5);//默认游戏次数5
//                jiuFangYouKeLog.setFzId(ykYqr.getFzId());// 邀请人所在酒坊id（经销商id）
//                jiuFangYouKeLog.setFzOpenid(ykYqr.getFzOpenid());// 微信唯一标识
//                jiuFangYouKeLog.setJpNum(0);//默认酒票0
//                jiuFangYouKeLog.setYkId(youKe.getId());
//                jiuFangYouKeLog.setYkOpenid(youKe.getOpenid());// 微信唯一标识
//                int j = jiuFangYouKeLogMapper.insertSelective(jiuFangYouKeLog);
//                if (j > 0) {
//                    fangZhuYqr = fangZhuMapper.selectByPrimaryKey(jiuFangYouKeLog.getFzId());
//                    // 更新经销商的游客数
//                    fangZhuYqr.setUpdateTime(new Date());
//                    fangZhuYqr.setYkNum(fangZhuYqr.getYkNum() == null ? 0 : fangZhuYqr.getYkNum() + 1);
//                    fangZhuMapper.updateByPrimaryKeySelective(fangZhuYqr);
//                    /**
//                     * 奖励游客邀请人，加游戏次数 和 酒票
//                     */
//                    ykYqr.setYxNum(ykYqr.getYxNum() + 5);
//                    // TODO 酒票应该怎么减，减谁的
//                    jiuFangYouKeLogMapper.updateByPrimaryKey(ykYqr);
//                    return Result.success("创建成功");
//                } else {
//                    return Result.failure(500, "游客创建失败");
//                }
//            } else {
//                return Result.failure(500, "游客创建失败");
//            }
//
//        }
//    }

    public YouKe queryRedisGroupById(Integer id) {
        return youKeMapper.selectByPrimaryKey(id);
    }

//    /**
//     * 游戏结束，更新酒票数，保存游戏记录
//     *
//     * @param myOpenid    挑战者：游客
//     * @param otherOpenid 被挑战者：经销商 或 游客
//     * @param success     1:挑战者赢，2：被挑战者 赢
//     * @return
//     */
////    @Transactional
//    public Result play(String myOpenid, String otherOpenid, Integer success) {
//        // 判断挑战者和被挑战者身份是否合法
////        YouKe tzz = youKeMapper.selectByPrimaryKey(myId);
//        JiuFangYouKeLog tzzNum = new JiuFangYouKeLog();
//        // 游客
//        tzzNum.setYkOpenid(myOpenid);
//        tzzNum = jiuFangYouKeLogMapper.selectOne(tzzNum);
//        if (tzzNum == null) {
//            return Result.failure(500, "挑战者不存在");
//        }
//        // 只判断挑战者游戏次数
//        if (tzzNum.getYxNum() < 1) {
//            return Result.failure(500, "游戏次数不足");
//        }
//        // 被挑战游客
////        YouKe btzyk = youKeMapper.selectByPrimaryKey(otherId);
//        JiuFangYouKeLog btzykNum = new JiuFangYouKeLog();
//        // 游客
//        btzykNum.setYkOpenid(otherOpenid);
//        btzykNum = jiuFangYouKeLogMapper.selectOne(btzykNum);
//        // 被挑战经销商
//        FangZhu btzfz = new FangZhu();
//        btzfz.setOpenid(otherOpenid);
//        btzfz = fangZhuMapper.selectByPrimaryKey(otherOpenid);
//        if (btzfz == null && btzykNum == null) {
//            return Result.failure(500, "被挑战者不存在");
//        }
//        // 挑战者第一次挑战 或者 没有酒票，只能和经销商挑战
//        if (tzzNum.getJpNum() < 1) {
//            if (btzykNum != null) {
//                return Result.failure(500, "挑战者无酒票只能挑战经销商");
//            }
//            // 被挑战者不是经销商，或者经销商没有酒票
//            if (btzfz != null && btzfz.getNum() - btzfz.getFcNum() < 1) {
//                return Result.failure(500, "被挑战经销商无酒票");
//            }
//        } else {
//            if (btzfz != null) {
//                return Result.failure(500, "挑战者有酒票不能挑战经销商");
//            }
//            if (btzykNum != null && btzykNum.getJpNum() < 1) {
//                return Result.failure(500, "被挑战者无酒票");
//            }
//        }
//
//        // 游戏挑战记录
//        YxLog yxLog = new YxLog();
//        yxLog.setTzz(tzzNum.getYkId());
//        yxLog.setCreateTime(new Date());
//        yxLog.setTzzOpenid(tzzNum.getYkOpenid());
//        yxLog.setSuccess(success);
//        if (success.intValue() == 1) {// 挑战者赢
//            if (tzzNum != null) {
//                /**
//                 * 酒票加一，游戏次数减一
//                 */
//                tzzNum.setJpNum(tzzNum.getJpNum() + 1);
//                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
//                jiuFangYouKeLogMapper.updateByPrimaryKey(tzzNum);
//            }
//            // 如果被挑战者是游客
//            if (btzykNum != null) {
//                /**
//                 * 酒票加一
//                 */
//                btzykNum.setJpNum(btzykNum.getJpNum() - 1);
////                    fangZhuYouKeRel.setYxNum(fangZhuYouKeRel.getYxNum() - 1);
//                jiuFangYouKeLogMapper.updateByPrimaryKey(btzykNum);
//            }
//            // 如果被挑战者是经销商
//            if (btzfz != null) {
//                // 经销商发出酒票加一
//                btzfz.setFcNum(btzfz.getFcNum() + 1);
//                fangZhuMapper.updateByPrimaryKeySelective(btzfz);
//            }
//        } else {// 被挑战者赢
//            // 挑战者第一次挑战 或者 没有酒票，只能和经销商挑战
//            if (tzzNum.getJpNum() < 1) {
//                // 挑战经销商失败, 游戏次数减一
//                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
//                jiuFangYouKeLogMapper.updateByPrimaryKey(tzzNum);
//            } else {
//                // 挑战游客失败，挑战者游戏次数减一、酒票数减一
//                tzzNum.setJpNum(tzzNum.getJpNum() - 1);
//                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
//                jiuFangYouKeLogMapper.updateByPrimaryKey(btzykNum);
//                // 被挑战者酒票数加一
//                btzykNum.setJpNum(btzykNum.getJpNum() + 1);
//                jiuFangYouKeLogMapper.updateByPrimaryKey(btzykNum);
//            }
//        }
//        if (btzykNum != null) {// 被挑战者为游客
//            yxLog.setBtz(btzykNum.getYkId());
//            yxLog.setBtzOpenid(btzykNum.getYkOpenid());
//
//            yxLog.setBtzType(2);
//            yxLogMapper.insert(yxLog);
//            return Result.success();
//        }
//        if (btzfz != null) {// 被挑战者是经销商
//            yxLog.setBtz(btzfz.getId());
//            yxLog.setBtzOpenid(btzfz.getOpenid());
//
//            yxLog.setBtzType(1);
//            yxLogMapper.insert(yxLog);
//            return Result.success();
//        }
//        return Result.success();
//    }

    public YouKe queryByOpenid(String openid) {
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        return youKeMapper.selectOne(youKe);
    }

    /**
     * 认证
     * @param yk
     * @return
     */
    @Transactional
    public Result auth(YouKe yk) {
        YouKe youk = new YouKe();
        youk.setOpenid(yk.getOpenid());
        youk = youKeMapper.selectOne(youk);
        if (youk != null) {
            return Result.failure(500, "游客已存在");
        }
        jpConfigService.checkJpNum(1);
        // 送一张酒票
        yk.setJpNum(1);
        yk.setYxNum(0);
        // 被邀请进入酒坊免费获取的酒票数
        yk.setFreeNum(1);
        yk.setUpdateTime(new Date());
        int i = youKeMapper.insertSelective(yk);
        if (i > 0) {
            jpConfigService.addJpNum(1);
            // 初始化我的战绩
            YkSuccess ys = new YkSuccess();
            ys.setYkOpenid(yk.getOpenid());
            ys.setNum(0);
            ys.setYxTp(1);
            ykSuccessMapper.insert(ys);
            ys = new YkSuccess();
            ys.setYkOpenid(yk.getOpenid());
            ys.setNum(0);
            ys.setYxTp(2);
            ykSuccessMapper.insert(ys);
//            youk = youKeMapper.selectByPrimaryKey(youk.getId());
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("openid", yk.getOpenid());
            resMap.put("isOwner", true);
            resMap.put("isYk", true);
            resMap.put("jpNum", yk.getJpNum());
            resMap.put("yxNum", yk.getYxNum());
            return Result.success(resMap);
        } else {
            return Result.failure(500, "修改失败");
        }
    }
}
