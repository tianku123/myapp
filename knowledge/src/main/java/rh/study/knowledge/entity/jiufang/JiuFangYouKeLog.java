package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 酒坊游客玩游戏的记录表
 */
@Getter
@Setter
@Table(name = "jf_yk_log")
public class JiuFangYouKeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 游客id
    private Integer ykId;
    private String openid;
    // 经销商id
    private Integer jfId;
    // 游客赢得的酒票数
    private Integer jpNum;
    // 酒坊创建人类型，1:经销商；2:游客
    private Integer jfTp;
    // 游戏类型，1：筛子，2：游戏大作战
    private Integer yxTp;
    // 0:未完游戏，掉线或者自己跑了，1:玩游戏了
    private Integer online;
    // 第一局
    private Integer one;
    // 第二局
    private Integer two;
    // 第三局
    private Integer three;
    // 第几局游戏，病毒多作战只有一句，筛子有三局
    private Integer ord;
    // 分数统计
    private Integer total;

    private Date createTime;
    private Date updateTime;

    @Transient
    private Integer tp;
    @Transient
    private Integer score;
}
