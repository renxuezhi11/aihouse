package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.UserSearch;
import com.aihouse.aihousedao.bean.UserHistory;
import com.aihouse.aihousedao.bean.UserFocus;
import com.aihouse.aihousedao.bean.UserCollect;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserCertification;
import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.dao.users.*;
import com.aihouse.aihouseservice.users.UsersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户表 serverImpl
 */
@Service
@Transactional
public class UsersServiceImpl   implements UsersService {

	/**
	 * 用户搜索记录
	 */
	@Resource
	private UserSearchDao userSearchDao;


	/**
	 * 用户浏览记录表
	 */
	@Resource
	private UserHistoryDao userHistoryDao;


	/**
	 * 二手房图片
	 */
	@Resource
	private UserFocusDao userFocusDao;


	/**
	 * 二手房图片
	 */
	@Resource
	private UserCollectDao userCollectDao;


	/**
	 * 用户认证信息
	 */
	@Resource
	private UserCertificationDao userCertificationDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UsersDao usersDao;
	/**
	 * 初始化
	 */
	@Override
	public UsersDao initDao(){
		return usersDao;
	}


	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	@Override
	public Users selectUsersAndUserCertification(Users users){
		users = this.selectAllByPaging(users);
		if(users!=null && users.getRows()!=null){
			users.getRows().forEach(t->{
				Users data= (Users) t;
				UserCertification userCertification=new UserCertification();
				userCertification.setUserId(data.getId());
				List<UserCertification> lists = userCertificationDao.selectByCondition(userCertification);
				if(lists!=null && lists.size()>0){
					data.setUserCertification(lists.get(0));
				}
			});
		}
		return users;

	}


	/**
	 * 级联条件查询 APP用户--用户认证信息
	 */
	@Override
	public List<Users> selectUsersAndUserCertificationByCondition(Users users){
		List<Users> datas = this.selectByCondition(users);
		if(datas!=null){
			datas.forEach(t->{
				UserCertification userCertification=new UserCertification();
				userCertification.setUserId(t.getId());
				List<UserCertification> lists = userCertificationDao.selectByCondition(userCertification);
				if(lists!=null && lists.size()>0){
					t.setUserCertification(lists.get(0));
				}
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) APP用户--用户认证信息
	 */
	@Override
	public Integer deleteUsersAndUserCertification(Users users){
		UserCertification userCertification=new UserCertification();
		userCertification.setUserId(users.getId());
		userCertificationDao.deleteUserCertificationByUsers(userCertification);
		return usersDao.deleteByPrimaryKey(users);

	}



	/**
	 * 级联查询(带分页) 用户详细信息--用户收藏
	 */
	@Override
	public Users selectUsersAndUserCollect(Users users){
		users = this.selectAllByPaging(users);
		if(users!=null && users.getRows()!=null){
			users.getRows().forEach(t->{
				Users data= (Users) t;
				UserCollect userCollect=new UserCollect();
				userCollect.setUserId(data.getId());
				List<UserCollect> lists = userCollectDao.selectByCondition(userCollect);
				data.setUserCollectList(lists);
			});
		}
		return users;

	}


	/**
	 * 构建主表 级联条件查询 用户详细信息--用户收藏
	 */
	@Override
	public List<Users> selectUsersAndUserCollectByCondition(Users users){
		List<Users> datas = this.selectByCondition(users);
		if(datas!=null){
			datas.forEach(t->{
				UserCollect userCollect=new UserCollect();
				userCollect.setUserId(t.getId());
				List<UserCollect> lists = userCollectDao.selectByCondition(userCollect);
				t.setUserCollectList(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 用户详细信息--用户收藏
	 */
	@Override
	public Integer deleteUsersAndUserCollect(Users users){
		UserCollect userCollect=new UserCollect();
		userCollect.setUserId(users.getId());
		userCollectDao.deleteUserCollectByUsers(userCollect);
		return usersDao.deleteByPrimaryKey(users);

	}



	/**
	 * 级联查询(带分页) 用户详细信息--用户关注
	 */
	@Override
	public Users selectUsersAndUserFocus(Users users){
		users = this.selectAllByPaging(users);
		if(users!=null && users.getRows()!=null){
			users.getRows().forEach(t->{
				Users data= (Users) t;
				UserFocus userFocus=new UserFocus();
				userFocus.setUserId(data.getId());
				List<UserFocus> lists = userFocusDao.selectByCondition(userFocus);
				data.setUserFocusList(lists);
			});
		}
		return users;

	}


	/**
	 * 构建主表 级联条件查询 用户详细信息--用户关注
	 */
	@Override
	public List<Users> selectUsersAndUserFocusByCondition(Users users){
		List<Users> datas = this.selectByCondition(users);
		if(datas!=null){
			datas.forEach(t->{
				UserFocus userFocus=new UserFocus();
				userFocus.setUserId(t.getId());
				List<UserFocus> lists = userFocusDao.selectByCondition(userFocus);
				t.setUserFocusList(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 用户详细信息--用户关注
	 */
	@Override
	public Integer deleteUsersAndUserFocus(Users users){
		UserFocus userFocus=new UserFocus();
		userFocus.setUserId(users.getId());
		userFocusDao.deleteUserFocusByUsers(userFocus);
		return usersDao.deleteByPrimaryKey(users);

	}



	/**
	 * 级联查询(带分页) 用户详细信息--用户浏览记录表
	 */
	@Override
	public Users selectUsersAndUserHistory(Users users){
		users = this.selectAllByPaging(users);
		if(users!=null && users.getRows()!=null){
			users.getRows().forEach(t->{
				Users data= (Users) t;
				UserHistory userHistory=new UserHistory();
				userHistory.setUserId(data.getId());
				List<UserHistory> lists = userHistoryDao.selectByCondition(userHistory);
				data.setUserHistoryList(lists);
			});
		}
		return users;

	}


	/**
	 * 构建主表 级联条件查询 用户详细信息--用户浏览记录表
	 */
	@Override
	public List<Users> selectUsersAndUserHistoryByCondition(Users users){
		List<Users> datas = this.selectByCondition(users);
		if(datas!=null){
			datas.forEach(t->{
				UserHistory userHistory=new UserHistory();
				userHistory.setUserId(t.getId());
				List<UserHistory> lists = userHistoryDao.selectByCondition(userHistory);
				t.setUserHistoryList(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 用户详细信息--用户浏览记录表
	 */
	@Override
	public Integer deleteUsersAndUserHistory(Users users){
		UserHistory userHistory=new UserHistory();
		userHistory.setUserId(users.getId());
		userHistoryDao.deleteUserHistoryByUsers(userHistory);
		return usersDao.deleteByPrimaryKey(users);

	}



	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	@Override
	public Users selectUsersAndUserSearch(Users users){
		users = this.selectAllByPaging(users);
		if(users!=null && users.getRows()!=null){
			users.getRows().forEach(t->{
				Users data= (Users) t;
				UserSearch userSearch=new UserSearch();
				userSearch.setUserId(data.getId());
				List<UserSearch> lists = userSearchDao.selectByCondition(userSearch);
				data.setUserSearchList(lists);
			});
		}
		return users;

	}


	/**
	 * 构建主表 级联条件查询 用户--用户搜索记录
	 */
	@Override
	public List<Users> selectUsersAndUserSearchByCondition(Users users){
		List<Users> datas = this.selectByCondition(users);
		if(datas!=null){
			datas.forEach(t->{
				UserSearch userSearch=new UserSearch();
				userSearch.setUserId(t.getId());
				List<UserSearch> lists = userSearchDao.selectByCondition(userSearch);
				t.setUserSearchList(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 用户--用户搜索记录
	 */
	@Override
	public Integer deleteUsersAndUserSearch(Users users){
		UserSearch userSearch=new UserSearch();
		userSearch.setUserId(users.getId());
		userSearchDao.deleteUserSearchByUsers(userSearch);
		return usersDao.deleteByPrimaryKey(users);

	}


	@Override
	public List<Map<String, Object>> selectAllBroker(Integer page,Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Map<String, Object>> lists = usersDao.getAllBroker();
		PageInfo pageInfo = new PageInfo(lists);
		return  lists;
	}
}
