package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysLoginLogDao;
import com.aihouse.aihouseservice.SysLoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Resource
    private SysLoginLogDao sysLoginLogDao;


    @Override
    public SysLoginLogDao initDao() {
        return sysLoginLogDao;
    }

}
