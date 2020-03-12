package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.service.jiufang.YouKeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/wechat/yk")
public class YouKeController {

    @Autowired
    private YouKeService youKeService;

    @GetMapping(value = "list")
    public Result list(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String nickName
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", nickName);
        PageResult pageResult = youKeService.listPagable(current, pageSize, params);
        return Result.success(pageResult);
    }

    /**
     * 酒坊下游客根据酒票倒序排行
     * @param current
     * @param pageSize
     * @param fzOpenid    经销商openid
     * @return
     */
    @GetMapping(value = "jpRank")
    public Result jpRank(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String fzOpenid
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("fzOpenid", fzOpenid);
        PageResult pageResult = youKeService.jpRankPagable(current, pageSize, params);
        return Result.success(pageResult);
    }

    /**
     * 新增游客，初始化微信信息，初始化游戏数
     * @return
     */
//    @PostMapping(value = "save")
//    public Result save(
//            @RequestBody YouKe youKe
//    ) {
//        if (youKe.getOpenid() == null) {
//            throw new ServiceException(403, "openid必传");
//        }
//        if (youKe.getShareOpenid() == null) {
//            throw new ServiceException(403, "shareOpenid必传");
//        }
//        return youKeService.save(youKe);
//    }

//    /**
//     * 游戏结束，更新酒票数，保存游戏记录
//     * @param myOpenid      挑战者：游客
//     * @param otherOpenid   被挑战者：经销商 或 游客
//     * @param success   1:挑战者赢，2：被挑战者 赢
//     * @return
//     */
//    @PostMapping(value = "play")
//    public Result play(
//            @RequestParam String myOpenid, //挑战者：游客id
//            @RequestParam String otherOpenid, //被挑战者：经销商id 或 游客id
//            @RequestParam Integer success // 1:挑战者赢，2：被挑战者 赢
//    ) {
//        if (success == null || (success.intValue() != 1 && success.intValue() != 2)) {
//            return Result.failure(403, "success参数错误");
//        }
//        if (myOpenid == null || otherOpenid == null) {
//            return Result.failure(403, "参数错误");
//        }
//        if (myOpenid.trim().equals(otherOpenid.trim())) {
//            return Result.failure(403, "挑战者与被挑战者不能相同");
//        }
//        return youKeService.play(myOpenid, otherOpenid, success);
//    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        YouKe i = youKeService.queryRedisGroupById(id);
        return Result.success(i);
    }
}
