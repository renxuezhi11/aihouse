package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.users.UserFeedbackDao;
import com.aihouse.aihouseservice.users.UserFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Resource
    private UserFeedbackDao userFeedbackDao;
    @Override
    public UserFeedbackDao initDao() {
        return userFeedbackDao;
    }
}
