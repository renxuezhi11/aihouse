package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.PriceNoticeDao;
import com.aihouse.aihouseservice.PriceNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PriceNoticeServiceImpl implements PriceNoticeService {

    @Resource
    private PriceNoticeDao priceNoticeDao;
    @Override
    public PriceNoticeDao initDao() {
        return this.priceNoticeDao;
    }
}
