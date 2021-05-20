package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihousedao.dao.SysAuthDao;
import com.aihouse.aihousedao.dao.SysRoleDao;
import com.aihouse.aihouseservice.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysAuthDao sysAuthDao;


    @Override
    public SysRoleDao initDao() {
        return sysRoleDao;
    }

    @Override
    @Transactional
    public int insert(SysRole sysRole, List<String> auths) {
        int insert = sysRoleDao.insert(sysRole);
        if(auths!=null && auths.size()>0){
            sysAuthDao.insertBatch(auths,sysRole.getId());
        }
        return insert;
    }

    @Override
    @Transactional
    public int update(SysRole sysRole, List<String> auths) {
        Integer update = sysRoleDao.update(sysRole);
        sysRoleDao.deletAuths(sysRole);
        if(auths!=null && auths.size()>0) {
            sysAuthDao.insertBatch(auths, sysRole.getId());
        }
        return update;
    }

    @Override
    public List<SysRole> queryByUser(SysUser sysUser) {
        return sysRoleDao.queryByUser(sysUser);
    }
}
