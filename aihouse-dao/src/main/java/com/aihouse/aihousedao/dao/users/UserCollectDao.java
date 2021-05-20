package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserCollect;
import org.apache.ibatis.annotations.Param;


/**
 *用户收藏表 dao
 */
@Mapper
public interface UserCollectDao  extends BaseDao<UserCollect> {




	/**
	 * 根据用户详细信息删除用户收藏
	 */
	public Integer deleteUserCollectByUsers(UserCollect userCollect);

	public Integer deleteUserCollect(@Param("ids")Integer[] ids, @Param("userId") Integer userId);
}
