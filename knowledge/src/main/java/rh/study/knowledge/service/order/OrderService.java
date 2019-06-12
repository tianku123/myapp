package rh.study.knowledge.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.dao.order.OrderMapper;
import rh.study.knowledge.entity.order.Order;

import java.util.List;

/**
 * Created by admin on 2018/11/2.
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public List<Order> list() {
        return orderMapper.selectAll().subList(0, 10);
    }
}
