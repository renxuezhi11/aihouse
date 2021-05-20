package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserFocus;


/**
 *用户关注表 dao
 */
@Mapper
public interface UserFocusDao  extends BaseDao<UserFocus> {




	/**
	 * 根据用户房源详细信息删除用户关注
	 */
	public Integer deleteUserFocusByUsers(UserFocus userFocus);

	/**
	 * 获取用户的粉丝人数
	 * @param userId
	 * @return
	 */
    public Integer selectCount(Integer userId);
}
