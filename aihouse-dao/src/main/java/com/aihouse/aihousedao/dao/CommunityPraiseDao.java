package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.CommunityPraise;


/**
 *房圈子点赞表 dao
 */
@Mapper
public interface CommunityPraiseDao  extends BaseDao<CommunityPraise> {
	/**
	 * 根据房圈子删除圈子信息评论表
	 */
	public Integer deleteCommunityPraiseByCommunity(CommunityPraise communityPraise);
}
