package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.FangZhuMapper;
import rh.study.knowledge.entity.jiufang.FangZhu;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FangZhuService {

    @Autowired
    private FangZhuMapper fangZhuMapper;

    public PageResult listPagable(int current, int pageSize, Map<String, Object> params) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Map<String, Object>> list = fangZhuMapper.list(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public Result save(FangZhu fangZhu) {
        try {
            Map<String, Object> phone = fangZhuMapper.queryByPhone(fangZhu.getPhone());
            if (phone == null) {
                int i = fangZhuMapper.insertSelective(fangZhu);
                if (i > 0) {
                    return Result.success(i);
                } else {
                    return Result.failure(500, "创建失败");
                }
            } else {
                return Result.failure(500, "手机号码已存在！");
            }
        } catch (Exception e) {
            return Result.failure(500, "创建失败");
        }
    }


    public FangZhu queryById(Integer id) {
        return fangZhuMapper.selectByPrimaryKey(id);
    }

    public Result delete(Integer id) {
        try {
            FangZhu fz = new FangZhu();
            fz.setId(id);
            // 状态：0删除，1创建状态，2微信认证过
            fz.setStat(0);
            fz.setUpdateTime(new Date());
            int i = fangZhuMapper.updateByPrimaryKeySelective(fz);
            if (i > 0) {
                return Result.success(i);
            } else {
                return Result.failure(500, "删除失败");
            }
        } catch (Exception e) {
            return Result.failure(500, "删除失败");
        }
    }

    public Result update(FangZhu fangZhu) {
        try {
            // 根据手机号码获取坊主信息
            FangZhu fz = new FangZhu();
            fz.setPhone(fangZhu.getPhone());
            fz = fangZhuMapper.selectOne(fz);
            if (fz == null) {
                return Result.failure(500, "坊主不存在");
            }
            fangZhu.setOpenid(fangZhu.getOpenid());
            fangZhu.setNickName(fangZhu.getNickName());
            fangZhu.setAvatarUrl(fangZhu.getAvatarUrl());
            fangZhu.setGender(fangZhu.getGender());
            fangZhu.setProvince(fangZhu.getProvince());
            fangZhu.setCity(fangZhu.getCity());
            fangZhu.setUpdateTime(new Date());
            // 状态：0删除，1创建状态，2微信认证过
            fangZhu.setStat(2);
            int i = fangZhuMapper.updateByPrimaryKeySelective(fz);
            if (i > 0) {
                return Result.success(i);
            } else {
                return Result.failure(500, "修改失败");
            }
        } catch (Exception e) {
            return Result.failure(500, "修改失败");
        }
    }

    /**
     * 根据openid 获取已认证的坊主
     * @param openid
     * @return
     */
    public Map<String, Object> queryByOpenid(String openid) {
        return fangZhuMapper.queryByOpenid(openid);
    }
}
