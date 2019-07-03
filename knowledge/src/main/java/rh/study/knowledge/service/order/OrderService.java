package rh.study.knowledge.service.order;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
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

    public PageResult listPagable(int current, int pageSize) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Order> list = orderMapper.selectAll();
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }
}
