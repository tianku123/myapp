package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 酒坊，经销商和游客都可以创建酒坊邀请游客到酒坊玩游戏
 * 经销商创建酒坊需要设置酒坊名称、酒坊票数，邀请游客玩游戏，游客进来默认有一张酒票，通过被邀请获取酒票最多获取两张
 * 游客创建酒坊需要自己具备一张酒票才可以创建，邀请游客进来默认有一张酒票，通过被邀请获取酒票最多获取两张
 */
@Getter
@Setter
@Table(name = "jiufang")
public class JiuFang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 经销商、或游客id
    private Integer fzYkId;
    // 经销商、或游客id
    private String openid;
    // 1:经销商；2:游客
    private Integer tp;
    // 酒坊名称
    private String name;
    // 酒票数
    private Integer num;
    // 1:筛子;2:病毒大作战
    private Integer yxTp;
    // 酒坊进入的游客人数
    private Integer perNum;
    // 状态：0 准备中，1：开始，2，结束，3：已获得最终排行榜（只能获取最终排行榜一次）
    private Integer stat;

    private Date createTime;
}
