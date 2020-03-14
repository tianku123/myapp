package rh.study.knowledge.service.jiufang;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.jiufang.JpConfigMapper;
import rh.study.knowledge.entity.jiufang.FangZhu;
import rh.study.knowledge.entity.jiufang.JpConfig;

import java.util.List;
import java.util.Map;

/**
 * 总酒票管理
 */
@Service
public class JpConfigService {

    @Autowired
    private JpConfigMapper jpConfigMapper;

    /**
     * 判断剩余酒票
     *
     * @param i
     * @return
     */
    public JpConfig checkJpNum(int i) {
        List<JpConfig> list = jpConfigMapper.selectAll();
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(500, "没有配置酒票总数");
        }
        if (list.get(0).getSyNum() >= i) {

        } else {
            throw new ServiceException(500, "酒票已发完");
        }
        return list.get(0);
    }

    /**
     * 修改总酒票信息
     * @param num
     */
    public void addJpNum(Integer num) {
        JpConfig jpConfig = checkJpNum(num);
        jpConfig.setFcNum(jpConfig.getFcNum() + num);
        jpConfig.setSyNum(jpConfig.getNum() - jpConfig.getFcNum());
        jpConfigMapper.updateByPrimaryKey(jpConfig);
    }

    public PageResult list(int current, int pageSize) {
        //分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
        List<JpConfig> list = jpConfigMapper.selectAll();
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());
    }

    public Result update(Integer num) {
        List<JpConfig> list = jpConfigMapper.selectAll();
        JpConfig jpConfig;
        if (CollectionUtils.isEmpty(list)) {
            jpConfig = new JpConfig();
            jpConfig.setSyNum(num);
            jpConfig.setFcNum(0);
            jpConfig.setNum(num);
            int i = jpConfigMapper.insert(jpConfig);
            if (i > 0) {
                return Result.success(i);
            }
        }
        jpConfig = list.get(0);
        if (num <= jpConfig.getFcNum()) {
            throw new ServiceException(500, "重新设置的票数不能小于已发出票数");
        }
        jpConfig.setNum(num);
        jpConfig.setSyNum(num - jpConfig.getFcNum());
        int i = jpConfigMapper.updateByPrimaryKey(jpConfig);
        if (i > 0) {
            return Result.success(i);
        }
        return Result.failure(500, "失败");
    }

    public JpConfig queryById(Integer id) {
        return jpConfigMapper.selectByPrimaryKey(id);
    }
}
