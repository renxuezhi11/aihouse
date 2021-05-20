package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.CommunityComment;
import com.aihouse.aihousedao.dao.CommunityCommentDao;


/**
 *圈子信息评论表 service
 */
public interface CommunityCommentService extends BaseService<CommunityComment,CommunityCommentDao>{

	/**
	 * 级联查询(带分页) 房圈子--圈子信息评论表
	 */
	public CommunityComment selectCommunityAndCommunityComment(CommunityComment communityComment);
	/**
	 * 级联条件查询 房圈子--圈子信息评论表
	 */
	public List<CommunityComment> selectCommunityAndCommunityCommentByCondition(CommunityComment communityComment);
	/**
	 * 级联删除(根据主键删除) 房圈子--圈子信息评论表
	 */
	public Integer deleteCommunityAndCommunityComment(CommunityComment communityComment);

	/**
	 * 根据圈子ID查询评论(带分页)
	 * */
	List<CommunityComment> selectCommunityCommentByCommunityId(CommunityComment communityComment);

	public Integer deleteCommentAndCommentReply(CommunityComment query);

	List<CommunityComment> selectCommentAndReplayByCommunityId(CommunityComment communityComment);

	/**
	 * 删除该圈子的所有评论
	 * @param id
	 */
	public void deleteCommunityCommentByCommentId(Integer id);
}
