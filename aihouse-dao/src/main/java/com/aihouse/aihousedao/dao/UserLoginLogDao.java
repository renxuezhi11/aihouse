package com.aihouse.aihousedao.dao;
import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.UserLoginLog;


/**
 *ahs_user_login_log dao
 */
@Mapper
public interface UserLoginLogDao  extends BaseDao<UserLoginLog> {
}
