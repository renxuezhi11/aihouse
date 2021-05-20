package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.users.UserScreenDao;
import com.aihouse.aihouseservice.users.UserScreenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserScreenServiceImpl implements UserScreenService {

    @Resource
    private UserScreenDao userScreenDao;

    @Override
    public UserScreenDao initDao() {
        return this.userScreenDao;
    }
}
