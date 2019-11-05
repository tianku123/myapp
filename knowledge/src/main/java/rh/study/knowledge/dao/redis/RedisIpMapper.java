package rh.study.knowledge.dao.redis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rh.study.knowledge.entity.redis.RedisIp;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface RedisIpMapper extends BaseMapper<RedisIp> {

    List<Map<String, Object>> list(@Param(value = "groupId") Integer groupId,
                                          @Param(value = "ip")String ip,
                                          @Param(value = "stat")Integer stat
    );

    Map<String, Object> queryByIp(@Param(value = "ip")String ip,
                            @Param(value = "groupId") Integer groupid,
                            @Param(value = "stat")Integer stat);
}
