package rh.study.knowledge.dao.jiufang;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.YouKe;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
@Repository("fangZhuMapper")
@Mapper
public interface FangZhuMapper extends BaseMapper<FangZhu> {

    Map<String, Object> queryByPhone(@Param(value = "phone") String phone);

    List<Map<String,Object>> list(Map<String, Object> params);
}
