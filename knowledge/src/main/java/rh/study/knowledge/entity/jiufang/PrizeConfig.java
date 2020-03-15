package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 奖品配置
 */
@Getter
@Setter
@Table(name = "prize_config")
public class PrizeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 奖品名
    private String name;
    // 赢得酒票数
    private Integer num;
    // 图片
    private String img;
}
