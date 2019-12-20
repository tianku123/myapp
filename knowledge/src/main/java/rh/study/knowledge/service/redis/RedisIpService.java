package rh.study.knowledge.service.redis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.redis.RedisIpMapper;
import rh.study.knowledge.entity.common.StatParam;
import rh.study.knowledge.entity.redis.RedisIp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class RedisIpService {


    private static List<Map<String, Object>> ipList = new ArrayList<>();

    @Autowired
    private RedisIpMapper redisIpMapper;


    public PageResult listPagable(int current, int pageSize,Integer groupId,
                                  String ip, Integer stat) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
//        List<RedisIp> list = redisIpMapper.selectAll();
        if (!StringUtils.isEmpty(ip)) {
            ip = ip.trim();
        }
        List<Map<String, Object>> list = redisIpMapper.list(groupId, ip, stat);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public int saveRedisIp(String ip, Integer groupid) {
        RedisIp b = new RedisIp();
        b.setIp(ip.trim());
        b.setStat(StatParam.SAVE);
        List<RedisIp> list = redisIpMapper.select(b);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException(500, "此IP地址已有分组！");
        }
        b.setGroupId(groupid);
        b.setCreateTime(new Date());
        return redisIpMapper.insertSelective(b);
    }

    public int deleteRedisIp(Integer id) {
        RedisIp b = redisIpMapper.selectByPrimaryKey(id);
        if (b == null) {
            throw new ServiceException(500, "该记录不存在！");
        }
        b.setStat(StatParam.DELETE);
        b.setUpdateTime(new Date());
        return redisIpMapper.updateByPrimaryKey(b);
    }

    public RedisIp queryRedisIpById(Integer id) {
        return redisIpMapper.selectByPrimaryKey(id);
    }

    public List<RedisIp> list4Select(Integer groupId) {
        RedisIp bean = new RedisIp();
        bean.setGroupId(groupId);
        return redisIpMapper.select(bean);
    }

    @Transactional
    public int updateRedisIp(Integer id, String ip, Integer groupid) {
        RedisIp b = new RedisIp();
        b.setId(id);
        RedisIp old = redisIpMapper.selectByPrimaryKey(b);
        if (old == null) {
            throw new ServiceException(500, "该记录不存在！");
        }

        // 其他组有此IP则不能变更分组
        Map<String, Object> map = redisIpMapper.queryByIp(ip, old.getGroupId(), StatParam.SAVE);
        if (map != null && map.containsKey("CNT") && MapUtils.getInteger(map, "CNT") > 0) {
            throw new ServiceException(500, "此IP地址已有分组！");
        }
        // 本组有此IP
        RedisIp newR = new RedisIp();
        newR.setIp(ip.trim());
        newR.setStat(StatParam.SAVE);
        newR.setGroupId(groupid);
        List<RedisIp> list = redisIpMapper.select(newR);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new ServiceException(500, "此IP地址已在本分组内！");
        }
        /**
         * 把原有记录状态改为删除状态
         * 新增一条新纪录
         */
        old.setStat(StatParam.DELETE);
        old.setUpdateTime(new Date());
        redisIpMapper.updateByPrimaryKey(old);
        newR.setCreateTime(new Date());
        return redisIpMapper.insert(newR);
    }
}
