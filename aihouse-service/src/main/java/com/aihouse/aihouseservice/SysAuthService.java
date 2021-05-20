package com.aihouse.aihouseservice;


import com.aihouse.aihousedao.bean.SysAuth;
import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.dao.SysAuthDao;

import java.util.List;

public interface SysAuthService extends  BaseService<SysAuth, SysAuthDao> {

    /**
     * 查询角色下的权限
     * @param sysRole
     * @return
     */
    List<SysAuth> queryByRole(SysRole sysRole);

    /**
     * 查询用户权限
     * @param userId
     * @return
     */
    List<SysAuth> queryByUser(Integer userId);

}