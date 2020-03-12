package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 酒坊游客玩游戏的得分记录表
 */
@Getter
@Setter
@Table(name = "yx_log")
public class YxLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 酒坊、游客维度每次游戏每个游客的记录id
    private Integer jfYkLogId;
    // 得分
    private Integer score;
    // 酒坊创建人类型，1:经销商；2:游客
    private Integer yxTp;
    // 第几局
    private Integer ord;
    //
    private Date createTime;
}
