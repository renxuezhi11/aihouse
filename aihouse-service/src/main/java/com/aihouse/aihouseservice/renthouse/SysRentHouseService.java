package com.aihouse.aihouseservice.renthouse;

import com.aihouse.aihousedao.bean.RentHouse;
import com.aihouse.aihousedao.dao.renthouse.SysRentHouseDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface SysRentHouseService extends BaseService<RentHouse,SysRentHouseDao>{
    public List<Map<String,Object>> getUserRentHouseList(int i);
    public int updateRent(RentHouse rentHouse);

    public void sendSms(Integer status, String telephone, String reason);
}
