package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.users.UserFriendsDao;
import com.aihouse.aihouseservice.users.UserFriendsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {

    @Resource
    private UserFriendsDao userFriendsDao;

    @Override
    public UserFriendsDao initDao() {
        return this.userFriendsDao;
    }

    @Override
    public List<Map<String, Object>> getUserFriendsList(String imAccount,String letter) {
        return userFriendsDao.getUserFriendsList(imAccount,letter);
    }

    @Override
    public List<Map<String, Object>> getUserFriendsLetter(String imAccount) {
        return userFriendsDao.getUserFriendsLetter(imAccount);
    }

    @Override
    public List<Map<String, Object>> searchFriends(String keyword) {
        return userFriendsDao.searchFriends(keyword);
    }

    @Override
    public List<Map<String, Object>> getUserFriendsInfo(String[] member, int userId) {
        return userFriendsDao.getUserFriendsInfo(member,userId);
    }

    @Override
    public List<Map<String, Object>> getUserFriendsLsInfo(String[] memberLs) {
        return userFriendsDao.getUserFriendsLsInfo(memberLs);
    }
}
