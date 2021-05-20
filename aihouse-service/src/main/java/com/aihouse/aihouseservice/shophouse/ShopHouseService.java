package com.aihouse.aihouseservice.shophouse;

import com.aihouse.aihousedao.bean.ShopHouse;
import com.aihouse.aihousedao.dao.shophouse.ShopHouseDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface ShopHouseService extends BaseService<ShopHouse,ShopHouseDao> {
    public List<Map<String,Object>> getUserShopHouseList(int i);

    public int updateShopHouse(ShopHouse shopHouse);

    public void sendSms(Integer checkStatus, String telephone, String reason);
}
