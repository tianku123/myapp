package rh.study.knowledge.entity.wechat;

import com.alibaba.fastjson.JSON;

/**
 * 解密手机号码信息，最终返回格式
 * {
         "phoneNumber": "13580006666",
         "purePhoneNumber": "13580006666",
         "countryCode": "86",
         "watermark":
         {
         "appid":"APPID",
         "timestamp": TIMESTAMP
         }
     }
 */
public class WeixinPhoneDecryptInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private int countryCode;
    private String watermark;
    private WeixinWaterMark weixinWaterMark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
        this.weixinWaterMark = JSON.toJavaObject(JSON.parseObject(this.watermark),WeixinWaterMark.class);
    }

    public WeixinWaterMark getWeixinWaterMark(){
        return weixinWaterMark;
    }

    @Override
    public String toString() {
        return "WeixinPhoneDecryptInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", purePhoneNumber='" + purePhoneNumber + '\'' +
                ", countryCode=" + countryCode +
                ", appid=" + weixinWaterMark.getAppid() +
                ", timestamp=" + weixinWaterMark.getTimestamp() +
                '}';
    }
}
