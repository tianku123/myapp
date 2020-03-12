package rh.study.knowledge.dao.jiufang;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rh.study.knowledge.entity.jiufang.JiuFang;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/11/2.
 */
//@Repository("fangZhuMapper")
@Mapper
public interface JiuFangMapper extends BaseMapper<JiuFang> {

    JiuFang queryByPhone(@Param(value = "phone") String phone);

    List<Map<String,Object>> list(Map<String, Object> params);

    // 根据openid 获取已认证的经销商
    Map<String, Object> queryByOpenid(String openid);
}
