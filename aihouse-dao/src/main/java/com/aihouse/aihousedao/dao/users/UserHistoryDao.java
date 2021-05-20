package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserHistory;
import org.apache.ibatis.annotations.Param;


/**
 *用户浏览记录表 dao
 */
@Mapper
public interface UserHistoryDao  extends BaseDao<UserHistory> {




	/**
	 * 根据二手房源详细信息删除用户浏览记录表
	 */
	public Integer deleteUserHistoryByUsers(UserHistory userHistory);

    public Integer deleteUserHistory(@Param("ids")Integer[] ids, @Param("userId") Integer userId);
}
