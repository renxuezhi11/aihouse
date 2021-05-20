package com.aihouse.aihouseservice.renthouse.impl;

import com.aihouse.aihousedao.dao.renthouse.SysRentHouseImgDao;
import com.aihouse.aihouseservice.renthouse.SysRentHouseImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 房源图片
 */
@Service
public class SysRentHouseImgServiceImpl implements SysRentHouseImgService {

    @Resource
    private  SysRentHouseImgDao sysRentHouseImgDao;

    @Override
    public SysRentHouseImgDao initDao() {
        return sysRentHouseImgDao;
    }

    @Override
    public Integer deleteAllByHouseId(Integer id) {
        return sysRentHouseImgDao.deleteAllByHouseId(id);
    }
}
