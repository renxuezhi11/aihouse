package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.AskCommentPraise;
import com.aihouse.aihousedao.dao.AskCommentPraiseDao;


/**
 *问题回复点赞表 service
 */
public interface AskCommentPraiseService extends BaseService<AskCommentPraise,AskCommentPraiseDao>{




	/**
	 * 级联查询(带分页) 回复表--回复点赞表
	 */
	public AskCommentPraise selectAppAskComentAndAskCommentPraise(AskCommentPraise askCommentPraise);
	/**
	 * 级联条件查询 回复表--回复点赞表
	 */
	public List<AskCommentPraise> selectAppAskComentAndAskCommentPraiseByCondition(AskCommentPraise askCommentPraise);
	/**
	 * 级联删除(根据主键删除) 回复表--回复点赞表
	 */
	public Integer deleteAppAskComentAndAskCommentPraise(AskCommentPraise askCommentPraise);

	public Integer deleteAppAskCommentPraiseByAskId(Long id);

	public Integer deleteAppAskCommentPraiseByCommentId(Long id);
}
