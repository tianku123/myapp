package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.FangZhuMapper;
import rh.study.knowledge.dao.jiufang.FangZhuYouKeRelMapper;
import rh.study.knowledge.dao.jiufang.YouKeMapper;
import rh.study.knowledge.dao.jiufang.YxLogMapper;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.FangZhuYouKeRel;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.entity.jiufang.YxLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class YouKeService {

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private FangZhuYouKeRelMapper fangZhuYouKeRelMapper;

    @Autowired
    private YxLogMapper yxLogMapper;

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
     * @param myOpenid 邀请人：坊主 或 游客，如果是坊主则增加游客数量，如果是游客则增加该游客的游戏次数
     * @return
     */
    @Transactional
    public Result save(YouKe youKe, String myOpenid) {
        try {
            // 查询邀请人是否为坊主
            FangZhu fangZhuYqr = new FangZhu();
            fangZhuYqr.setOpenid(myOpenid);
            fangZhuYqr = fangZhuMapper.selectOne(fangZhuYqr);
            // 查询邀请人是否为游客
            FangZhuYouKeRel ykYqr = new FangZhuYouKeRel();
            ykYqr.setYkOpenid(myOpenid);
            ykYqr = fangZhuYouKeRelMapper.selectOne(ykYqr);
            if (fangZhuYqr == null && ykYqr == null) {
                return Result.failure(500, "邀请人信息错误");
            }
            // 如果邀请人是坊主
            if (fangZhuYqr != null) {
                // 判断该游客是否已经被邀请过
                FangZhuYouKeRel fangZhuYouKeRel = new FangZhuYouKeRel();
//                fangZhuYouKeRel.setFzId(fangZhu.getId());
//                fangZhuYouKeRel.setFzOpenid(fangZhu.getOpenid());
                //被邀请游客openid
                fangZhuYouKeRel.setYkOpenid(youKe.getOpenid());
                fangZhuYouKeRel = fangZhuYouKeRelMapper.selectOne(fangZhuYouKeRel);
                if (fangZhuYouKeRel != null) {
                    return Result.failure(500, "已被邀请过");
                }
                youKe.setCreateTime(new Date());
                // 保存游客信息
                int i = youKeMapper.insertSelective(youKe);
                if (i > 0) {
                    /**
                     * 游客和坊主关系表，
                     */
                    fangZhuYouKeRel = new FangZhuYouKeRel();
                    fangZhuYouKeRel.setFxNum(0);//默认分享次数0
                    fangZhuYouKeRel.setYxNum(5);//默认游戏次数5
                    fangZhuYouKeRel.setFzId(fangZhuYqr.getId());// 坊主id
                    fangZhuYouKeRel.setFzOpenid(fangZhuYqr.getOpenid());// 微信唯一标识
                    fangZhuYouKeRel.setJpNum(0);//默认酒票0
                    fangZhuYouKeRel.setYkId(youKe.getId());
                    fangZhuYouKeRel.setYkOpenid(youKe.getOpenid());// 微信唯一标识
                    int j = fangZhuYouKeRelMapper.insertSelective(fangZhuYouKeRel);
                    if (j > 0) {
                        // 更新坊主的游客数
                        fangZhuYqr.setUpdateTime(new Date());
                        fangZhuYqr.setYkNum(fangZhuYqr.getYkNum() + 1);
                        fangZhuMapper.updateByPrimaryKeySelective(fangZhuYqr);
                        return Result.success("创建成功");
                    } else {
                        if (youKe.getId() != null) {
                            youKeMapper.deleteByPrimaryKey(youKe.getId());
                        }
                        if (fangZhuYouKeRel.getId() != null) {
                            fangZhuYouKeRelMapper.deleteByPrimaryKey(fangZhuYouKeRel.getId());
                        }
                        return Result.failure(500, "游客创建失败");
                    }
                } else {
                    if (youKe.getId() != null) {
                        youKeMapper.deleteByPrimaryKey(youKe.getId());
                    }
                    return Result.failure(500, "游客创建失败");
                }
            } else {// 游客邀请游客
                // 判断该游客是否已经被邀请过
                FangZhuYouKeRel fangZhuYouKeRel = new FangZhuYouKeRel();
                //被邀请游客openid
                fangZhuYouKeRel.setYkOpenid(youKe.getOpenid());
                fangZhuYouKeRel = fangZhuYouKeRelMapper.selectOne(fangZhuYouKeRel);
                if (fangZhuYouKeRel != null) {
                    return Result.failure(500, "已被邀请过");
                }

                // 保存游客信息
                youKe.setCreateTime(new Date());
                int i = youKeMapper.insertSelective(youKe);
                if (i > 0) {
                    /**
                     * 游客和坊主关系表，
                     */
                    fangZhuYouKeRel = new FangZhuYouKeRel();
                    fangZhuYouKeRel.setFxNum(0);//默认分享次数0
                    fangZhuYouKeRel.setYxNum(5);//默认游戏次数5
                    fangZhuYouKeRel.setFzId(ykYqr.getFzId());// 邀请人所在酒坊id（坊主id）
                    fangZhuYouKeRel.setFzOpenid(ykYqr.getFzOpenid());// 微信唯一标识
                    fangZhuYouKeRel.setJpNum(0);//默认酒票0
                    fangZhuYouKeRel.setYkId(youKe.getId());
                    fangZhuYouKeRel.setYkOpenid(youKe.getOpenid());// 微信唯一标识
                    int j = fangZhuYouKeRelMapper.insertSelective(fangZhuYouKeRel);
                    if (j > 0) {
                        fangZhuYqr = fangZhuMapper.selectByPrimaryKey(fangZhuYouKeRel.getFzId());
                        // 更新坊主的游客数
                        fangZhuYqr.setUpdateTime(new Date());
                        fangZhuYqr.setYkNum(fangZhuYqr.getYkNum() + 1);
                        fangZhuMapper.updateByPrimaryKeySelective(fangZhuYqr);
                        /**
                         * 奖励游客邀请人，加游戏次数 和 酒票
                         */
                        ykYqr.setYxNum(ykYqr.getYxNum() + 5);
                        // TODO 酒票应该怎么减，减谁的
                        fangZhuYouKeRelMapper.updateByPrimaryKey(ykYqr);
                        return Result.success("创建成功");
                    } else {
                        if (youKe.getId() != null) {
                            youKeMapper.deleteByPrimaryKey(youKe.getId());
                        }
                        if (fangZhuYouKeRel.getId() != null) {
                            fangZhuYouKeRelMapper.deleteByPrimaryKey(fangZhuYouKeRel.getId());
                        }
                        return Result.failure(500, "游客创建失败");
                    }
                } else {
                    if (youKe.getId() != null) {
                        youKeMapper.deleteByPrimaryKey(youKe.getId());
                    }
                    return Result.failure(500, "游客创建失败");
                }

            }
        } catch (Exception e) {
            return Result.failure(500, "创建失败");
        }
    }

    public YouKe queryRedisGroupById(Integer id) {
        return youKeMapper.selectByPrimaryKey(id);
    }

    /**
     * 游戏结束，更新酒票数，保存游戏记录
     *
     * @param myOpenid    挑战者：游客
     * @param otherOpenid 被挑战者：坊主 或 游客
     * @param success     1:挑战者赢，2：被挑战者 赢
     * @return
     */
    @Transactional
    public Result play(String myOpenid, String otherOpenid, Integer success) {
        // 判断挑战者和被挑战者身份是否合法
//        YouKe tzz = youKeMapper.selectByPrimaryKey(myId);
        FangZhuYouKeRel tzzNum = new FangZhuYouKeRel();
        // 游客
        tzzNum.setYkOpenid(myOpenid);
        tzzNum = fangZhuYouKeRelMapper.selectOne(tzzNum);
        if (tzzNum == null) {
            return Result.failure(500, "挑战者不存在");
        }
        // 只判断挑战者游戏次数
        if (tzzNum.getYxNum() < 1) {
            return Result.failure(500, "游戏次数不足");
        }
        // 被挑战游客
//        YouKe btzyk = youKeMapper.selectByPrimaryKey(otherId);
        FangZhuYouKeRel btzykNum = new FangZhuYouKeRel();
        // 游客
        btzykNum.setYkOpenid(otherOpenid);
        btzykNum = fangZhuYouKeRelMapper.selectOne(btzykNum);
        // 被挑战坊主
        FangZhu btzfz = new FangZhu();
        btzfz.setOpenid(otherOpenid);
        btzfz = fangZhuMapper.selectByPrimaryKey(otherOpenid);
        if (btzfz == null && btzykNum == null) {
            return Result.failure(500, "被挑战者不存在");
        }
        // 挑战者第一次挑战 或者 没有酒票，只能和坊主挑战
        if (tzzNum.getJpNum() < 1) {
            if (btzykNum != null) {
                return Result.failure(500, "挑战者无酒票只能挑战坊主");
            }
            // 被挑战者不是坊主，或者坊主没有酒票
            if (btzfz != null && btzfz.getNum() - btzfz.getFcNum() < 1) {
                return Result.failure(500, "被挑战坊主无酒票");
            }
        } else {
            if (btzfz != null) {
                return Result.failure(500, "挑战者有酒票不能挑战坊主");
            }
            if (btzykNum != null && btzykNum.getJpNum() < 1) {
                return Result.failure(500, "被挑战者无酒票");
            }
        }

        // 游戏挑战记录
        YxLog yxLog = new YxLog();
        yxLog.setTzz(tzzNum.getYkId());
        yxLog.setCreateTime(new Date());
        yxLog.setTzzOpenid(tzzNum.getYkOpenid());
        yxLog.setSuccess(success);
        if (success.intValue() == 1) {// 挑战者赢
            if (tzzNum != null) {
                /**
                 * 酒票加一，游戏次数减一
                 */
                tzzNum.setJpNum(tzzNum.getJpNum() + 1);
                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
                fangZhuYouKeRelMapper.updateByPrimaryKey(tzzNum);
            }
            // 如果被挑战者是游客
            if (btzykNum != null) {
                /**
                 * 酒票加一
                 */
                btzykNum.setJpNum(btzykNum.getJpNum() - 1);
//                    fangZhuYouKeRel.setYxNum(fangZhuYouKeRel.getYxNum() - 1);
                fangZhuYouKeRelMapper.updateByPrimaryKey(btzykNum);
            }
            // 如果被挑战者是坊主
            if (btzfz != null) {
                // 坊主发出酒票加一
                btzfz.setFcNum(btzfz.getFcNum() + 1);
                fangZhuMapper.updateByPrimaryKeySelective(btzfz);
            }
        } else {// 被挑战者赢
            // 挑战者第一次挑战 或者 没有酒票，只能和坊主挑战
            if (tzzNum.getJpNum() < 1) {
                // 挑战坊主失败, 游戏次数减一
                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
                fangZhuYouKeRelMapper.updateByPrimaryKey(tzzNum);
            } else {
                // 挑战游客失败，挑战者游戏次数减一、酒票数减一
                tzzNum.setJpNum(tzzNum.getJpNum() - 1);
                tzzNum.setYxNum(tzzNum.getYxNum() - 1);
                fangZhuYouKeRelMapper.updateByPrimaryKey(btzykNum);
                // 被挑战者酒票数加一
                btzykNum.setJpNum(btzykNum.getJpNum() + 1);
                fangZhuYouKeRelMapper.updateByPrimaryKey(btzykNum);
            }
        }
        if (btzykNum != null) {// 被挑战者为游客
            yxLog.setBtz(btzykNum.getYkId());
            yxLog.setBtzOpenid(btzykNum.getYkOpenid());

            yxLog.setBtzType(2);
            yxLogMapper.insert(yxLog);
            return Result.success();
        }
        if (btzfz != null) {// 被挑战者是坊主
            yxLog.setBtz(btzfz.getId());
            yxLog.setBtzOpenid(btzfz.getOpenid());

            yxLog.setBtzType(1);
            yxLogMapper.insert(yxLog);
            return Result.success();
        }
        return Result.success();
    }
}
