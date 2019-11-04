package rh.study.knowledge.service.redis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.dao.redis.RedisGroupMapper;
import rh.study.knowledge.entity.redis.RedisGroup;

import java.util.List;

@Service
public class RedisGroupService {

    @Autowired
    private RedisGroupMapper redisGroupMapper;

    public PageResult listPagable(int current, int pageSize) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<RedisGroup> list = redisGroupMapper.selectAll();
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public int saveRedisGroup(RedisGroup b) {
        return redisGroupMapper.insertSelective(b);
    }

    public int updateRedisGroup(RedisGroup b) {
        return redisGroupMapper.updateByPrimaryKey(b);
    }

    public int deleteRedisGroup(RedisGroup b) {
        return redisGroupMapper.delete(b);
    }

    public RedisGroup queryRedisGroupById(Integer id) {
        return redisGroupMapper.selectByPrimaryKey(id);
    }

    public List<RedisGroup> list4Select() {
        return redisGroupMapper.selectAll();
    }
}
