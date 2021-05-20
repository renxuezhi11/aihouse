package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.dao.newhouse.NewHouseStateDao;
import com.aihouse.aihouseservice.newhouse.NewHouseStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewHouseStateServiceImpl implements NewHouseStateService {

    @Resource
    private NewHouseStateDao newHouseStateDao;

    @Override
    public NewHouseStateDao initDao() {
        return this.newHouseStateDao;
    }
}
