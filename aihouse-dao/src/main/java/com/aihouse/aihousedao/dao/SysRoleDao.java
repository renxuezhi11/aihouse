package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.bean.SysUser;

import java.util.List;

public interface SysRoleDao extends BaseDao<SysRole> {
    /**
     * 删除权限
     * @param sysRole
     */
    void deletAuths(SysRole sysRole);

    /**
     * 查询用户已有的权限
     * @param sysUser
     * @return
     */
    List<SysRole> queryByUser(SysUser sysUser);
}