package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AppUpdateDao;
import com.aihouse.aihouseservice.AppUpdateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppUpdateServiceImpl implements AppUpdateService {

    @Resource
    private AppUpdateDao appUpdateDao;

    @Override
    public AppUpdateDao initDao() {
        return this.appUpdateDao;
    }
}
