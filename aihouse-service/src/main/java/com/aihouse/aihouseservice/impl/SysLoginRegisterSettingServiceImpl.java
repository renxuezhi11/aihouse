package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysLoginRegisterSettingDao;
import com.aihouse.aihouseservice.SysLoginRegisterSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLoginRegisterSettingServiceImpl implements SysLoginRegisterSettingService {

    @Resource
    private SysLoginRegisterSettingDao sysLoginRegisterSettingDao;

    @Override
    public SysLoginRegisterSettingDao initDao() {
        return this.sysLoginRegisterSettingDao;
    }
}
