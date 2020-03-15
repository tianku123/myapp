package rh.study.knowledge.service.jiufang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.dao.jiufang.PrizeConfigMapper;

/**
 * 总酒票管理
 */
@Service
public class PrizeConfigService {

    @Autowired
    private PrizeConfigMapper prizeConfigMapper;

    public Result list() {
        return Result.success(prizeConfigMapper.selectAll());
    }
}
