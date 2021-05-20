package com.aihouse.aihouseservice.users;

import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.dao.users.UserSpreadLogDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.Map;

public interface UserSpreadLogService extends BaseService<UserSpreadLog,UserSpreadLogDao>{

    public Map<String,Object> selectCnt(UserSpreadLog userSpreadLog);
}
