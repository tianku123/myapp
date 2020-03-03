package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
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
     * @param fzOpenid    坊主openid
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
     * @param myOpenid
     * @param otherOpenid
     * @param nickName
     * @param avatarUrl
     * @param gender
     * @param province
     * @param city
     * @return
     */
    @PostMapping(value = "save")
    public Result save(
            @RequestParam String myOpenid,// 坊主id 或 游客id
            @RequestParam String otherOpenid, // 被邀请游客openid
            @RequestParam(required = false) String nickName,
            @RequestParam(required = false) String avatarUrl,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city
    ) {
        YouKe youKe = new YouKe();
        youKe.setOpenid(otherOpenid);
        youKe.setNickName(nickName);
        youKe.setAvatarUrl(avatarUrl);
        youKe.setGender(gender);
        youKe.setProvince(province);
        youKe.setCity(city);
        return youKeService.save(youKe, myOpenid);
    }

    /**
     * 游戏结束，更新酒票数，保存游戏记录
     * @param myOpenid      挑战者：游客
     * @param otherOpenid   被挑战者：坊主 或 游客
     * @param success   1:挑战者赢，2：被挑战者 赢
     * @return
     */
    @PostMapping(value = "play")
    public Result play(
            @RequestParam String myOpenid, //挑战者：游客id
            @RequestParam String otherOpenid, //被挑战者：坊主id 或 游客id
            @RequestParam Integer success // 1:挑战者赢，2：被挑战者 赢
    ) {
        if (success == null || (success.intValue() != 1 && success.intValue() != 2)) {
            return Result.failure(403, "success参数错误");
        }
        if (myOpenid == null || otherOpenid == null) {
            return Result.failure(403, "参数错误");
        }
        if (myOpenid.trim().equals(otherOpenid.trim())) {
            return Result.failure(403, "挑战者与被挑战者不能相同");
        }
        return youKeService.play(myOpenid, otherOpenid, success);
    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        YouKe i = youKeService.queryRedisGroupById(id);
        return Result.success(i);
    }
}
