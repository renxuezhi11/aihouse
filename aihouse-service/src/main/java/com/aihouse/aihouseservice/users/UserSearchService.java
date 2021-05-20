package com.aihouse.aihouseservice.users;

import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.bean.UserSearch;
import com.aihouse.aihousedao.dao.users.UserSearchDao;
import com.aihouse.aihouseservice.BaseService;


/**
 *用户搜索记录表 service
 */
public interface UserSearchService extends BaseService<UserSearch, UserSearchDao> {




	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	public UserSearch selectUsersAndUserSearch(UserSearch userSearch);
	/**
	 * 级联条件查询 用户--用户搜索记录
	 */
	public List<UserSearch> selectUsersAndUserSearchByCondition(UserSearch userSearch);
	/**
	 * 级联删除(根据主键删除) 用户--用户搜索记录
	 */
	public Integer deleteUsersAndUserSearch(UserSearch userSearch);

	public void deleteAllUserSearch(Integer type, int userId);

	/**
	 * 查询用户搜索记录--去重
	 * @param userSearch
	 * @return
	 */
	List<Map<String,Object>> queryUserSearch(UserSearch userSearch);

	public List<Map<String,Object>> queryHotSearch();
}
