package com.aihouse.aihouseservice.renthouse;

import com.aihouse.aihousedao.bean.RentHouseImg;
import com.aihouse.aihousedao.dao.renthouse.SysRentHouseImgDao;
import com.aihouse.aihouseservice.BaseService;

public interface SysRentHouseImgService extends BaseService<RentHouseImg,SysRentHouseImgDao> {
    public Integer deleteAllByHouseId(Integer id);
}
