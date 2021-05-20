package com.aihouse.aihouseservice;



import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihousedao.dao.SysRoleDao;

import java.util.List;

public interface SysRoleService extends  BaseService<SysRole, SysRoleDao> {


    int insert(SysRole sysRole, List<String> auths);

    int update(SysRole sysRole, List<String> auths);

    /**
     * 查询用户已有的权限
     * @param sysUser
     * @return
     */
    List<SysRole> queryByUser(SysUser sysUser);
}