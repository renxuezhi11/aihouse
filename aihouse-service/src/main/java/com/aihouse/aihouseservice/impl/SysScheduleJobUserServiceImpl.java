package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysScheduleJobUserDao;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysScheduleJobUserServiceImpl implements SysScheduleJobUserService {

    @Resource
    private SysScheduleJobUserDao sysScheduleJobUserDao;

    @Override
    public SysScheduleJobUserDao initDao() {
        return this.sysScheduleJobUserDao;
    }
}
