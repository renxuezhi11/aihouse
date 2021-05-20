package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.CommunityPraise;
import com.aihouse.aihousedao.dao.CommunityPraiseDao;


/**
 *房圈子点赞表 service
 */
public interface CommunityPraiseService extends BaseService<CommunityPraise,CommunityPraiseDao>{




	/**
	 * 级联查询(带分页) 房圈子--圈子信息评论表
	 */
	public CommunityPraise selectCommunityAndCommunityPraise(CommunityPraise communityPraise);
	/**
	 * 级联条件查询 房圈子--圈子信息评论表
	 */
	public List<CommunityPraise> selectCommunityAndCommunityPraiseByCondition(CommunityPraise communityPraise);
	/**
	 * 级联删除(根据主键删除) 房圈子--圈子信息评论表
	 */
	public Integer deleteCommunityAndCommunityPraise(CommunityPraise communityPraise);

	public void deleteCommunityPraiseByCommentId(Integer id);
}
