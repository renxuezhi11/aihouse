package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.HouseLimitDao;
import com.aihouse.aihouseservice.HouseLimitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class HouseLimitServiceImpl implements HouseLimitService {

    @Resource
    private HouseLimitDao houseLimitDao;

    @Override
    public HouseLimitDao initDao() {
        return this.houseLimitDao;
    }
}
