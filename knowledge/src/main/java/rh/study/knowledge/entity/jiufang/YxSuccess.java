package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 游客最终赢得酒票汇总
 */
@Getter
@Setter
@Table(name = "yx_success")
public class YxSuccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 酒坊、游客维度每次游戏每个游客的记录id
    private Integer ykId;
    private String ykOpenid;
    // 酒坊创建人类型，1:经销商；2:游客
    private Integer yxTp;
    // 赢得酒票数
    private Integer num;
}
