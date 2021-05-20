package com.aihouse.aihouseservice.officehouse.impl;

import com.aihouse.aihousedao.bean.OfficeHouse;
import com.aihouse.aihousedao.dao.officehouse.OfficeHouseDao;
import com.aihouse.aihouseservice.officehouse.OfficeHouseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sms.SmsDemo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class OfficeHouseServiceImpl implements OfficeHouseService {

    @Resource
    private OfficeHouseDao officeHouseDao;

    @Override
    public OfficeHouseDao initDao() {
        return officeHouseDao;
    }

    @Override
    public List<Map<String, Object>> getUserShopHouseList(int userId) {
        return officeHouseDao.getUserShopHouseList(userId);
    }

    @Override
    @CacheEvict(value = "officehouse",key = "#p0.id")
    public int updateOfficeHouse(OfficeHouse officeHouse) {
        return this.update(officeHouse);
    }

    @Override
    @Async
    public void sendSms(Integer checkStatus, String telephone, String statusContent) {
        SmsDemo.sendHouseSms(checkStatus,telephone,statusContent);
    }
}
