package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihousedao.dao.SysUserDao;
import com.aihouse.aihouseservice.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUserDao initDao() {
        return sysUserDao;
    }

    @Override
    public List<SysUser> selectByUsername(String name) {
        SysUser user=new SysUser();
        user.setLoginName(name);
        return sysUserDao.queryByCondition(user);
    }
    @Override
    public int insert(SysUser sysUser, List<String> roles) {
        int insert = sysUserDao.insert(sysUser);
        if(roles.size()>0){
            sysUserDao.insertBatch(sysUser.getId(),roles);
        }
        return insert;
    }

    @Override
    public int update(SysUser sysUser, List<String> roles) {
        Integer update = sysUserDao.update(sysUser);
        sysUserDao.deleteRoles(sysUser);
        if(roles!=null && roles.size()>0) {
            sysUserDao.insertBatch(sysUser.getId(),roles);
        }
        return update;
    }

    @Override
    public int updatePassword(SysUser user) {
        return sysUserDao.updatePassword(user);
    }
}
