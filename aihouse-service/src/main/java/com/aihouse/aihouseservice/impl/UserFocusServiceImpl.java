package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserFocus;
import java.util.List;

import com.aihouse.aihousedao.dao.users.UserFocusDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.users.UserFocusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户关注表 serverImpl
 */
@Service
@Transactional
public class UserFocusServiceImpl   implements UserFocusService {

	/**
	 * 二手房图片
	 */
	@Resource
	private UsersDao usersDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UserFocusDao userFocusDao;
	/**
	 * 初始化
	 */
	@Override
	public UserFocusDao initDao(){
		return userFocusDao;
	}


	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@Override
	public UserFocus selectUsersAndUserFocus(UserFocus userFocus){
		userFocus = this.selectAllByPaging(userFocus);
		if(userFocus!=null && userFocus.getRows()!=null){
			userFocus.getRows().forEach(t->{
				UserFocus data= (UserFocus) t;
				Users users=new Users();
				users.setId(data.getUserId());
				data.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return userFocus;

	}


	/**
	 * 级联条件查询二手房源详细信息--二手房图片
	 */
	@Override
	public List<UserFocus> selectUsersAndUserFocusByCondition(UserFocus userFocus){
		List<UserFocus> datas = this.selectByCondition(userFocus);
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
	 * 级联删除(根据主表删除) 二手房源详细信息--二手房图片
	 */
	@Override
	public Integer deleteUsersAndUserFocus(UserFocus userFocus){
		userFocus = userFocusDao.selectByPrimaryKey(userFocus);
		if(userFocus!=null){
			Users users=new Users();
			users.setId(userFocus.getUserId());
			usersDao.deleteByPrimaryKey(users);
		}
		return userFocusDao.deleteByPrimaryKey(userFocus);

	}

	@Override
	public Integer selectCount(Integer userId) {
		return userFocusDao.selectCount(userId);
	}
}
