package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.PriceLog;
import com.aihouse.aihousedao.dao.PriceLogDao;
import com.aihouse.aihouseservice.PriceLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房源价格变动service
 */
@Service
public class PriceLogServiceImpl implements PriceLogService {

    @Resource
    private PriceLogDao priceLogDao;

    @Override
    public PriceLogDao initDao() {
        return this.priceLogDao;
    }

    /**
     * 异步插入价格变动记录
     * @param houseId
     * @param type
     * @param oldPrice
     * @param newPrice
     * @param remark
     */
    @Override
    @Async
    public void insert(Integer houseId, Integer type, Double oldPrice, Double newPrice, String remark) {
        PriceLog priceLog=new PriceLog();
        priceLog.setHouseId(houseId);
        priceLog.setNewPrice(newPrice);
        priceLog.setOldPrice(oldPrice);
        priceLog.setRemark(remark);
        priceLog.setType(type);
        this.insert(priceLog);
    }

    @Override
    public List<PriceLog> selectAllLow() {
        return priceLogDao.selectAllLow();
    }

    @Override
    public void updateIds(List<Integer> ids) {
        priceLogDao.updateIds(ids);
    }
}
