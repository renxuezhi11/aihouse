package com.aihouse.aihouseservice.officehouse;

import com.aihouse.aihousedao.bean.OfficeHouseImg;
import com.aihouse.aihousedao.dao.officehouse.OfficeHouseImgDao;
import com.aihouse.aihouseservice.BaseService;

/**
 * 写字楼图片service
 */
public interface OfficeHouseImgService extends BaseService<OfficeHouseImg,OfficeHouseImgDao> {

    public Integer deleteAllByHouseId(Integer id);
}
