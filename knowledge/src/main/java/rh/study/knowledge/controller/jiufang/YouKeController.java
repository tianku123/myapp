package rh.study.knowledge.controller.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.YkPrizeLog;
import rh.study.knowledge.entity.jiufang.YkSuccess;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.service.jiufang.YouKeService;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 根据游客的openid获取战绩
     * @param openid
     * @return
     */
    @GetMapping(value = "fetchYkSuccessById")
    public Result fetchYkSuccessById(@RequestParam String openid) {
        List<YkSuccess> i = youKeService.fetchYkSuccessById(openid);
        return Result.success(i);
    }

    /**
     * 根据游客的openid获取兑奖记录
     * @param openid
     * @return
     */
    @GetMapping(value = "fetchYkPrizeById")
    public Result fetchYkPrizeById(@RequestParam String openid) {
        List<YkPrizeLog> i = youKeService.fetchYkPrizeById(openid);
        return Result.success(i);
    }


    @PostMapping(value = "delete")
    public Result delete(@RequestParam Integer id) {
        return youKeService.delete(id);
    }

    @PostMapping(value = "addressInfo")
    public Result addressInfo(
            @RequestBody YouKe youKe
    ) {
        if (youKe.getOpenid() == null) {
            throw new ServiceException(400, "openid必传");
        }
        if (youKe.getShName() == null) {
            throw new ServiceException(400, "shName必传");
        }
        if (youKe.getShPhone() == null) {
            throw new ServiceException(400, "shPhone必传");
        }
        if (youKe.getAddress() == null) {
            throw new ServiceException(400, "address必传");
        }
        return youKeService.addressInfo(youKe);
    }
}
