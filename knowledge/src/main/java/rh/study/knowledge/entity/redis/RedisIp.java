package rh.study.knowledge.entity.redis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@Table(name = "redis_ip")
public class RedisIp {

    @Id
    private Integer id;
    private Integer groupId;
    private String ip;
    private Integer master;
    private String intro;
    private Integer stat;
    private Date createTime;
    private Date updateTime;
}
