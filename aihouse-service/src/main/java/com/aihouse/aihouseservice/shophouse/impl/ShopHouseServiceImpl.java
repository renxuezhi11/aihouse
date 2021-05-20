package com.aihouse.aihouseservice.shophouse.impl;

import com.aihouse.aihousedao.bean.ShopHouse;
import com.aihouse.aihousedao.dao.shophouse.ShopHouseDao;
import com.aihouse.aihouseservice.shophouse.ShopHouseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sms.SmsDemo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 商铺出租service
 */
@Service
public class ShopHouseServiceImpl implements ShopHouseService {

    @Resource
    private ShopHouseDao shopHouseDao;

    @Override
    public ShopHouseDao initDao() {
        return shopHouseDao;
    }

    @Override
    public List<Map<String, Object>> getUserShopHouseList(int userId) {
        return shopHouseDao.getUserShopHouseList(userId);
    }

    @Override
    @CacheEvict(value = "shophouse",key = "#p0.id")
    public int updateShopHouse(ShopHouse shopHouse) {
        return this.update(shopHouse);
    }

    @Override
    @Async
    public void sendSms(Integer checkStatus, String telephone, String reason) {
        SmsDemo.sendHouseSms(checkStatus,telephone,reason);
    }
}
