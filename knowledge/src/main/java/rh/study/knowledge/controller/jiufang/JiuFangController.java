package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.JiuFang;
import rh.study.knowledge.entity.jiufang.JiuFangYouKeLog;
import rh.study.knowledge.service.jiufang.FangZhuService;
import rh.study.knowledge.service.jiufang.JiuFangService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/wechat/jf")
public class JiuFangController {

    @Autowired
    private JiuFangService jiuFangService;


    /**
     * 创建酒坊，游客和经销商都可以创建酒坊
     * @param jiuFang
     * @return
     */
    @PostMapping(value = "save")
    public Result save(
            @RequestBody JiuFang jiuFang
    ) {
        if (jiuFang.getNum().intValue() < 1) {
            throw new ServiceException(400, "无酒票");
        }
        // 类型：1表示经销商，2：表示游客
        if (jiuFang.getTp() == null || (jiuFang.getTp().intValue() != 1 && jiuFang.getTp().intValue() != 2)) {
            throw new ServiceException(400, "tp参数错误");
        }
        // 1:筛子;2:病毒大作战
        if (jiuFang.getYxTp() == null || (jiuFang.getYxTp().intValue() != 1 && jiuFang.getYxTp().intValue() != 2)) {
            throw new ServiceException(400, "tp参数错误");
        }
        if (jiuFang.getOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        if (jiuFang.getName() == null) {
            throw new ServiceException(400, "name必传");
        }
        jiuFang.setCreateTime(new Date());
        return jiuFangService.save(jiuFang);
    }

    /**
     * 进入酒坊
     * @param jiuFangYouKeLog
     * @return
     */
    @PostMapping(value = "getInto")
    public Result getInto(
            @RequestBody JiuFangYouKeLog jiuFangYouKeLog
    ) {

        if (jiuFangYouKeLog.getOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        return jiuFangService.getInto(jiuFangYouKeLog);
    }

    /**
     * 开始游戏
     * @param jiuFangYouKeLog
     * @return
     */
    @PostMapping(value = "playGame")
    public Result playGame(
            @RequestBody JiuFangYouKeLog jiuFangYouKeLog
    ) {
        if (jiuFangYouKeLog.getJfId() == null) {
            throw new ServiceException(400, "jfId必传");
        }
        // 1：开始，2，结束
        if (jiuFangYouKeLog.getTp() == null || (jiuFangYouKeLog.getTp().intValue() != 1)) {
            throw new ServiceException(400, "tp参数错误");
        }
        return jiuFangService.playGame(jiuFangYouKeLog);
    }

    /**
     * 获取游戏状态及酒坊内游客列表
     * @param jfId
     * @return
     */
    @GetMapping(value = "getGameStatus")
    public Result getGameStatus(
            @RequestParam Integer jfId
    ) {
        if (jfId == null) {
            throw new ServiceException(400, "jfId必传");
        }
        return jiuFangService.getGameStatus(jfId);
    }

    /**
     * 游客上报游戏结果接口
     * @param jiuFangYouKeLog
     * @return
     */
    @PostMapping(value = "saveGameScore")
    public Result saveGameScore(
            @RequestBody JiuFangYouKeLog jiuFangYouKeLog
    ) {
        if (jiuFangYouKeLog.getScore() == null || jiuFangYouKeLog.getScore() < 0) {
            throw new ServiceException(400, "score必传");
        }
        if (jiuFangYouKeLog.getOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        if (jiuFangYouKeLog.getJfId() == null) {
            throw new ServiceException(400, "jfId必传");
        }
        return jiuFangService.saveGameScore(jiuFangYouKeLog);
    }

    /**
     * 酒坊游客得分排行榜接口
     * @param jfId
     * @return
     */
    @GetMapping(value = "getGameRank")
    public Result getGameRank(
            @RequestParam Integer jfId
    ) {
        if (jfId == null) {
            throw new ServiceException(400, "jfId必传");
        }
        return jiuFangService.getGameRank(jfId);
    }

    /**
     * 分享接口
     * @param shareOpenid   分享者，如果分享者是游客，则分享者增加最多两张票
     * @param openid        游客
     * @return
     */
    @GetMapping(value = "share")
    public Result share(
            @RequestParam String shareOpenid,
            @RequestParam String openid
    ) {
        if (shareOpenid == null) {
            throw new ServiceException(400, "shareOpenid必传");
        }
        if (openid == null) {
            throw new ServiceException(400, "openid必传");
        }
        return jiuFangService.share(shareOpenid, openid);
    }

    /**
     * 我的战绩
     * @param openid        游客
     * @return
     */
    @GetMapping(value = "myRecord")
    public Result myRecord(
            @RequestParam String openid
    ) {
        if (openid == null) {
            throw new ServiceException(400, "openid必传");
        }
        return jiuFangService.myRecord(openid);
    }

}
