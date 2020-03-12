package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 酒票总数设置，累计已发出酒票，计算剩余酒票，当酒票发完后，游戏结束
 */
@Getter
@Setter
@Table(name = "jp_config")
public class JpConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 游戏开始设置的总酒票数
    private Integer num;
    // 已发出酒票数
    private Integer fcNum;
    // 剩余酒票数
    private Integer syNum;
}
