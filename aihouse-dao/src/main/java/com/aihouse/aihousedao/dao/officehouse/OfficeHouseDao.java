package com.aihouse.aihousedao.dao.officehouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.OfficeHouse;

import java.util.List;
import java.util.Map;

/**
 * 写字楼出租
 */
public interface OfficeHouseDao extends BaseDao<OfficeHouse> {

    public Map<String,Object> selectByIdForSolr(Integer id);

    public Map<String,Object> queryInfo(Integer id);

    public Map<String,Object> queryDetail(Integer id);

    public List<Map<String,Object>> getUserShopHouseList(int userId);
}
