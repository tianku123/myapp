package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 游客，参与游戏的用户
 */
@Getter
@Setter
@Table(name = "youke")
public class YouKe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 微信ID，单个应用中唯一
    private String openid;
    // 不同小程序或应用中都相同
    private String unionid;
    // 微信昵称
    private String nickName;
    // 微信头像
    private String avatarUrl;
    // 性别
    private String gender;
    // 省份
    private String province;
    // 城市
    private String city;
    // 手机号
    private String phone;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
    // 收货地址
    private String address;
    // 收货手机号码
    private String sh_phone;
    // 已完游戏次数
    private Integer yxNum;
    // 赢取酒票数
    private Integer jpNum;
    // 认证时免费获取一张，邀请其他游客进入酒坊每次获取一张酒票（最分享两次获取两张），总共三张免费酒票
    private Integer freeNum;

    // 非数据库字段 ===============
    // 分享者，可能是经销商 或 游客
    @Transient
    private String shareOpenid;
    // 酒坊id
    @Transient
    private Integer jfId;


}
