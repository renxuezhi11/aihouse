package com.aihouse.aihouseservice.shophouse;

import com.aihouse.aihousedao.bean.ShopHouseImg;
import com.aihouse.aihousedao.dao.shophouse.ShopHouseImgDao;
import com.aihouse.aihouseservice.BaseService;

/**
 * 商铺出租图片service
 */
public interface ShopHouseImgService  extends BaseService<ShopHouseImg,ShopHouseImgDao>{
    public Integer deleteAllByHouseId(Integer id);
}
