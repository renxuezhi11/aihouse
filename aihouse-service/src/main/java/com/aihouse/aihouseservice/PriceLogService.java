package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.bean.PriceLog;
import com.aihouse.aihousedao.dao.PriceLogDao;

import java.util.List;

public interface PriceLogService extends BaseService<PriceLog,PriceLogDao> {
    public void insert(Integer houseId,Integer type,Double oldPrice,Double newPrice,String remark);

    List<PriceLog> selectAllLow();

    void updateIds(List<Integer> ids);
}
