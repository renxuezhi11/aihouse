package com.aihouse.aihouseservice.shophouse.impl;

import com.aihouse.aihousedao.dao.shophouse.ShopHouseImgDao;
import com.aihouse.aihouseservice.shophouse.ShopHouseImgService;
import com.aihouse.aihouseservice.shophouse.ShopHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商铺出租图片实现
 */
@Service
public class ShopHouseImgServiceImpl implements ShopHouseImgService{

    @Resource
    private ShopHouseImgDao shopHouseImgDao;

    @Override
    public ShopHouseImgDao initDao() {
        return shopHouseImgDao;
    }


    @Override
    public Integer deleteAllByHouseId(Integer id) {
        return shopHouseImgDao.deleteAllByHouseId(id);
    }
}
