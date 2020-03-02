package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.service.jiufang.YouKeService;

@RestController
@RequestMapping(value = "/api/wechat/yk")
public class YouKeController {

    @Autowired
    private YouKeService youKeService;

    @GetMapping(value = "list")
    public Result list(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize) {
        PageResult pageResult = youKeService.listPagable(current, pageSize);
        return Result.success(pageResult);
    }

    @PostMapping(value = "save")
    public Result save(
            @RequestParam String openid,
            @RequestParam String nickName,
            @RequestParam String avatarUrl,
            @RequestParam String gender,
            @RequestParam String province,
            @RequestParam String city,
            @RequestParam String phone,
            @RequestParam Integer fzId //坊主id
    ) {
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        youKe.setNickName(nickName);
        youKe.setAvatarUrl(avatarUrl);
        youKe.setGender(gender);
        youKe.setProvince(province);
        youKe.setCity(city);
        youKe.setPhone(phone);
        return youKeService.save(youKe, fzId);
    }

//    @PostMapping(value = "update")
//    public Result update(
//            @RequestParam Integer id, //游客id
//            @RequestParam String openid,
//            @RequestParam String nickName,
//            @RequestParam String avatarUrl,
//            @RequestParam String gender,
//            @RequestParam String province,
//            @RequestParam String city,
//            @RequestParam String phone,
//            @RequestParam Integer fzId //坊主id
//    ) {
//        YouKe youKe = new YouKe();
//        youKe.setId(id);
//        youKe.setOpenid(openid);
//        youKe.setNickName(nickName);
//        youKe.setAvatarUrl(avatarUrl);
//        youKe.setGender(gender);
//        youKe.setProvince(province);
//        youKe.setCity(city);
//        youKe.setPhone(phone);
//        return youKeService.update(youKe, fzId);
//    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        YouKe i = youKeService.queryRedisGroupById(id);
        return Result.success(i);
    }
}
