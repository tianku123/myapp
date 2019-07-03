package rh.study.knowledge.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.service.order.OrderService;

/**
 * 订单
 */
@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "list")
    public Result list(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize) {
        PageResult pageResult = orderService.listPagable(current, pageSize);
        return Result.success(pageResult);
    }
}
