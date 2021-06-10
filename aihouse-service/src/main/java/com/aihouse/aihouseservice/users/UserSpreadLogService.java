package com.aihouse.aihouseservice.users;

import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.dao.users.UserSpreadLogDao;
import com.aihouse.aihousedao.vo.UserSpreadLogVO;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface UserSpreadLogService extends BaseService<UserSpreadLog,UserSpreadLogDao>{

    public Map<String,Object> selectCnt(UserSpreadLog userSpreadLog);

    List<UserSpreadLogVO> getPersonSpreadLog(String userId);
}
