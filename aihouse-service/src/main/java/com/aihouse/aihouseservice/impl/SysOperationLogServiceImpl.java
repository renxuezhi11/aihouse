package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysOperationLogDao;
import com.aihouse.aihouseservice.SysOperationLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SysOperationLogServiceImpl implements SysOperationLogService {

    @Resource
    private SysOperationLogDao sysOperationLogDao;


    @Override
    public SysOperationLogDao initDao() {
        return sysOperationLogDao;
    }
}
