package com.aihouse.aihouseservice.renthouse.impl;

import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.RentHouse;
import com.aihouse.aihousedao.dao.renthouse.SysRentHouseDao;
import com.aihouse.aihouseservice.renthouse.SysRentHouseService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRentHouseServiceImpl implements SysRentHouseService {

    @Resource
    private SysRentHouseDao sysRentHouseDao;

    @Override
    public SysRentHouseDao initDao() {
        return sysRentHouseDao;
    }

    @Override
    public List<Map<String, Object>> getUserRentHouseList(int userId) {
        return sysRentHouseDao.geUserRentHouseList(userId);
    }


    @CacheEvict(value = "rent",key = "#p0.id")
    @Override
    public int updateRent(RentHouse rentHouse) {
        return this.update(rentHouse);
    }

    @Override
    @Async
    public void sendSms(Integer status, String telephone, String reason) {
        SmsDemo.sendHouseSms(status,telephone,reason);
    }
}
