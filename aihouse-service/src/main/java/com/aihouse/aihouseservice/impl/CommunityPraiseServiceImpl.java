package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.CommunityDao;
import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.bean.CommunityPraise;
import java.util.List;
import com.aihouse.aihousedao.dao.CommunityPraiseDao;
import com.aihouse.aihouseservice.CommunityPraiseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *房圈子点赞表 serverImpl
 */
@Service
@Transactional
public class CommunityPraiseServiceImpl   implements CommunityPraiseService {

	/**
	 * 圈子信息评论表
	 */
	@Resource
	private CommunityDao communityDao;



	/**
	 * 注入dao
	 */
	@Resource
	private CommunityPraiseDao communityPraiseDao;
	/**
	 * 初始化
	 */
	@Override
	public CommunityPraiseDao initDao(){
		return communityPraiseDao;
	}


	/**
	 * 级联查询(带分页) 房圈子--圈子信息评论表
	 */
	@Override
	public CommunityPraise selectCommunityAndCommunityPraise(CommunityPraise communityPraise){
		communityPraise = this.selectAllByPaging(communityPraise);
		if(communityPraise!=null && communityPraise.getRows()!=null){
			communityPraise.getRows().forEach(t->{
				CommunityPraise data= (CommunityPraise) t;
				Community community=new Community();
				community.setId(data.getCommunityId());
				data.setCommunity(communityDao.selectByPrimaryKey(community));
			});
		}
		return communityPraise;
	}


	/**
	 * 级联条件查询房圈子--圈子信息评论表
	 */
	@Override
	public List<CommunityPraise> selectCommunityAndCommunityPraiseByCondition(CommunityPraise communityPraise){
		List<CommunityPraise> datas = this.selectByCondition(communityPraise);
		if(datas!=null){
			datas.forEach(t->{
				Community community=new Community();
				community.setId(t.getCommunityId());
				t.setCommunity(communityDao.selectByPrimaryKey(community));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 房圈子--圈子信息评论表
	 */
	@Override
	public Integer deleteCommunityAndCommunityPraise(CommunityPraise communityPraise){
		communityPraise = communityPraiseDao.selectByPrimaryKey(communityPraise);
		if(communityPraise!=null){
			Community community=new Community();
			community.setId(communityPraise.getCommunityId());
			communityDao.deleteByPrimaryKey(community);
		}
		return communityPraiseDao.deleteByPrimaryKey(communityPraise);

	}

	@Override
	public void deleteCommunityPraiseByCommentId(Integer id) {
		CommunityPraise communityPraise=new CommunityPraise();
		communityPraise.setCommunityId(id);
		communityPraiseDao.deleteCommunityPraiseByCommunity(communityPraise);
	}
}
