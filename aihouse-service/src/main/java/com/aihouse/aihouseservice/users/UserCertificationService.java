package com.aihouse.aihouseservice.users;

import java.util.List;
import com.aihouse.aihousedao.bean.UserCertification;
import com.aihouse.aihousedao.dao.users.UserCertificationDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *用户认证表 service
 */
public interface UserCertificationService extends BaseService<UserCertification, UserCertificationDao> {




	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	public UserCertification selectUsersAndUserCertification(UserCertification userCertification);
	/**
	 * 级联条件查询 APP用户--用户认证信息
	 */
	public List<UserCertification> selectUsersAndUserCertificationByCondition(UserCertification userCertification);
	/**
	 * 级联删除(根据主键删除) APP用户--用户认证信息
	 */
	public Integer deleteUsersAndUserCertification(UserCertification userCertification);
	/**
	 * 审核通过
	 */
	/**
	 * 审核驳回
	 */

	/**
	 * 发送实名验证短信通知
	 * @param type
	 * @param phone
	 */
	public void sendSms(Integer type,String phone);


}
