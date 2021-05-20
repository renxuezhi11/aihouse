package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserSearch;
import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.dao.users.UserSearchDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户搜索记录表 serverImpl
 */
@Service
@Transactional
public class UserSearchServiceImpl   implements UserSearchService {

	/**
	 * 用户搜索记录
	 */
	@Resource
	private UsersDao usersDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UserSearchDao userSearchDao;
	/**
	 * 初始化
	 */
	@Override
	public UserSearchDao initDao(){
		return userSearchDao;
	}


	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	@Override
	public UserSearch selectUsersAndUserSearch(UserSearch userSearch){
		userSearch = this.selectAllByPaging(userSearch);
		if(userSearch!=null && userSearch.getRows()!=null){
			userSearch.getRows().forEach(t->{
				UserSearch data= (UserSearch) t;
				Users users=new Users();
				users.setId(data.getUserId());
				data.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return userSearch;

	}


	/**
	 * 级联条件查询用户--用户搜索记录
	 */
	@Override
	public List<UserSearch> selectUsersAndUserSearchByCondition(UserSearch userSearch){
		List<UserSearch> datas = this.selectByCondition(userSearch);
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
	 * 级联删除(根据主表删除) 用户--用户搜索记录
	 */
	@Override
	public Integer deleteUsersAndUserSearch(UserSearch userSearch){
		userSearch = userSearchDao.selectByPrimaryKey(userSearch);
	/*	if(userSearch!=null){
			Users users=new Users();
			users.setId(userSearch.getUserId());
			usersDao.deleteByPrimaryKey(users);
		}*/
		return userSearchDao.deleteByPrimaryKey(userSearch);

	}


	@Override
	public void deleteAllUserSearch(Integer type, int userId) {
		userSearchDao.deleteAllUserSearch(type,userId);
	}

	@Override
	public List<Map<String, Object>> queryUserSearch(UserSearch userSearch) {
		return userSearchDao.queryUserSearch(userSearch);
	}

	@Override
	public List<Map<String, Object>> queryHotSearch() {
		return userSearchDao.queryHotSearch();
	}
}
