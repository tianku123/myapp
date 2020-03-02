package rh.study.knowledge.controller.jiufang;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rh.study.knowledge.common.result.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/wechat/common")
public class CommonController {

    @GetMapping(value = "openid")
    public Result openid(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            Result.failure(304, "参数错误");
        }
        return getOpenid(code);
    }

    private Result getOpenid(String code) {
        // GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/jscode2session?appid=");
        final String appid = "wx042d9b5b159808fe";
        url.append(appid);
        final String secret = "91135584368c0ce12474f76643279762";
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
                Map<String, Object> openid = parseResponse(str);
                return Result.success(openid);
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
