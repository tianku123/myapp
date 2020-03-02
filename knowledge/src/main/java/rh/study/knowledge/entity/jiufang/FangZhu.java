package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 坊主（酒坊主），拥有一定量的酒票，负责分享小程序游戏，邀请游客参与并分发酒票
 */
@Getter
@Setter
@Table(name = "fangzhu")
public class FangZhu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 微信ID，单个应用中唯一
    private String openid;
    // 不同小程序或应用中都相同
    private String unionid;
    // 微信昵称
//    @Column(name = "nick_name")
    private String nickName;
    // 微信头像
//    @Column(name = "avatar_url")
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
//    @Column(name = "c_time")
    private Date createTime;
    // 更新时间
//    @Column(name = "u_time")
    private Date updateTime;
    // 酒坊名称
    private String name;
    // 总酒票数
    private Integer num;
    // 已发出酒票数
//    @Column(name = "fc_num")
    private Integer fcNum;
    // 邀请的游客数，已经玩了游戏的才算数
//    @Column(name = "p_num")
    private Integer ykNum;
    // 状态：0删除，1创建状态，2微信认证过
    private Integer stat;
}
