package com.aihouse.aihouseservice.newhouse;

import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.dao.newhouse.NewHouseDao;
import com.aihouse.aihouseservice.BaseService;

public interface NewHouseService extends BaseService<NewHouse,NewHouseDao>{

    public int updateNewhouse(NewHouse newHouse);

}
