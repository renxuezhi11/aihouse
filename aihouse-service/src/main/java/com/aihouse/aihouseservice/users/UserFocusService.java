package com.aihouse.aihouseservice.users;

import java.util.List;
import com.aihouse.aihousedao.bean.UserFocus;
import com.aihouse.aihousedao.dao.users.UserFocusDao;
import com.aihouse.aihouseservice.BaseService;

/**
 *用户关注表 service
 */
public interface UserFocusService extends BaseService<UserFocus, UserFocusDao> {




	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	public UserFocus selectUsersAndUserFocus(UserFocus userFocus);
	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	public List<UserFocus> selectUsersAndUserFocusByCondition(UserFocus userFocus);
	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	public Integer deleteUsersAndUserFocus(UserFocus userFocus);

    public Integer selectCount(Integer id);
}
