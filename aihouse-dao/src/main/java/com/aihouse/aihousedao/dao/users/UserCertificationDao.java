package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserCertification;


/**
 *用户认证表 dao
 */
@Mapper
public interface UserCertificationDao  extends BaseDao<UserCertification> {




	/**
	 * 根据APP用户删除用户认证信息
	 */
	public Integer deleteUserCertificationByUsers(UserCertification userCertification);

}
