package com.aihouse.aihousedao.dao;
import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.SysMarket;


/**
 *行情表 dao
 */
@Mapper
public interface SysMarketDao  extends BaseDao<SysMarket> {
}
