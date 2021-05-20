package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.AskCommentPraise;


/**
 *问题回复点赞表 dao
 */
@Mapper
public interface AskCommentPraiseDao  extends BaseDao<AskCommentPraise> {

	/**
	 * 根据回复表删除回复点赞表
	 */
	public Integer deleteAskCommentPraiseByAppAskComent(AskCommentPraise askCommentPraise);

	public Integer deleteAppAskCommentPraiseByAskId(Long id);
}
