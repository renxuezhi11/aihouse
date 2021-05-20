package com.aihouse.aihousedao.dao.shophouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.ShopHouse;

import java.util.List;
import java.util.Map;

/**
 * 商铺出租dao
 */
public interface ShopHouseDao extends BaseDao<ShopHouse> {
    public Map<String,Object> selectByIdForSolr(Integer id);

    public Map<String,Object> queryInfo(Integer id);

    public Map<String,Object> queryDetail(Integer id);

    public List<Map<String,Object>> getUserShopHouseList(int userId);
}
