package rh.study.knowledge.controller.jiufang;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.YkPrizeLog;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.entity.wechat.WeixinPhoneDecryptInfo;
import rh.study.knowledge.service.jiufang.FangZhuService;
import rh.study.knowledge.service.jiufang.PrizeConfigService;
import rh.study.knowledge.service.jiufang.YouKeService;
import rh.study.knowledge.util.aes.AESForWeixinGetPhoneNumber;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/wechat/common")
public class CommonController {

    public static final String appid = "wx042d9b5b159808fe";

    public static final String secret = "91135584368c0ce12474f76643279762";

    @Value("${areaCity}")
    private String areaCity;

    @Value("${areaOpen}")
    private Boolean areaOpen;

    @Autowired
    private FangZhuService fangZhuService;

    @Autowired
    private YouKeService youKeService;

    @Autowired
    private PrizeConfigService prizeConfigService;


    @GetMapping(value = "prizeConfig")
    public Result prizeConfig() {
        return prizeConfigService.list();
    }

    @PostMapping(value = "prizeLog")
    public Result prizeLog(
            @RequestBody YkPrizeLog ykPrizeLog
    ) {
        if (ykPrizeLog.getYkOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        if (ykPrizeLog.getPrizeStr() == null) {
            throw new ServiceException(400, "prizeStr必传");
        }
        return prizeConfigService.prizeLog(ykPrizeLog);
    }

    @PostMapping(value = "auth")
    public Result auth(
            @RequestBody FangZhu fangZhu
    ) {
        // 类型：1表示经销商，2：表示游客
        if (fangZhu.getTp() == null || (fangZhu.getTp().intValue() != 1 && fangZhu.getTp().intValue() != 2)) {
            throw new ServiceException(400, "tp参数错误");
        }
        if (fangZhu.getOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        //类型：1表示经销商，2：表示游客
        if (fangZhu.getTp().intValue() == 1) {
            if (fangZhu.getPhone() == null) {
                throw new ServiceException(400, "phone必传");
            }
            return fangZhuService.auth(fangZhu);
        } else {
            YouKe yk = new YouKe();
            yk.setPhone(fangZhu.getPhone());
            yk.setOpenid(fangZhu.getOpenid());
            yk.setNickName(fangZhu.getNickName());
            yk.setProvince(fangZhu.getProvince());
            yk.setCity(fangZhu.getCity());
            yk.setAvatarUrl(fangZhu.getAvatarUrl());
            yk.setGender(fangZhu.getGender());
            return youKeService.auth(yk);
        }
    }

    /**
     * 通过调用微信接口获取小程序openid
     * @param code
     * @return
     */
    @GetMapping(value = "openid")
    public Result openid(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            Result.failure(400, "参数错误");
        }
        return getOpenid(code);
    }

    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    @GetMapping(value = "getUserInfo")
    public Result getUserInfo(@RequestParam String openid) {
        if (StringUtils.isEmpty(openid)) {
            Result.failure(400, "参数错误");
        }
        return getUserInfoData(openid);
    }

    @GetMapping(value = "area")
    public Result area() {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("isOpen", false);
        resMap.put("list", null);
        if (areaCity != null) {
            resMap.put("list", Arrays.asList(areaCity.split(",")));
        }
        if (areaOpen != null) {
            resMap.put("isOpen", areaOpen);
        }
        return Result.success(resMap);
    }

    @GetMapping(value = "/getPhoneNumber")
    public Result getPhoneNumber(
            @RequestParam String sessionKey,
            @RequestParam String iv,
            @RequestParam String encryptedData) {
        try {
            AESForWeixinGetPhoneNumber aes = new AESForWeixinGetPhoneNumber(encryptedData, sessionKey, iv);
            WeixinPhoneDecryptInfo info = aes.decrypt();
            if (null == info) {
                return Result.failure(500, "获取失败");
            } else {
                if (!info.getWeixinWaterMark().getAppid().equals(appid)) {
                    return Result.failure(500, "appid error");
                }
            }
            return Result.success(info);
        } catch (Exception e) {
            return Result.failure(500, "fail");
        }
    }


    private Result getUserInfoData(String openid) {
        Map<String, Object> map = new HashMap<>();
        map.put("openid", openid);
        // 根据openid 查询该经销商是否已经微信认证
        FangZhu fangZhu = fangZhuService.queryByOpenid(openid);
        if (fangZhu == null) {
            map.put("isOwner", false);
            // 不是经销商，查询是否为游客
            YouKe youKe = youKeService.queryByOpenid(openid);
            // 是否为游客

            if (youKe != null) {
                map.put("isOwner", true);
                //1表示经销商，2：表示游客
                map.put("tp", 2);
                // 是游客则返回游客信息
                // 游客参与游戏次数
                map.put("yxNum", youKe.getYxNum());
                // 游客酒票数
                map.put("jpNum", youKe.getJpNum());
                map.put("nickName", youKe.getNickName());
                map.put("avatarUrl", youKe.getAvatarUrl());
                map.put("gender", youKe.getGender());
                map.put("freeNum", youKe.getFreeNum());
            } else {
                throw new ServiceException(500, "用户不存在");
            }
        } else {
            map.put("isOwner", true);
            //1表示经销商，2：表示游客
            map.put("tp", 1);
            // 经销商剩余酒票
            map.put("jpNum", (fangZhu.getNum() - fangZhu.getFcNum()));
            map.put("nickName", fangZhu.getNickName());
            map.put("avatarUrl", fangZhu.getAvatarUrl());
            map.put("gender", fangZhu.getGender());
        }
        return Result.success(map);

    }

    private Result getOpenid(String code) {
        // GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?appid=");
        url.append(appid);
        url.append("&secret=").append(secret)
                .append("&js_code=").append(code)
                .append("&grant_type=authorization_code");
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url.toString());
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String str = EntityUtils.toString(responseEntity);
                Map<String, Object> map = parseResponse(str);
                if (map != null && map.containsKey("openid")) {
                    // 根据openid 查询该经销商是否已经微信认证
                    String openid = MapUtils.getString(map, "openid");
                    FangZhu fangZhu = fangZhuService.queryByOpenid(openid);
                    map.put("isOwner", false);
                    if (fangZhu == null) {
                        // 不是经销商，查询是否为游客
                        YouKe youKe = youKeService.queryByOpenid(openid);
                        // 是否为游客

                        if (youKe != null) {
                            map.put("isOwner", true);
                            //1表示经销商，2：表示游客
                            map.put("tp", 2);
                            // 是游客则返回游客信息
                            // 游客参与游戏次数
                            map.put("yxNum", youKe.getYxNum());
                            // 游客酒票数
                            map.put("jpNum", youKe.getJpNum());
                            map.put("nickName", youKe.getNickName());
                            map.put("avatarUrl", youKe.getAvatarUrl());
                            map.put("gender", youKe.getGender());
                            map.put("freeNum", youKe.getFreeNum());
                        }
                    } else {
                        map.put("isOwner", true);
                        //1表示经销商，2：表示游客
                        map.put("tp", 1);
                        // 经销商剩余酒票
                        map.put("jpNum", (fangZhu.getNum() - fangZhu.getFcNum()));
                        map.put("nickName", fangZhu.getNickName());
                        map.put("avatarUrl", fangZhu.getAvatarUrl());
                        map.put("gender", fangZhu.getGender());
                    }
                }
                return Result.success(map);
            }
        } catch (Exception e) {
            return Result.failure(500, "微信接口访问异常，请稍后重试！");
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success(null);
    }

    private Map<String, Object> parseResponse(String str) throws IOException {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(str)) {
            JSONObject jsonObject = JSONObject.parseObject(str);
            if (jsonObject.containsKey("openid")) {
                map.put("openid", jsonObject.getString("openid"));
                map.put("session_key", jsonObject.getString("session_key"));
                return map;
            }
            if (jsonObject.containsKey("errcode")) {

                map.put("errcode", jsonObject.getString("errcode"));
                map.put("errmsg", jsonObject.getString("errmsg"));
                return map;
            }
        }
        return null;
    }

}
