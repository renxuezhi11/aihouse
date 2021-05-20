package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihouseservice.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaDao areaDao;
    @Override
    public AreaDao initDao() {
        return areaDao;
    }
}
