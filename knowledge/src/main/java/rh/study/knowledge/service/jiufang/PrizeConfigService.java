package rh.study.knowledge.service.jiufang;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rh.study.knowledge.common.result.Result;
import rh.study.knowledge.common.result.ServiceException;
import rh.study.knowledge.dao.jiufang.PrizeConfigMapper;
import rh.study.knowledge.dao.jiufang.YkPrizeLogMapper;
import rh.study.knowledge.dao.jiufang.YouKeMapper;
import rh.study.knowledge.entity.jiufang.PrizeConfig;
import rh.study.knowledge.entity.jiufang.YkPrizeLog;
import rh.study.knowledge.entity.jiufang.YouKe;

import java.util.*;

/**
 * 总酒票管理
 */
@Service
public class PrizeConfigService {

    private Logger logger = LoggerFactory.getLogger(PrizeConfigService.class);

    @Autowired
    private PrizeConfigMapper prizeConfigMapper;

    @Autowired
    private YouKeMapper youKeMapper;

    @Autowired
    private YkPrizeLogMapper ykPrizeLogMapper;

    public Result list() {
        return Result.success(prizeConfigMapper.selectAll());
    }

    @Transactional
    public Result prizeLog(YkPrizeLog ykPrizeLog) {
        logger.warn("prizeLog:" + JSONObject.toJSONString(ykPrizeLog));
        YouKe youKe = new YouKe();
        youKe.setOpenid(ykPrizeLog.getYkOpenid());
        youKe = youKeMapper.selectOne(youKe);
        if (youKe == null) {
            throw new ServiceException(500, "游客不存在");
        }
        // 奖品id,数量;奖品id,数量
        String prizeStr = ykPrizeLog.getPrizeStr();
        if (prizeStr.indexOf(",") == -1) {
            throw new ServiceException(400, "prizeStr错误");
        }
        String[] arr = prizeStr.split(";");
        Map<Integer, Integer> map = new HashMap<>();
        int total = 0;
        for (String s : arr) {
            if (s.indexOf(",") == -1) {
                throw new ServiceException(400, "prizeStr错误");
            }
            String[] a = s.split(",");
            map.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        }
        List<YkPrizeLog> ykPrizeLogList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            PrizeConfig prizeConfig = prizeConfigMapper.selectByPrimaryKey(entry.getKey());
            if (prizeConfig == null) {
                throw new ServiceException(500, "此奖品不存在");
            }
            total += entry.getValue() * prizeConfig.getNum();
            YkPrizeLog bean = new YkPrizeLog();
            bean.setYkOpenid(ykPrizeLog.getYkOpenid());
            bean.setCreateTime(new Date());
            bean.setPrizeConfigId(entry.getKey());
            bean.setNum(entry.getValue());
//            bean.setAddress(ykPrizeLog.getAddress());
//            bean.setShName(ykPrizeLog.getShName());
//            bean.setShPhone(ykPrizeLog.getShPhone());
            ykPrizeLogList.add(bean);
        }
        if (total > youKe.getJpNum()) {
            throw new ServiceException(500, "游客酒票不足");
        } else {
            if (CollectionUtils.isNotEmpty(ykPrizeLogList)) {
                // 减游客酒票
                youKe.setJpNum(youKe.getJpNum() - total);
                int i = youKeMapper.updateByPrimaryKey(youKe);
                if (i > 0) {
                    for (YkPrizeLog prizeLog : ykPrizeLogList) {
                        ykPrizeLogMapper.insert(prizeLog);
                    }
                }
            }
        }
        return Result.success();
    }

    @Transactional
    public Result prizeLogAddress(YkPrizeLog ykPrizeLog) {
        logger.warn("prizeLogAddress:" + JSONObject.toJSONString(ykPrizeLog));
        YouKe youKe = new YouKe();
        youKe.setOpenid(ykPrizeLog.getYkOpenid());
        youKe = youKeMapper.selectOne(youKe);
        if (youKe == null) {
            throw new ServiceException(500, "游客不存在");
        }
        // 奖品id,数量;奖品id,数量
        String prizeStr = ykPrizeLog.getPrizeStr();
        if (prizeStr.indexOf(",") == -1) {
            throw new ServiceException(400, "prizeStr错误");
        }
        String[] arr = prizeStr.split(";");
        Map<Integer, Integer> map = new HashMap<>();
        int total = 0;
        for (String s : arr) {
            if (s.indexOf(",") == -1) {
                throw new ServiceException(400, "prizeStr错误");
            }
            String[] a = s.split(",");
            map.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        }
        List<YkPrizeLog> ykPrizeLogList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            PrizeConfig prizeConfig = prizeConfigMapper.selectByPrimaryKey(entry.getKey());
            if (prizeConfig == null) {
                throw new ServiceException(500, "此奖品不存在");
            }
            total += entry.getValue() * prizeConfig.getNum();
            YkPrizeLog bean = new YkPrizeLog();
            bean.setYkOpenid(ykPrizeLog.getYkOpenid());
            bean.setCreateTime(new Date());
            bean.setPrizeConfigId(entry.getKey());
            bean.setNum(entry.getValue());
            bean.setAddress(ykPrizeLog.getAddress());
            bean.setShName(ykPrizeLog.getShName());
            bean.setShPhone(ykPrizeLog.getShPhone());
            ykPrizeLogList.add(bean);
        }
        if (total > youKe.getJpNum()) {
            throw new ServiceException(500, "游客酒票不足");
        } else {
            if (CollectionUtils.isNotEmpty(ykPrizeLogList)) {
                // 减游客酒票
                youKe.setJpNum(youKe.getJpNum() - total);
                int i = youKeMapper.updateByPrimaryKey(youKe);
                if (i > 0) {
                    for (YkPrizeLog prizeLog : ykPrizeLogList) {
                        ykPrizeLogMapper.insert(prizeLog);
                    }
                }
            }
        }
        return Result.success();
    }
}
