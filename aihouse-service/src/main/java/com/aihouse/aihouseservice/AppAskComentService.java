package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.AppAskComent;
import com.aihouse.aihousedao.dao.AppAskComentDao;


/**
 *提问回复表 service
 */
public interface AppAskComentService extends BaseService<AppAskComent,AppAskComentDao>{




	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	public AppAskComent selectAppAskAndAppAskComent(AppAskComent appAskComent);
	/**
	 * 级联条件查询 问答题--提问回复表
	 */
	public List<AppAskComent> selectAppAskAndAppAskComentByCondition(AppAskComent appAskComent);
	/**
	 * 级联删除(根据主键删除) 问答题--提问回复表
	 */
	public Integer deleteAppAskAndAppAskComent(AppAskComent appAskComent);

	/**
	 * 级联查询(带分页) 回复表--回复点赞表
	 */
	public AppAskComent selectAppAskComentAndAskCommentPraise(AppAskComent appAskComent);
	/**
	 * 级联查询(带分页) 回复表--回复点赞表
	 */
	public List<AppAskComent> selectAppAskComentAndAskCommentPraiseByCondition(AppAskComent appAskComent);
	/**
	 * 级联删除(根据主键删除) 回复表--回复点赞表
	 */
	public Integer deleteAppAskComentAndAskCommentPraise(AppAskComent appAskComent);

	/**
	 * 级联查询(带分页) 回复表--回复评论表
	 */
	public AppAskComent selectAppAskComentAndAskCommentReply(AppAskComent appAskComent);
	/**
	 * 级联查询(带分页) 回复表--回复评论表
	 */
	public List<AppAskComent> selectAppAskComentAndAskCommentReplyByCondition(AppAskComent appAskComent);
	/**
	 * 级联删除(根据主键删除) 回复表--回复评论表
	 */
	public Integer deleteAppAskComentAndAskCommentReply(AppAskComent appAskComent);

	public List<AppAskComent> checkedAppAskComentAndAskCommentPraiseByCondition(AppAskComent appAskComent);

	public List<AppAskComent> checkedAppAskComentAndAskCommentReplyByCondition(AppAskComent appAskComent);

    List<AppAskComent> selectCommentForMyAnswer(AppAskComent appAskComent);

	public Integer deleteAppAskComentAskId(Long id);
}
