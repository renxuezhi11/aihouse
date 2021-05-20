package com.aihouse.aihousedao.dao.renthouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.RentHouseImg;

public interface SysRentHouseImgDao extends BaseDao<RentHouseImg> {

    public Integer deleteAllByHouseId(Integer id);
}
