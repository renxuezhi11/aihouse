package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.dao.users.UserSpreadLogDao;
import com.aihouse.aihousedao.vo.UserSpreadLogVO;
import com.aihouse.aihouseservice.users.UserSpreadLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service
public class UserSpreadLogServiceImpl implements UserSpreadLogService {

    @Resource
    private UserSpreadLogDao userSpreadLogDao;

    @Override
    public UserSpreadLogDao initDao() {
        return this.userSpreadLogDao;
    }

    @Override
    public Map<String, Object> selectCnt(UserSpreadLog userSpreadLog) {
        return userSpreadLogDao.selectCnt(userSpreadLog);
    }

    @Override
    public List<UserSpreadLogVO> getPersonSpreadLog(String userId) {
        return userSpreadLogDao.getPersonSpreadLog(userId);
    }
}
