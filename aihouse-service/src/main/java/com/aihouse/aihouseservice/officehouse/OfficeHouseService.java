package com.aihouse.aihouseservice.officehouse;

import com.aihouse.aihousedao.bean.OfficeHouse;
import com.aihouse.aihousedao.dao.officehouse.OfficeHouseDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 写字楼出租service
 */
public interface OfficeHouseService extends BaseService<OfficeHouse,OfficeHouseDao> {
   public List<Map<String,Object>> getUserShopHouseList(int i);

   public int updateOfficeHouse(OfficeHouse officeHouse);

    public void sendSms(Integer checkStatus, String telephone, String statusContent);
}
