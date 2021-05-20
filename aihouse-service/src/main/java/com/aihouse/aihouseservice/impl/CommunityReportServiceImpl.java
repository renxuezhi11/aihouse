package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.CommunityReportDao;
import com.aihouse.aihouseservice.CommunityReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class CommunityReportServiceImpl implements CommunityReportService {

    @Resource
    private CommunityReportDao communityReportDao;

    @Override
    public CommunityReportDao initDao() {
        return this.communityReportDao;
    }
}
