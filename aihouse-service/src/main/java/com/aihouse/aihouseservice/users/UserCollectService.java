package com.aihouse.aihouseservice.users;

import java.util.List;
import com.aihouse.aihousedao.bean.UserCollect;
import com.aihouse.aihousedao.dao.users.UserCollectDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *用户收藏表 service
 */
public interface UserCollectService extends BaseService<UserCollect, UserCollectDao> {




	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	public UserCollect selectUsersAndUserCollect(UserCollect userCollect);
	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	public List<UserCollect> selectUsersAndUserCollectByCondition(UserCollect userCollect);
	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	public Integer deleteUsersAndUserCollect(UserCollect userCollect);

    public void deleteUserCollect(Integer[] ids, int userId);
}
