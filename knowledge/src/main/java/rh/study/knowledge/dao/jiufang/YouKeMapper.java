package rh.study.knowledge.dao.jiufang;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import rh.study.knowledge.entity.jiufang.YouKe;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
//@Repository("youKeMapper")
@Mapper
public interface YouKeMapper extends BaseMapper<YouKe> {

    List<Map<String,Object>> list(Map<String, Object> params);

    List<Map<String,Object>> jpRankPagable(Map<String, Object> params);
}
