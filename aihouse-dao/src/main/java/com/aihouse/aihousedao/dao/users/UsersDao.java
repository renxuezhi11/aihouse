package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.Users;

import java.util.List;
import java.util.Map;


/**
 *用户表 dao
 */

public interface UsersDao  extends BaseDao<Users> {


   public List<Map<String,Object>> queryBroker(Integer id);

   public List<Map<String,Object>> getNewHouseAllBroker(Integer id);

   public List<Map<String,Object>> getAllBroker();
}
