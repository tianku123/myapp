package rh.study.knowledge.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.redis.RedisIp;
import rh.study.knowledge.service.redis.RedisIpService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/redis/ip")
public class RedisIpController {

    @Autowired
    private RedisIpService redisIpService;

    @GetMapping(value = "/list")
    public Result list(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize) {
        PageResult pageResult = redisIpService.listPagable(current, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping(value = "list4Select")
    public Result list4Select(@RequestParam Integer groupId) {
        List<RedisIp> list = redisIpService.list4Select(groupId);
        return Result.success(list);
    }

    @PostMapping(value = "save")
    public Result save(@RequestParam String ip, @RequestParam Integer groupid) {
        RedisIp b = new RedisIp();
        b.setIp(ip);
        b.setGroupId(groupid);
        int i = redisIpService.saveRedisIp(b);
        return Result.success(i);
    }

    @PostMapping(value = "update")
    public Result update(@RequestParam Integer id, @RequestParam String ip,
                         @RequestParam Integer groupid) {
        RedisIp b = new RedisIp();
        b.setId(id);
        b.setIp(ip);
        b.setGroupId(groupid);
        int i = redisIpService.updateRedisIp(b);
        return Result.success(i);
    }

    @PostMapping(value = "delete")
    public Result delete(@RequestParam Integer id) {
        RedisIp b = new RedisIp();
        b.setId(id);
        int i = redisIpService.deleteRedisIp(b);
        return Result.success(i);
    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        RedisIp i = redisIpService.queryRedisIpById(id);
        return Result.success(i);
    }
}
