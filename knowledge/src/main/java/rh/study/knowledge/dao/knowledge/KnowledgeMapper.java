package rh.study.knowledge.dao.knowledge;

import org.apache.ibatis.annotations.Mapper;
import rh.study.knowledge.entity.knowledge.Knowledge;
import rh.study.knowledge.entity.test.Test;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created by admin on 2018/11/2.
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge>{

}
