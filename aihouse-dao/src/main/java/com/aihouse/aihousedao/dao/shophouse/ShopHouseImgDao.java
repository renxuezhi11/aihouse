package com.aihouse.aihousedao.dao.shophouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.ShopHouseImg;

/**
 * 商铺出租图片dao
 */
public interface ShopHouseImgDao extends BaseDao<ShopHouseImg>{
   public  Integer deleteAllByHouseId(Integer id);
}
