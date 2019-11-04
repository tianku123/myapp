package rh.study.knowledge.controller.redis;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.entity.redis.Monitor;
import rh.study.knowledge.service.redis.RedisService;
import rh.study.knowledge.util.ExcelExportUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/redis/monitor")
public class RedisController {

    @Autowired
    private RedisService redisService;

    /**
     * 获取当前 Redis监控文件
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getRedisExcel")
    public void getRedisExcel(HttpServletRequest request,
                              HttpServletResponse response) {
        XSSFWorkbook workbook = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            workbook = redisService.getRedisExcel();
            ExcelExportUtil.outWrite(request, response, workbook, "Redis监控文件"+ format.format(new Date()) +".xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前 Redis监控
     * @return
     */
    @GetMapping(value = "/saveRedisMonitor")
    public Result getRedisMonitor() {
        int list = redisService.saveRedisMonitor();
        return Result.success(list);
    }

    /**
     * 获取当前 Redis监控列表
     * @return
     */
    @GetMapping(value = "/listPageable")
    public Result list(@RequestParam(defaultValue = "1") int current,
                       @RequestParam(defaultValue = "10") int pageSize,
                       @RequestParam Integer ipId
                       ) {
        PageResult list = redisService.listPageable(current, pageSize, ipId);
        return Result.success(list);
    }

}
