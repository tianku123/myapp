package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.wechat.WeixinPhoneDecryptInfo;
import rh.study.knowledge.service.jiufang.FangZhuService;
import rh.study.knowledge.util.aes.AESForWeixinGetPhoneNumber;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/wechat/fz")
public class FangZhuController {

    @Autowired
    private FangZhuService fangZhuService;

    @GetMapping(value = "list")
    public Result list(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) String phone,
                       @RequestParam(required = false) Integer stat
                       ) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("phone", phone);
        params.put("stat", stat);
        PageResult pageResult = fangZhuService.listPagable(current, pageSize, params);
        return Result.success(pageResult);
    }

    @PostMapping(value = "save")
    public Result save(
            @RequestParam String name,// 酒坊名称
            @RequestParam String phone, // 坊主手机号码
            @RequestParam Integer num // 票数
    ) {
        FangZhu fangZhu = new FangZhu();
        fangZhu.setName(name);
        fangZhu.setPhone(phone);
        fangZhu.setNum(num);
        // 状态：0删除，1创建状态，2微信认证过
        fangZhu.setStat(1);
        fangZhu.setCreateTime(new Date());
        return fangZhuService.save(fangZhu);
    }

    @PostMapping(value = "update")
    public Result update(@RequestParam Integer id, @RequestParam String name,// 酒坊名称
                         @RequestParam Integer num // 票数
    ) {
        FangZhu fangZhu = new FangZhu();
        fangZhu.setId(id);
        fangZhu.setName(name);
        fangZhu.setNum(num);
        fangZhu.setUpdateTime(new Date());
        return fangZhuService.update(fangZhu);
    }

    @PostMapping(value = "auth")
    public Result update(@RequestParam String sessionKey,
                         @RequestParam String iv,
                         @RequestParam String encryptedData,
                         @RequestParam String openid,// 用户微信唯一标识
                         // 微信昵称
                         @RequestParam(required = false) String nickName,
                         // 微信头像
                         @RequestParam(required = false) String avatarUrl,
                         // 性别
                         @RequestParam(required = false) String gender,
                         // 省份
                         @RequestParam(required = false) String province,
                         // 城市
                         @RequestParam(required = false) String city
    ) {
        AESForWeixinGetPhoneNumber aes=new AESForWeixinGetPhoneNumber(encryptedData,sessionKey,iv);
        WeixinPhoneDecryptInfo info=aes.decrypt();
        if (null==info){
            return Result.failure(500, "获取手机号码失败");
        }else {
            if (!info.getWeixinWaterMark().getAppid().equals(CommonController.appid)){
                return Result.failure(500, "获取手机号码失败,appid不匹配");
            }
        }
        String phone = info.getPhoneNumber();
        FangZhu fangZhu = new FangZhu();
        fangZhu.setPhone(phone);
        fangZhu.setOpenid(openid);
        fangZhu.setNickName(nickName);
        fangZhu.setAvatarUrl(avatarUrl);
        fangZhu.setGender(gender);
        fangZhu.setProvince(province);
        fangZhu.setCity(city);
        return fangZhuService.update(fangZhu);
    }

    @PostMapping(value = "delete")
    public Result delete(@RequestParam Integer id) {
        return fangZhuService.delete(id);
    }

    @GetMapping(value = "queryById")
    public Result queryById(@RequestParam Integer id) {
        FangZhu i = fangZhuService.queryById(id);
        return Result.success(i);
    }
}
