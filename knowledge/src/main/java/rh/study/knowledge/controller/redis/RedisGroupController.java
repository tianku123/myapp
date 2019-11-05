package rh.study.knowledge.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.common.StatParam;
import rh.study.knowledge.entity.redis.RedisGroup;
import rh.study.knowledge.service.redis.RedisGroupService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/redis/group")
public class RedisGroupController {

    @Autowired
    private RedisGroupService redisGroupService;

    @GetMapping(value = "list")
    public Result list(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize) {
        PageResult pageResult = redisGroupService.listPagable(current, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping(value = "list4Select")
    public Result list4Select() {
        List<RedisGroup> list = redisGroupService.list4Select();
        return Result.success(list);
    }

    @PostMapping(value = "save")
    public Result save(@RequestParam String name) {
        int i = redisGroupService.saveRedisGroup(name);
        return Result.success(i);
    }

    @PostMapping(value = "update")
    public Result update(@RequestParam Integer id, @RequestParam String name) {
        int i = redisGroupService.updateRedisGroup(id, name);
        return Result.success(i);
    }

    @PostMapping(value = "delete")
    public Result delete(@RequestParam Integer id) {
        int i = redisGroupService.deleteRedisGroup(id);
        return Result.success(i);
    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        RedisGroup i = redisGroupService.queryRedisGroupById(id);
        return Result.success(i);
    }
}
