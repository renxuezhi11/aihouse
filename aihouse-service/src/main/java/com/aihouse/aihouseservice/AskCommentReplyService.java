package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.AskCommentReply;
import com.aihouse.aihousedao.dao.AskCommentReplyDao;


/**
 *ahs_ask_comment_reply service
 */
public interface AskCommentReplyService extends BaseService<AskCommentReply,AskCommentReplyDao>{

	/**
	 * 级联查询(带分页) 回复表--回复评论表
	 */
	public AskCommentReply selectAppAskComentAndAskCommentReply(AskCommentReply askCommentReply);
	/**
	 * 级联条件查询 回复表--回复评论表
	 */
	public List<AskCommentReply> selectAppAskComentAndAskCommentReplyByCondition(AskCommentReply askCommentReply);
	/**
	 * 级联删除(根据主键删除) 回复表--回复评论表
	 */
	public Integer deleteAppAskComentAndAskCommentReply(AskCommentReply askCommentReply);


	public Integer deleteAppAskComentReplyByAskId(Long id);

	public Integer deleteAppAskCommentReplyByCommentId(Long id);
}
