package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.bean.NewHouseCommentPraise;
import com.aihouse.aihousedao.dao.newhouse.NewHouseCommentDao;
import com.aihouse.aihousedao.dao.newhouse.NewHouseCommentPraiseDao;
import com.aihouse.aihouseservice.newhouse.NewHouseCommentPraiseService;
import com.aihouse.aihouseservice.newhouse.NewHouseCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewHouseCommentPraiseServiceImpl implements NewHouseCommentPraiseService {

    @Resource
    private NewHouseCommentPraiseDao newHouseCommentPraiseDao;


    @Override
    public NewHouseCommentPraiseDao initDao() {
        return this.newHouseCommentPraiseDao;
    }

    @Override
    public void deleteNewHouseCommentPraise(NewHouseCommentPraise newHouseCommentPraise) {
        this.newHouseCommentPraiseDao.deleteNewHouseCommentPraise(newHouseCommentPraise);
    }
}
