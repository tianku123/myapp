package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 游客兑奖记录
 */
@Getter
@Setter
@Table(name = "yk_prize_log")
public class YkPrizeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 酒坊、游客维度每次游戏每个游客的记录id
    private Integer ykId;
    private String ykOpenid;
    // 奖品配置表
    private Integer prizeConfigId;
    // 赢得酒票数
    private Integer num;

    private Date createTime;

    @Transient
    private String prizeStr;
}
