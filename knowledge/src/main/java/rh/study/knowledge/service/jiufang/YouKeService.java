package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.*;
import rh.study.knowledge.entity.jiufang.YouKe;
import rh.study.knowledge.entity.jiufang.YkSuccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YouKeService {

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private JiuFangYouKeLogMapper jiuFangYouKeLogMapper;

    @Autowired
    private YxLogMapper yxLogMapper;

    @Autowired
    private JpConfigService jpConfigService;

    @Autowired
    private YkSuccessMapper ykSuccessMapper;

    public PageResult listPagable(int current, int pageSize, Map<String, Object> params) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<Map<String, Object>> list = youKeMapper.list(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public List<YkSuccess> fetchYkSuccessById(String openid) {
        YkSuccess yk = new YkSuccess();
        yk.setYkOpenid(openid);
        return ykSuccessMapper.select(yk);
    }

    public YouKe queryByOpenid(String openid) {
        YouKe youKe = new YouKe();
        youKe.setOpenid(openid);
        return youKeMapper.selectOne(youKe);
    }

    /**
     * 认证
     * @param yk
     * @return
     */
    @Transactional
    public Result auth(YouKe yk) {
        YouKe youk = new YouKe();
        youk.setOpenid(yk.getOpenid());
        youk = youKeMapper.selectOne(youk);
        if (youk != null) {
            return Result.failure(500, "游客已存在");
        }
        jpConfigService.checkJpNum(1);
        // 送一张酒票
        yk.setJpNum(1);
        yk.setYxNum(0);
        // 被邀请进入酒坊免费获取的酒票数
        yk.setFreeNum(1);
        yk.setCreateTime(new Date());
//        yk.setUpdateTime(new Date());
        int i = youKeMapper.insertSelective(yk);
        if (i > 0) {
            jpConfigService.addJpNum(1);
            // 初始化我的战绩
            YkSuccess ys = new YkSuccess();
            ys.setYkOpenid(yk.getOpenid());
            ys.setNum(0);
            ys.setYxTp(1);
            ykSuccessMapper.insert(ys);
            ys = new YkSuccess();
            ys.setYkOpenid(yk.getOpenid());
            ys.setNum(0);
            ys.setYxTp(2);
            ykSuccessMapper.insert(ys);
//            youk = youKeMapper.selectByPrimaryKey(youk.getId());
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("openid", yk.getOpenid());
            resMap.put("isOwner", true);
            resMap.put("isYk", true);
            resMap.put("jpNum", yk.getJpNum());
            resMap.put("yxNum", yk.getYxNum());
            return Result.success(resMap);
        } else {
            return Result.failure(500, "修改失败");
        }
    }

    public Result delete(Integer id) {
        int i = youKeMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return Result.success(i);
        }
        return Result.failure(500, "删除失败");
    }

    public Result addressInfo(YouKe yk) {
        YouKe youk = new YouKe();
        youk.setOpenid(yk.getOpenid());
        youk = youKeMapper.selectOne(youk);
        if (youk == null) {
            return Result.failure(500, "游客不存在");
        }
        youk.setAddress(yk.getAddress());
        youk.setShName(yk.getShName());
        youk.setShPhone(yk.getShPhone());
        int i = youKeMapper.updateByPrimaryKey(youk);
        if (i > 0) {
            return Result.success(i);
        }
        return Result.failure(500, "修改失败");
    }
}
