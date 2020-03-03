package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 游戏记录
 */
@Getter
@Setter
@Table(name = "yx_log")
public class YxLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 挑战者
    private Integer tzz;
    private String tzzOpenid;
    // 被挑战者
    private Integer btz;
    private String btzOpenid;
    // 被挑战者类别，1：坊主，2：游客
    private Integer btzType;
    // 1：挑战者赢，2：被挑战者赢
    private Integer success;
    //
    private Date createTime;
}
