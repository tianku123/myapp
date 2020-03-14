package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.service.jiufang.YouKeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/wechat/yk")
public class YouKeController {

    @Autowired
    private YouKeService youKeService;

    @GetMapping(value = "list")
    public Result list(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String nickName
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", nickName);
        PageResult pageResult = youKeService.listPagable(current, pageSize, params);
        return Result.success(pageResult);
    }

    @GetMapping(value = "queryRedisGroupById")
    public Result queryRedisGroupById(@RequestParam Integer id) {
        YouKe i = youKeService.queryRedisGroupById(id);
        return Result.success(i);
    }
}
