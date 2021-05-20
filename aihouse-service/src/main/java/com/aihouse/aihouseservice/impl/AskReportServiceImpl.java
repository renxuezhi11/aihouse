package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AskReportDao;
import com.aihouse.aihouseservice.AskReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AskReportServiceImpl implements AskReportService {

    @Resource
    private AskReportDao askReportDao;

    @Override
    public AskReportDao initDao() {
        return this.askReportDao;
    }
}
