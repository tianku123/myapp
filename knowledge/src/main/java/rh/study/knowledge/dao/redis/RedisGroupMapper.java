package rh.study.knowledge.dao.redis;

import org.apache.ibatis.annotations.Mapper;
import rh.study.knowledge.entity.redis.RedisGroup;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface RedisGroupMapper extends BaseMapper<RedisGroup> {

}
