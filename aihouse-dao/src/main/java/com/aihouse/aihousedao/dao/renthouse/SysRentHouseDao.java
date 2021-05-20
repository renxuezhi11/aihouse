package com.aihouse.aihousedao.dao.renthouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.RentHouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SysRentHouseDao extends BaseDao<RentHouse> {

    public Map<String,Object> selectByIdForSolr(Integer id);

    public Map<String,Object> queryInfo(Integer id);

    public HashMap<String,Object> queryDetail(Integer id);

    public List<Map<String,Object>> geUserRentHouseList(int userId);
}
