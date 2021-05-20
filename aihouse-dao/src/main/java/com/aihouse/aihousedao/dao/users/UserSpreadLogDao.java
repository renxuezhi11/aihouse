package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.UserSpreadLog;

import java.util.Map;

public interface UserSpreadLogDao extends BaseDao<UserSpreadLog> {

   public Map<String,Object> selectCnt(UserSpreadLog userSpreadLog);
}
