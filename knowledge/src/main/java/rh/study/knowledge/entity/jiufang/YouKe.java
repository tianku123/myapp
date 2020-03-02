package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
