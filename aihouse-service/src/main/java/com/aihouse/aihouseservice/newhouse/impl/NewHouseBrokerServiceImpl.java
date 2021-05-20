package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.dao.newhouse.NewHouseBrokerDao;
import com.aihouse.aihouseservice.newhouse.NewHouseBrokerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NewHouseBrokerServiceImpl implements NewHouseBrokerService {

    @Resource
    private NewHouseBrokerDao newHouseBrokerDao;

    @Override
    public NewHouseBrokerDao initDao() {
        return this.newHouseBrokerDao;
    }


}
