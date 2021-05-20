package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao extends BaseDao<SysUser> {
    void insertBatch(@Param("userId") Integer userId, @Param("roles") List<String> roles);

    void deleteRoles(SysUser sysUser);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int updatePassword(SysUser user);
}
