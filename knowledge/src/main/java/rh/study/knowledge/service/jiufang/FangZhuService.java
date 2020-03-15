package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.FangZhuMapper;
import rh.study.knowledge.entity.jiufang.FangZhu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FangZhuService {

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private JpConfigService jpConfigService;

    public PageResult listPagable(int current, int pageSize, Map<String, Object> params) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Map<String, Object>> list = fangZhuMapper.list(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public Result save(FangZhu fangZhu) {
        try {
            FangZhu phone = fangZhuMapper.queryByPhone(fangZhu.getPhone());
            if (phone == null) {
                int i = fangZhuMapper.insertSelective(fangZhu);
                if (i > 0) {
                    // 减少总酒票数
                    jpConfigService.addJpNum(fangZhu.getNum());
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
            FangZhu fz = fangZhuMapper.selectByPrimaryKey(id);
            // 状态：0删除，1创建状态，2微信认证过
            fz.setStat(0);
            fz.setUpdateTime(new Date());
            int i = fangZhuMapper.updateByPrimaryKeySelective(fz);
            if (i > 0) {
                // 删除，回退总票数
                jpConfigService.addJpNum(fz.getFcNum() - fz.getNum());
                return Result.success(i);
            } else {
                return Result.failure(500, "删除失败");
            }
        } catch (Exception e) {
            return Result.failure(500, "删除失败");
        }
    }

    @Transactional
    public Result update(FangZhu fangZhu) {
        int updateNum = fangZhu.getNum();
        FangZhu fz = fangZhuMapper.selectByPrimaryKey(fangZhu.getId());
        int oldNum = fz.getNum();
        if (fz == null) {
            return Result.failure(500, "经销商不存在");
        }
//        if (2 == fz.getStat().intValue()) {
//            return Result.failure(500, "经销商已认证");
//        }
        if (fangZhu.getNum() < fz.getFcNum()) {
            return Result.failure(500, "已发出" + fz.getFcNum() + "张酒票");
        }
//        fz.setOpenid(fangZhu.getOpenid());
//        fz.setNickName(fangZhu.getNickName());
//        fz.setAvatarUrl(fangZhu.getAvatarUrl());
//        fz.setGender(fangZhu.getGender());
//        fz.setProvince(fangZhu.getProvince());
//        fz.setCity(fangZhu.getCity());
//        fz.setUpdateTime(new Date());
//        // 状态：0删除，1创建状态，2微信认证过
//        fz.setStat(2);
        fz.setNum(fangZhu.getNum());
        fz.setName(fangZhu.getName());
        int i = fangZhuMapper.updateByPrimaryKeySelective(fz);
        if (i > 0) {
            // 减少总酒票数
//            if (fangZhu.getNum() > fz.getNum()) {
                // 增加酒票 或 减少酒票，修改总票数
                jpConfigService.addJpNum(updateNum - oldNum);
//            }
            return Result.success(i);
        } else {
            return Result.failure(500, "修改失败");
        }
    }

    /**
     * 认证 update的变种方法
     *
     * @param fangZhu
     * @return
     */
    @Transactional
    public Result auth(FangZhu fangZhu) {
        FangZhu fz = fangZhuMapper.queryByPhone(fangZhu.getPhone());
        if (fz == null) {
            return Result.failure(500, "经销商不存在");
        }
        if (2 == fz.getStat().intValue()) {
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("openid", fz.getOpenid());
            resMap.put("isOwner", true);
            resMap.put("isYk", false);
            int n = fz.getNum() == null ? 0 : fz.getNum();
            int f = fz.getFcNum() == null ? 0 : fz.getFcNum();
            resMap.put("jpNum", (n - f));
            return Result.success(resMap);
        }
        fz.setOpenid(fangZhu.getOpenid());
        fz.setNickName(fangZhu.getNickName());
        fz.setAvatarUrl(fangZhu.getAvatarUrl());
        fz.setGender(fangZhu.getGender());
        fz.setProvince(fangZhu.getProvince());
        fz.setCity(fangZhu.getCity());
        fz.setUpdateTime(new Date());
        // 状态：0删除，1创建状态，2微信认证过
        fz.setStat(2);
        int i = fangZhuMapper.updateByPrimaryKeySelective(fz);
        if (i > 0) {
            FangZhu fangZhu1 = queryByOpenid(fz.getOpenid());
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("openid", fangZhu1.getOpenid());
            resMap.put("isOwner", true);
            resMap.put("isYk", false);
            int n = fz.getNum() == null ? 0 : fz.getNum();
            int f = fz.getFcNum() == null ? 0 : fz.getFcNum();
            resMap.put("jpNum", (n - f));
            return Result.success(resMap);
        } else {
            return Result.failure(500, "修改失败");
        }
    }

    /**
     * 根据openid 获取已认证的经销商
     *
     * @param openid
     * @return
     */
    public FangZhu queryByOpenid(String openid) {
        FangZhu fangZhu = new FangZhu();
        fangZhu.setOpenid(openid);
        fangZhu.setStat(2);
        return fangZhuMapper.selectOne(fangZhu);
    }
}
