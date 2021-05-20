package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.dao.CommunityDao;

import java.util.List;
import java.util.Map;

/**
 * 圈子信息service
 */
public interface CommunityService extends BaseService<Community,CommunityDao> {



    /**
     * 级联查询(带分页) 房圈子--圈子信息评论表
     */
    public Community selectCommunityAndCommunityComment(Community community);
    /**
     * 级联查询(带分页) 房圈子--圈子信息评论表
     */
    public List<Community> selectCommunityAndCommunityCommentByCondition(Community community);
    /**
     * 级联删除(根据主键删除) 房圈子--圈子信息评论表
     */
    public Integer deleteCommunityAndCommunityComment(Community community);

    /**
     * 级联查询(带分页) 房圈子--圈子点赞表
     */
    public Community selectCommunityAndCommunityPraise(Community community);
    /**
     * 级联查询(带分页) 房圈子--圈子点赞表
     */
    public List<Community> selectCommunityAndCommunityPraiseByCondition(Community community);
    /**
     * 级联删除(根据主键删除) 房圈子--圈子点赞表
     */
    public Integer deleteCommunityAndCommunityPraise(Community community);

    public List<Community> selectAppIndexList(Community community);

    public List<Map<String,Object>> selectUserList(Integer userId);

    public List<Community> selectAppIndexScreenList(Community community);
}
