package rh.study.knowledge.dao.jiufang;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rh.study.knowledge.entity.jiufang.JiuFangYouKeLog;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
//@Repository("fangZhuYouKeRelMapper")
@Mapper
public interface JiuFangYouKeLogMapper extends BaseMapper<JiuFangYouKeLog> {

    /**
     * 查询酒坊内游客
     * @param jfId
     * @return
     */
    List<Map<String,Object>> queryYoukeByJfId(@Param(value = "jfId") Integer jfId);

    /**
     * 查询酒坊内分数排行榜
     */
    List<Map<String,Object>> queryScoreByJfId(@Param(value = "jfId") Integer jfId);
    List<Map<String,Object>> queryScoreByJfIdOrderByUpdateTimeDesc(@Param(value = "jfId") Integer jfId);
}
