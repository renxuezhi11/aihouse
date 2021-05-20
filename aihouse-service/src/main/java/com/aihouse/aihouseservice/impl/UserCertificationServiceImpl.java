package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserCertification;

import java.util.HashMap;
import java.util.List;

import com.aihouse.aihousedao.dao.users.UserCertificationDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.users.UserCertificationService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;


/**
 *用户认证表 serverImpl
 */
@Service
@Transactional
public class UserCertificationServiceImpl   implements UserCertificationService {

	/**
	 * 用户认证信息
	 */
	@Resource
	private UsersDao usersDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UserCertificationDao userCertificationDao;
	/**
	 * 初始化
	 */
	@Override
	public UserCertificationDao initDao(){
		return userCertificationDao;
	}


	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	@Override
	public UserCertification selectUsersAndUserCertification(UserCertification userCertification){
		userCertification = this.selectAllByPaging(userCertification);
		if(userCertification!=null && userCertification.getRows()!=null){
			userCertification.getRows().forEach(t->{
				UserCertification data= (UserCertification) t;
				Users users=new Users();
				users.setId(data.getUserId());
				data.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return userCertification;

	}


	/**
	 * 级联条件查询APP用户--用户认证信息
	 */
	@Override
	public List<UserCertification> selectUsersAndUserCertificationByCondition(UserCertification userCertification){
		List<UserCertification> datas = this.selectByCondition(userCertification);
		if(datas!=null){
			datas.forEach(t->{
				Users users=new Users();
				users.setId(t.getUserId());
				t.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) APP用户--用户认证信息
	 */
	@Override
	public Integer deleteUsersAndUserCertification(UserCertification userCertification){
		userCertification = userCertificationDao.selectByPrimaryKey(userCertification);
		if(userCertification!=null){
			Users users=new Users();
			users.setId(userCertification.getUserId());
			usersDao.deleteByPrimaryKey(users);
		}
		return userCertificationDao.deleteByPrimaryKey(userCertification);

	}


	@Override
	@Async
	public void sendSms(Integer type, String phone) {
		String model="";
		if(type==1){
			model=SmsConstant.USER_CERTIFICA_OK;
		}else {
			model=SmsConstant.USER_CERTIFICA_NO;
		}
		if(phone!=null &&!phone.equals("")){
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("phoneName", phone);
			hashMap.put("SignName", SmsConstant.SIGN_NAME);
			hashMap.put("TemplateCode", model);
			try {
				SendSmsResponse res = SmsDemo.sendSms(hashMap);
				if (res.getCode() != null && res.getCode().equals("OK")) {
					System.out.println("success");
				} else {
					System.out.println(ResponseCode.SMS_SEND_ERROR);
				}
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
	}
}
