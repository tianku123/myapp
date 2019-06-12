package rh.study.knowledge.dao.order;

import org.apache.ibatis.annotations.Mapper;
import rh.study.knowledge.entity.order.Order;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
