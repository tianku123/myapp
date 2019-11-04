package rh.study.knowledge.dao.cron;

import org.apache.ibatis.annotations.Mapper;
import rh.study.knowledge.entity.cron.Cron;
import rh.study.knowledge.entity.redis.RedisIp;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface CronMapper extends BaseMapper<Cron> {

}
