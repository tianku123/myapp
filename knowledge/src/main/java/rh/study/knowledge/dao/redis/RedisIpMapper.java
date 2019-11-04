package rh.study.knowledge.dao.redis;

import org.apache.ibatis.annotations.Mapper;
import rh.study.knowledge.entity.redis.RedisIp;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface RedisIpMapper extends BaseMapper<RedisIp> {

    public List<Map<String, Object>> list();

}
