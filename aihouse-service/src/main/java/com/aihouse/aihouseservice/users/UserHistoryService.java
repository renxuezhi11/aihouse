package com.aihouse.aihouseservice.users;

import java.util.List;
import com.aihouse.aihousedao.bean.UserHistory;
import com.aihouse.aihousedao.dao.users.UserHistoryDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *用户浏览记录表 service
 */
public interface UserHistoryService extends BaseService<UserHistory, UserHistoryDao> {




	/**
	 * 级联查询(带分页) 二手房源详细信息--用户浏览记录表
	 */
	public UserHistory selectUsersAndUserHistory(UserHistory userHistory);
	/**
	 * 级联条件查询 二手房源详细信息--用户浏览记录表
	 */
	public List<UserHistory> selectUsersAndUserHistoryByCondition(UserHistory userHistory);
	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--用户浏览记录表
	 */
	public Integer deleteUsersAndUserHistory(UserHistory userHistory);

	/**
	 * 记录用户浏览记录
	 * @param userId
	 * @param historyType
	 * @param houseId
	 */
	public void userHistoryLog(Integer userId,Integer historyType,Integer houseId,String picture,double price,String unit,String area,String street,String title,String content);

	public Integer deleteUserHistory(Integer[] ids,Integer userId);
}
