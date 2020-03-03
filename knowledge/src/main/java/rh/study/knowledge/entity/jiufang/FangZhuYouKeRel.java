package rh.study.knowledge.entity.jiufang;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 坊主（酒坊主）游客关联表，记录游客分享次数，剩余玩游戏数，酒票数
 */
@Getter
@Setter
@Table(name = "fz_yk_rel")
public class FangZhuYouKeRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 游客id
    private Integer ykId;
    private String ykOpenid;
    // 坊主id
    private Integer fzId;
    private String fzOpenid;
    // 游客赢得的酒票数
    private Integer jpNum;
    // 游客分享次数，分享后其他游客点击开始游戏后才会算数
    private Integer fxNum;
    // 剩余游戏次数
    private Integer yxNum;
}
