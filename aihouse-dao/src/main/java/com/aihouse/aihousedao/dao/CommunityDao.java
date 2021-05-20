package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.Community;

import java.util.List;
import java.util.Map;

/**
 * 圈子信息dao
 */
public interface CommunityDao extends BaseDao<Community> {

    List<Community> selectAppIndexList(Community community);

    List<Map<String,Object>> selectUserList(Integer userId);


    List<Community> selectAppIndexScreenList(Community community);
}
