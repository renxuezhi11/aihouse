package com.aihouse.aihouseservice.officehouse.impl;

import com.aihouse.aihousedao.dao.officehouse.OfficeHouseDao;
import com.aihouse.aihousedao.dao.officehouse.OfficeHouseImgDao;
import com.aihouse.aihouseservice.officehouse.OfficeHouseImgService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OfficeHouseImgServiceImpl implements OfficeHouseImgService{

    @Resource
    private OfficeHouseImgDao officeHouseImgDao;


    @Override
    public OfficeHouseImgDao initDao() {
        return officeHouseImgDao;
    }

    @Override
    public Integer deleteAllByHouseId(Integer id) {
        return officeHouseImgDao.deleteAllByHouseId(id);
    }
}
