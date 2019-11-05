package rh.study.knowledge.service.redis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.redis.RedisGroupMapper;
import rh.study.knowledge.entity.common.StatParam;
import rh.study.knowledge.entity.redis.RedisGroup;

import java.util.Date;
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

    public int saveRedisGroup(String name) {
        RedisGroup b = new RedisGroup();
        b.setName(name.trim());
        b.setStat(StatParam.SAVE);
        List<RedisGroup> list = redisGroupMapper.select(b);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException(500, "该分组已存在！");
        }
        b.setCreateTime(new Date());
        return redisGroupMapper.insertSelective(b);
    }

    public int updateRedisGroup(Integer id, String name) {
        RedisGroup b = new RedisGroup();
        b.setName(name.trim());
        b.setStat(StatParam.SAVE);
        List<RedisGroup> list = redisGroupMapper.select(b);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException(500, "该分组已存在！");
        }
        b.setUpdateTime(new Date());
        b.setId(id);
        return redisGroupMapper.updateByPrimaryKey(b);
    }

    public int deleteRedisGroup(Integer id) {
        RedisGroup b = redisGroupMapper.selectByPrimaryKey(id);
        if (b == null) {
            throw new ServiceException(500, "该分组不存在！");
        }
        b.setStat(StatParam.DELETE);
        b.setUpdateTime(new Date());
        return redisGroupMapper.updateByPrimaryKey(b);
    }

    public RedisGroup queryRedisGroupById(Integer id) {
        return redisGroupMapper.selectByPrimaryKey(id);
    }

    public List<RedisGroup> list4Select() {
        return redisGroupMapper.selectAll();
    }
}
