package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.dao.newhouse.NewHouseDao;
import com.aihouse.aihouseservice.newhouse.NewHouseService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class NewHouseServiceImpl implements NewHouseService {

    @Resource
    private  NewHouseDao newHouseDao;

    @Override
    public NewHouseDao initDao() {
        return newHouseDao;
    }

    @Override
    @CacheEvict(value = "newhouse",key = "#p0.id")
    public int updateNewhouse(NewHouse newHouse) {
        return this.update(newHouse);
    }
}
