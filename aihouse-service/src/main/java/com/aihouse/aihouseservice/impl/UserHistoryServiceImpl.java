package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.bean.UserHistory;

import java.util.Date;
import java.util.List;

import com.aihouse.aihousedao.dao.users.UserHistoryDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.users.UserHistoryService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户浏览记录表 serverImpl
 */
@Service
@Transactional
public class UserHistoryServiceImpl   implements UserHistoryService {

	/**
	 * 用户浏览记录表
	 */
	@Resource
	private UsersDao usersDao;



	/**
	 * 注入dao
	 */
	@Resource
	private UserHistoryDao userHistoryDao;
	/**
	 * 初始化
	 */
	@Override
	public UserHistoryDao initDao(){
		return userHistoryDao;
	}


	/**
	 * 级联查询(带分页) 二手房源详细信息--用户浏览记录表
	 */
	@Override
	public UserHistory selectUsersAndUserHistory(UserHistory userHistory){
		userHistory = this.selectAllByPaging(userHistory);
		if(userHistory!=null && userHistory.getRows()!=null){
			userHistory.getRows().forEach(t->{
				UserHistory data= (UserHistory) t;
				Users users=new Users();
				users.setId(data.getUserId());
				data.setUsers(usersDao.selectByPrimaryKey(users));
			});
		}
		return userHistory;

	}


	/**
	 * 级联条件查询二手房源详细信息--用户浏览记录表
	 */
	@Override
	public List<UserHistory> selectUsersAndUserHistoryByCondition(UserHistory userHistory){
		List<UserHistory> datas = this.selectByCondition(userHistory);
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
	 * 级联删除(根据主表删除) 二手房源详细信息--用户浏览记录表
	 */
	@Override
	public Integer deleteUsersAndUserHistory(UserHistory userHistory){
		userHistory = userHistoryDao.selectByPrimaryKey(userHistory);
		if(userHistory!=null){
			Users users=new Users();
			users.setId(userHistory.getUserId());
			usersDao.deleteByPrimaryKey(users);
		}
		return userHistoryDao.deleteByPrimaryKey(userHistory);

	}

	/**
	 * 记录用户浏览记录
	 * @param userId
	 * @param historyType
	 * @param houseId
	 */
	@Override
	@Async
	public void userHistoryLog(Integer userId, Integer historyType, Integer houseId,String picture,double price,String unit,String area,String street,String title,String content) {
		UserHistory userHistory=new UserHistory();
		userHistory.setHouseId(houseId);
		userHistory.setUserId(userId);
		userHistory.setHistoryType(historyType);
		List<UserHistory> list=userHistoryDao.selectAll(userHistory);
		if(list!=null &&list.size()>0){//更新浏览记录
			userHistory=list.get(0);
			userHistory.setCreatetime(new Date());
			userHistory.setHousePicture(picture);
			userHistory.setHousePrice(price);
			userHistoryDao.update(userHistory);
		}else{//添加记录
			userHistory.setIsDel(0);
			userHistory.setHousePicture(picture);
			userHistory.setHousePrice(price);
			userHistory.setHouseUnit(unit);
			userHistory.setHouseArea(area);
			userHistory.setHouseStreet(street);
			userHistory.setHouseTitle(title);
			userHistory.setHouseContent(content);
			userHistoryDao.insert(userHistory);
		}
	}

	@Override
	public Integer deleteUserHistory(Integer[] ids, Integer userId) {
		return userHistoryDao.deleteUserHistory(ids,userId);
	}
}
