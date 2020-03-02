package rh.study.knowledge.entity.wechat;

public class WeixinWaterMark {
    private Long timestamp;
    private String appid;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
