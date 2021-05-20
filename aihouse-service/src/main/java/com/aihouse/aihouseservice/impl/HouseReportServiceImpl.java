package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.HouseReportDao;
import com.aihouse.aihouseservice.HouseReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HouseReportServiceImpl implements HouseReportService {

    @Resource
    private HouseReportDao houseReportDao;

    @Override
    public HouseReportDao initDao() {
        return houseReportDao;
    }
}
