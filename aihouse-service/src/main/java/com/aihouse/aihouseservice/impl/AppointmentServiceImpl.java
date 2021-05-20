package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AppointmentDao;
import com.aihouse.aihouseservice.AppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Resource
    private AppointmentDao appointmentDao;

    @Override
    public AppointmentDao initDao() {
        return appointmentDao;
    }
}
