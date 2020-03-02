package rh.study.knowledge.dao.jiufang;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import rh.study.knowledge.entity.jiufang.FangZhuYouKeRel;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * Created by admin on 2018/11/2.
 */
@Repository("fangZhuYouKeRelMapper")
@Mapper
public interface FangZhuYouKeRelMapper extends BaseMapper<FangZhuYouKeRel> {

}
