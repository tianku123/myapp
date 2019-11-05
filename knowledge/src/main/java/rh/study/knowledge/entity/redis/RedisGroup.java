package rh.study.knowledge.entity.redis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "redis_group")
public class RedisGroup {

    @Id
    private Integer id;
    private String name;
    private String intro;
    private Integer ord;
    private Integer stat;
    private Date createTime;
    private Date updateTime;
}
