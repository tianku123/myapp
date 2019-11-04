package rh.study.knowledge.entity.redis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
@Entity(name = "monitor")
public class Monitor {

    @Id
    private Integer id;
    private Integer ipId;
    // 连接客户端数
    private Integer clientNum;
    // 每分钟命令数
    private Double commands;
    // 内存使用
    private Double memoryUsed;
    // Redis的key数量
    private Integer redisKeys;
    // 创建时间
    private Date createTime;

    @Transient
    private String ip;
    @Transient
    private String groupName;
}
