package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserCollect;
import java.util.List;

import com.aihouse.aihousedao.dao.users.UserCollectDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.users.UserCollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户收藏表 serverImpl
 */
@Service
@Transactional
public class UserCollectServiceImpl   implements UserCollectService {

	/**
	 * 二手房图片
	 */
	@Resource
	private UsersDao usersDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UserCollectDao userCollectDao;
	/**
	 * 初始化
	 */
	@Override
	public UserCollectDao initDao(){
		return userCollectDao;
	}


	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@Override
	public UserCollect selectUsersAndUserCollect(UserCollect userCollect){
		userCollect = this.selectAllByPaging(userCollect);
		if(userCollect!=null && userCollect.getRows()!=null){
			userCollect.getRows().forEach(t->{
				UserCollect data= (UserCollect) t;
				Users users=new Users();
				users.setId(data.getUserId());
				data.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return userCollect;

	}


	/**
	 * 级联条件查询二手房源详细信息--二手房图片
	 */
	@Override
	public List<UserCollect> selectUsersAndUserCollectByCondition(UserCollect userCollect){
		List<UserCollect> datas = this.selectByCondition(userCollect);
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
	public Integer deleteUsersAndUserCollect(UserCollect userCollect){
		userCollect = userCollectDao.selectByPrimaryKey(userCollect);
		if(userCollect!=null){
			Users users=new Users();
			users.setId(userCollect.getUserId());
			usersDao.deleteByPrimaryKey(users);
		}
		return userCollectDao.deleteByPrimaryKey(userCollect);

	}


	@Override
	public void deleteUserCollect(Integer[] ids, int userId) {
			userCollectDao.deleteUserCollect(ids,userId);
	}
}
