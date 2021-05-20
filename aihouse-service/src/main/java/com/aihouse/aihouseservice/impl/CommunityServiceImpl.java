package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.CommunityPraiseDao;
import com.aihouse.aihousedao.bean.CommunityPraise;

import com.aihouse.aihousedao.dao.CommunityCommentDao;
import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.bean.CommunityComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aihouse.aihouseservice.CommunityService;
import com.aihouse.aihousedao.dao.CommunityDao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 圈子信息serviceimpl
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    /**
     * 圈子点赞表
     */
    @Resource
    private CommunityPraiseDao communityPraiseDao;
    /**
     * 圈子信息评论表
     */
    @Resource
    private CommunityCommentDao communityCommentDao;
    /**
     * 注入dao
     */
    @Resource
    private CommunityDao communityDao;
    /**
     * 初始化
     */
    @Override
    public CommunityDao initDao(){
        return communityDao;
    }


    /**
     * 级联查询(带分页) 房圈子--圈子信息评论表
     */
    @Override
    public Community selectCommunityAndCommunityComment(Community community){
        community = this.selectAllByPaging(community);
        if(community!=null && community.getRows()!=null){
            community.getRows().forEach(t->{
                Community data= (Community) t;
                CommunityComment communityComment=new CommunityComment();
                communityComment.setCommunityId(data.getId());
                List<CommunityComment> lists = communityCommentDao.selectByCondition(communityComment);
                data.setCommunityCommentList(lists);
            });
        }
        return community;

    }


    /**
     * 构建主表 级联条件查询 房圈子--圈子信息评论表
     */
    @Override
    public List<Community> selectCommunityAndCommunityCommentByCondition(Community community){
        List<Community> datas = this.selectByCondition(community);
        if(datas!=null){
            datas.forEach(t->{
                CommunityComment communityComment=new CommunityComment();
                communityComment.setCommunityId(t.getId());
                List<CommunityComment> lists = communityCommentDao.selectByCondition(communityComment);
                t.setCommunityCommentList(lists);
            });
        }
        return datas;

    }


    /**
     * 级联删除(根据主表删除) 房圈子--圈子信息评论表
     */
    @Override
    public Integer deleteCommunityAndCommunityComment(Community community){
        CommunityComment communityComment=new CommunityComment();
        communityComment.setCommunityId(community.getId());
        communityCommentDao.deleteCommunityCommentByCommunity(communityComment);
        return communityDao.deleteByPrimaryKey(community);

    }



    /**
     * 级联查询(带分页) 房圈子--圈子信息评论表
     */
    @Override
    public Community selectCommunityAndCommunityPraise(Community community){
        community = this.selectAllByPaging(community);
        if(community!=null && community.getRows()!=null){
            community.getRows().forEach(t->{
                Community data= (Community) t;
                CommunityPraise communityPraise=new CommunityPraise();
                communityPraise.setCommunityId(data.getId());
                List<CommunityPraise> lists = communityPraiseDao.selectByCondition(communityPraise);
                data.setCommunityPraiseList(lists);
            });
        }
        return community;

    }


    /**
     * 构建主表 级联条件查询 房圈子--圈子信息评论表
     */
    @Override
    public List<Community> selectCommunityAndCommunityPraiseByCondition(Community community){
        List<Community> datas = this.selectByCondition(community);
        if(datas!=null){
            datas.forEach(t->{
                CommunityPraise communityPraise=new CommunityPraise();
                communityPraise.setCommunityId(t.getId());
                List<CommunityPraise> lists = communityPraiseDao.selectByCondition(communityPraise);
                t.setCommunityPraiseList(lists);
            });
        }
        return datas;

    }


    /**
     * 级联删除(根据主表删除) 房圈子--圈子信息评论表
     */
    @Override
    public Integer deleteCommunityAndCommunityPraise(Community community){
        CommunityPraise communityPraise=new CommunityPraise();
        communityPraise.setCommunityId(community.getId());
        communityPraiseDao.deleteCommunityPraiseByCommunity(communityPraise);
        return communityDao.deleteByPrimaryKey(community);

    }

    @Override
    public List<Community> selectAppIndexList(Community community) {
        List<Community> lists = new ArrayList<Community>();
        try {
            PageHelper.startPage(community.getPage(),community.getPageSize());
            lists = communityDao.selectByCondition(community);
            PageInfo pageInfo = new PageInfo(lists);
            community.setRows(lists);
            community.setTotal((new Long(pageInfo.getTotal())).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return lists;
    }

    @Override
    public List<Community> selectAppIndexScreenList(Community community) {
        List<Community> lists = new ArrayList<Community>();
        try {
            PageHelper.startPage(community.getPage(),community.getPageSize());
            lists = communityDao.selectAppIndexScreenList(community);
            PageInfo pageInfo = new PageInfo(lists);
            community.setRows(lists);
            community.setTotal((new Long(pageInfo.getTotal())).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return lists;
    }

    @Override
    public List<Map<String,Object>> selectUserList(Integer userId) {
        return communityDao.selectUserList(userId);
    }
}
