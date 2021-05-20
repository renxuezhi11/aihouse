package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.CommunityComment;

import java.util.List;


/**
 *圈子信息评论表 dao
 */
@Mapper
public interface CommunityCommentDao  extends BaseDao<CommunityComment> {
	/**
	 * 根据房圈子删除圈子信息评论表
	 */
	public Integer deleteCommunityCommentByCommunity(CommunityComment communityComment);

	public List<CommunityComment> indexList(CommunityComment communityComment);

    Integer countCommentReplayByCommunityId(CommunityComment queryComment);

    Integer countCommentReplayByCommentId(CommunityComment queryComment);

    Integer deleteCommentReplyByComment(CommunityComment communityComment);

    public List<CommunityComment> selectCommentAndReplayByCommunityId(CommunityComment communityComment);

}
