package com.aihouse.aihouseservice.users;





import java.util.List;
import java.util.Map;

import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.BaseService;


    /**
    *用户表 service
    */
    public interface UsersService extends BaseService<Users, UsersDao> {
	

	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	public Users selectUsersAndUserCertification(Users users);
	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	public List<Users> selectUsersAndUserCertificationByCondition(Users users);
	/**
	 * 级联删除(根据主键删除) APP用户--用户认证信息
	 */
	public Integer deleteUsersAndUserCertification(Users users);

	/**
	 * 级联查询(带分页) 用户详细信息--用户收藏
	 */
	public Users selectUsersAndUserCollect(Users users);
	/**
	 * 级联查询(带分页) 用户详细信息--用户收藏
	 */
	public List<Users> selectUsersAndUserCollectByCondition(Users users);
	/**
	 * 级������(根据主键删除) 用户详细信息--用户收藏
	 */
	public Integer deleteUsersAndUserCollect(Users users);

	/**
	 * 级联查询(带分页) 用户详细信息--用户关注
	 */
	public Users selectUsersAndUserFocus(Users users);
	/**
	 * 级联查询(带分页) 用户详细信息--用户关注
	 */
	public List<Users> selectUsersAndUserFocusByCondition(Users users);
	/**
	 * 级联删除(根据主键删除) 用户详细信息--用户关注
	 */
	public Integer deleteUsersAndUserFocus(Users users);

	/**
	 * 级联查询(带分页) 用户详细信息--用户浏览记录表
	 */
	public Users selectUsersAndUserHistory(Users users);
	/**
	 * 级联查询(带分页) 用户详细信息--用户浏览记录表
	 */
	public List<Users> selectUsersAndUserHistoryByCondition(Users users);
	/**
	 * 级联删除(根据主键删除) 用户详细信息--用户浏览记录表
	 */
	public Integer deleteUsersAndUserHistory(Users users);

	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	public Users selectUsersAndUserSearch(Users users);
	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	public List<Users> selectUsersAndUserSearchByCondition(Users users);
	/**
	 * 级联删除(根据主键删除) 用户--用户搜索记录
	 */
	public Integer deleteUsersAndUserSearch(Users users);

	public List<Map<String,Object>> selectAllBroker(Integer page,Integer pageSize);

}
