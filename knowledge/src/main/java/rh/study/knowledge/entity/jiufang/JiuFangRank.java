package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 酒坊排行榜
 */
@Getter
@Setter
@Table(name = "jiufang_rank")
public class JiuFangRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 酒坊id
    private Integer jfId;
    private String rankJson;

    private Date createTime;
}
