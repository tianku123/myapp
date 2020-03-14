package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.JpConfig;
import rh.study.knowledge.service.jiufang.FangZhuService;
import rh.study.knowledge.service.jiufang.JpConfigService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 总票数设置
 */
@RestController
@RequestMapping(value = "/api/wechat/jp")
public class JpConfigController {

    @Autowired
    private JpConfigService jpConfigService;

    @GetMapping(value = "list")
    public Result list(@RequestParam(defaultValue = "1") int current,
                       @RequestParam(defaultValue = "10") int pageSize

                       ) {
        PageResult pageResult = jpConfigService.list(current, pageSize);
        return Result.success(pageResult);
    }

    @PostMapping(value = "save")
    public Result update(
                         @RequestParam Integer num // 票数
    ) {
        return jpConfigService.update(num);
    }

    @GetMapping(value = "queryById")
    public Result queryById(@RequestParam Integer id) {
        JpConfig i = jpConfigService.queryById(id);
        return Result.success(i);
    }
}
