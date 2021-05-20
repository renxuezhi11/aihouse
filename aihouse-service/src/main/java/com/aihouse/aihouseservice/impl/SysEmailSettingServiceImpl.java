package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysEamilSettingDao;
import com.aihouse.aihouseservice.SysEmailSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysEmailSettingServiceImpl implements SysEmailSettingService {

    @Resource
    private SysEamilSettingDao sysEamilSettingDao;

    @Override
    public SysEamilSettingDao initDao() {
        return this.sysEamilSettingDao;
    }
}
