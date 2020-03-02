package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.FangZhuMapper;
import rh.study.knowledge.dao.jiufang.FangZhuYouKeRelMapper;
import rh.study.knowledge.dao.jiufang.YouKeMapper;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.FangZhuYouKeRel;
import rh.study.knowledge.entity.jiufang.YouKe;

import java.util.Date;
import java.util.List;

@Service
public class YouKeService {

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private FangZhuMapper fangZhuMapper;

    @Autowired
    private FangZhuYouKeRelMapper fangZhuYouKeRelMapper;

    public PageResult listPagable(int current, int pageSize) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<YouKe> list = youKeMapper.selectAll();
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    @Transactional
    public Result save(YouKe youKe, Integer fzId) {
        try {
            FangZhu fangZhu = fangZhuMapper.selectByPrimaryKey(fzId);
            if (fangZhu == null) {
                return Result.failure(403, "酒坊id错误");
            }
            youKe.setCreateTime(new Date());
            int i = youKeMapper.insertSelective(youKe);
            if (i > 0) {
                /**
                 * 游客和坊主关系表，
                 */
                FangZhuYouKeRel fangZhuYouKeRel = new FangZhuYouKeRel();
                fangZhuYouKeRel.setFxNum(0);//默认分享次数0
                fangZhuYouKeRel.setYxNum(5);//默认游戏次数5
                fangZhuYouKeRel.setFzId(fzId);// 坊主id
                fangZhuYouKeRel.setJpNum(0);//默认酒票0
                fangZhuYouKeRel.setYkId(youKe.getId());
                int j = fangZhuYouKeRelMapper.insertSelective(fangZhuYouKeRel);
                if (j > 0) {
                    // 更新坊主的游客数
                    fangZhu.setUpdateTime(new Date());
                    fangZhu.setYkNum(fangZhu.getYkNum() + 1);
                    fangZhuMapper.updateByPrimaryKeySelective(fangZhu);
                    return Result.success("创建成功");
                } else {
                    if (youKe.getId() != null) {
                        youKeMapper.deleteByPrimaryKey(youKe.getId());
                    }
                    if (fangZhuYouKeRel.getId() != null) {
                        fangZhuYouKeRelMapper.deleteByPrimaryKey(fangZhuYouKeRel.getId());
                    }
                    return Result.failure(500, "游客创建失败");
                }
            } else {
                if (youKe.getId() != null) {
                    youKeMapper.deleteByPrimaryKey(youKe.getId());
                }
                return Result.failure(500, "游客创建失败");
            }
        } catch (Exception e) {
            return Result.failure(500, "创建失败");
        }
    }

    public YouKe queryRedisGroupById(Integer id) {
        return youKeMapper.selectByPrimaryKey(id);
    }

}
