package rh.study.knowledge.dao.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import rh.study.knowledge.entity.test.Test;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface TestMapper extends BaseMapper<Test>{

  @Select("select * from test where name = #{name}")
  List<Test> findByName(@Param("name") String name);

}
