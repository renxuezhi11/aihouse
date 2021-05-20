package com.aihouse.aihouseservice.impl;
import com.aihouse.aihousedao.bean.UserLoginLog;
import com.aihouse.aihousedao.dao.UserLoginLogDao;
import com.aihouse.aihouseservice.UserLoginLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *ahs_user_login_log serverImpl
 */
@Service
@Transactional
public class UserLoginLogServiceImpl   implements UserLoginLogService {
	/**
	 * 注入dao
	 */
	@Resource
	private UserLoginLogDao userLoginLogDao;
	/**
	 * 初始化
	 */
	@Override
	public UserLoginLogDao initDao(){
		return userLoginLogDao;
	}

	@Override
	@Async
	public void insertLog(Integer userId,String ip,String deviceId){
		UserLoginLog userLoginLog=new UserLoginLog();
		userLoginLog.setUserId(userId);
		userLoginLog.setIp(ip);
		userLoginLog.setDeviceNumber(deviceId);
		this.insert(userLoginLog);
	}
}
