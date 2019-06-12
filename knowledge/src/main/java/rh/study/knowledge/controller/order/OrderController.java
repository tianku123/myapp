package rh.study.knowledge.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Result list() {
        return Result.success(orderService.list());
    }
}
