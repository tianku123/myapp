package rh.study.knowledge.service.jiufang;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.jiufang.JpConfigMapper;
import rh.study.knowledge.entity.jiufang.JpConfig;

import java.util.List;

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
}
