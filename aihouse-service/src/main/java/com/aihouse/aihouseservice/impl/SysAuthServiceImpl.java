package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.SysAuth;
import com.aihouse.aihousedao.bean.SysRole;
import com.aihouse.aihousedao.dao.SysAuthDao;
import com.aihouse.aihouseservice.SysAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SysAuthServiceImpl implements SysAuthService {

	@Resource
	private SysAuthDao sysAuthDao;


    @Override
    public SysAuthDao initDao() {
        return sysAuthDao;
    }

    @Override
    public List<SysAuth> queryByRole(SysRole sysRole) {
        return sysAuthDao.queryByRole(sysRole);
    }

    @Override
    public List<SysAuth> queryByUser(Integer userId) {
        return sysAuthDao.queryByUser(userId);
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(SysAuth sysAuth) {
        sysAuthDao.deleteByPrimaryKey(sysAuth);

        //清理所有有父id,但是不存在的记录
        sysAuthDao.deleteUnlinked();
        return 0;
    }
}
