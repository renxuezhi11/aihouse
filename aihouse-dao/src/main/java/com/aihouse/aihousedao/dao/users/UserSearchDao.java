package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserSearch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *用户搜索记录表 dao
 */
@Mapper
public interface UserSearchDao  extends BaseDao<UserSearch> {




	/**
	 * 根据用户删除用户搜索记录
	 */
	public Integer deleteUserSearchByUsers(UserSearch userSearch);

    public void deleteAllUserSearch(@Param("type") Integer type, @Param("userId") int userId);

	public List<Map<String,Object>> queryUserSearch(UserSearch userSearch);

    public List<Map<String,Object>> queryHotSearch();
}
