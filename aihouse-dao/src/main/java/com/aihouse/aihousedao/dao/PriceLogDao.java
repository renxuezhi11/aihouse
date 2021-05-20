package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.PriceLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房源价格变动dao
 */
public interface PriceLogDao extends BaseDao<PriceLog> {
    public List<PriceLog> selectAllLow();

    void updateIds(@Param("ids") List<Integer> ids);
}
