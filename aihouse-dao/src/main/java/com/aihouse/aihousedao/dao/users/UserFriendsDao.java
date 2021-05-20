package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.UserFriends;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserFriendsDao extends BaseDao<UserFriends> {
    public List<Map<String,Object>> getUserFriendsList(@Param("imAccount") String imAccount,@Param("letter") String letter);

    public List<Map<String,Object>> getUserFriendsLetter(String imAccount);

    public List<Map<String,Object>> searchFriends(String keyword);

    public List<Map<String,Object>> getUserFriendsInfo(@Param("member") String[] member, @Param("userId") int userId);

    public List<Map<String,Object>> getUserFriendsLsInfo(@Param("memberLs") String[] memberLs);
}
