package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihousedao.dao.SysUserDao;

import java.util.List;

public interface SysUserService extends BaseService<SysUser, SysUserDao> {

    public List<SysUser> selectByUsername(String name);
    int insert(SysUser sysUser, List<String> roles);

    int update(SysUser sysUser, List<String> roles);

    /**
     * 修改密码
     * @param user
     * @return
     */
    int updatePassword(SysUser user);
}
