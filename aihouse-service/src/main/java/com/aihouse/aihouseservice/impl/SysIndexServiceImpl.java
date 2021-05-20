package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.SysIndexDao;
import com.aihouse.aihouseservice.SysIndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SysIndexServiceImpl implements SysIndexService {

    @Resource
    private SysIndexDao sysIndexDao;
    @Override
    public Map<String, Object> getTongjiInfo() {
        return sysIndexDao.getTongjiInfo();
    }

    @Override
    public List<Map<String, Object>> getNewUserOfWeek() {
        return sysIndexDao.getNewUserOfWeek();
    }

    @Override
    public List<Map<String, Object>> getActiveUserOfWeek() {
        return sysIndexDao.getActiveUserOfWeek();
    }
}
