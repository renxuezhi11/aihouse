package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.AskCommentReply;


/**
 *ahs_ask_comment_reply dao
 */
@Mapper
public interface AskCommentReplyDao  extends BaseDao<AskCommentReply> {
	/**
	 * 根据回复表删除回复评论表
	 */
	public Integer deleteAskCommentReplyByAppAskComent(AskCommentReply askCommentReply);

    public Integer deleteAppAskComentReplyByAskId(Long id);
}
