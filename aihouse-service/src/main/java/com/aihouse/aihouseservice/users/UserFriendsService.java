package com.aihouse.aihouseservice.users;

import com.aihouse.aihousedao.bean.UserFriends;
import com.aihouse.aihousedao.dao.users.UserFriendsDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface UserFriendsService extends BaseService<UserFriends,UserFriendsDao> {
    public List<Map<String,Object>> getUserFriendsList(String imAccount,String letter);

    public List<Map<String,Object>> getUserFriendsLetter(String imAccount);

    public List<Map<String,Object>> searchFriends(String keyword);

    public List<Map<String,Object>> getUserFriendsInfo(String[] member, int i);

    public List<Map<String,Object>> getUserFriendsLsInfo(String[] memberLs);
}
