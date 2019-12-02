package rh.study.knowledge.dao.redis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rh.study.knowledge.entity.redis.Monitor;
import rh.study.knowledge.entity.redis.RedisGroup;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface MonitorMapper extends BaseMapper<Monitor> {

    List<Map<String,Object>> list(Map<String, Object> params);

    void batchInsert(@Param(value = "monitorList") List<Monitor> monitorList);
}
