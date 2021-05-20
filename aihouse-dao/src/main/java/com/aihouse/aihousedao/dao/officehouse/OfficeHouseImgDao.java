package com.aihouse.aihousedao.dao.officehouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.OfficeHouseImg;
import com.aihouse.aihousedao.bean.ShopHouseImg;

/**
 * 写字楼出租图片dao
 */
public interface OfficeHouseImgDao extends BaseDao<OfficeHouseImg>{
    public Integer deleteAllByHouseId(Integer id);
}
