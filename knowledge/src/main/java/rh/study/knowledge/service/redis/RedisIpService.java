package rh.study.knowledge.service.redis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.dao.redis.RedisIpMapper;
import rh.study.knowledge.entity.redis.RedisGroup;
import rh.study.knowledge.entity.redis.RedisIp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class RedisIpService {


    private static List<Map<String, Object>> ipList = new ArrayList<>();

    @Autowired
    private RedisIpMapper redisIpMapper;


    public PageResult listPagable(int current, int pageSize) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
//        List<RedisIp> list = redisIpMapper.selectAll();
        List<Map<String, Object>> list = redisIpMapper.list();
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public int saveRedisIp(RedisIp b) {
        return redisIpMapper.insertSelective(b);
    }

    public int updateRedisIp(RedisIp b) {
        return redisIpMapper.updateByPrimaryKey(b);
    }

    public int deleteRedisIp(RedisIp b) {
        return redisIpMapper.delete(b);
    }

    public RedisIp queryRedisIpById(Integer id) {
        return redisIpMapper.selectByPrimaryKey(id);
    }

    public List<RedisIp> list4Select(Integer groupId) {
        RedisIp bean = new RedisIp();
        bean.setGroupId(groupId);
        return redisIpMapper.select(bean);
    }
}
