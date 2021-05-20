package com.aihouse.aihouseservice;
import com.aihouse.aihousedao.bean.UserLoginLog;
import com.aihouse.aihousedao.dao.UserLoginLogDao;


/**
 *ahs_user_login_log service
 */
public interface UserLoginLogService extends BaseService<UserLoginLog,UserLoginLogDao>{

    public void insertLog(Integer userId,String ip,String deviceId);

}
