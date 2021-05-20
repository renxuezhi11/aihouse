package com.aihouse.aihouseservice.impl;
import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.bean.CommunityComment;
import com.aihouse.aihousedao.dao.CommunityCommentDao;
import com.aihouse.aihousedao.dao.CommunityDao;
import com.aihouse.aihouseservice.CommunityCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;


/**
 *圈子信息评论表 serverImpl
 */
@Service
@Transactional
public class CommunityCommentServiceImpl   implements CommunityCommentService {


	/**
	 * 圈子表
	 */
	@Resource
	private CommunityDao communityDao;

	/**
	 * 注入dao
	 */
	@Resource
	private CommunityCommentDao communityCommentDao;
	/**
	 * 初始化
	 */
	@Override
	public CommunityCommentDao initDao(){
		return communityCommentDao;
	}

	/**
	 * 级联查询(带分页) ���圈子--圈子信息评论表
	 */
	@Override
	public CommunityComment selectCommunityAndCommunityComment(CommunityComment communityComment){
		communityComment = this.selectAllByPaging(communityComment);
		if(communityComment!=null && communityComment.getRows()!=null){
			communityComment.getRows().forEach(t->{
				CommunityComment data= (CommunityComment) t;
				Community community=new Community();
				community.setId(data.getCommunityId());
				data.setCommunity(communityDao.selectByPrimaryKey(community));
			});
		}
		return communityComment;

	}


	/**
	 * 级联条件查询房圈子--圈子信息评论表
	 */
	@Override
	public List<CommunityComment> selectCommunityAndCommunityCommentByCondition(CommunityComment communityComment){
		List<CommunityComment> datas = this.selectByCondition(communityComment);
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
	public Integer deleteCommunityAndCommunityComment(CommunityComment communityComment){
		communityComment = communityCommentDao.selectByPrimaryKey(communityComment);
		if(communityComment!=null){
			Community community=new Community();
			community.setId(communityComment.getCommunityId());
			communityDao.deleteByPrimaryKey(community);
		}
		return communityCommentDao.deleteByPrimaryKey(communityComment);

	}

	@Override
	public List<CommunityComment> selectCommunityCommentByCommunityId(CommunityComment communityComment) {
		PageHelper.startPage(communityComment.getPage(),communityComment.getPageSize());
		List<CommunityComment> list = communityCommentDao.selectByCondition(communityComment);
		PageInfo pageInfo = new PageInfo(list);
		communityComment.setRows(list);
		communityComment.setTotal((new Long(pageInfo.getTotal())).intValue());
		return list;
	}

	@Override
	public Integer deleteCommentAndCommentReply(CommunityComment query) {
//		CommunityComment communityComment =new CommunityComment();
//		communityComment.setCommunityId(query.getId());
//		communityComment.setType(CommunityComment.TYPE_REPLAY);
//		communityCommentDao.deleteCommentReplyByComment(communityComment);
		return communityCommentDao.deleteByPrimaryKey(query);
	}

	@Override
	public List<CommunityComment> selectCommentAndReplayByCommunityId(CommunityComment communityComment) {
		PageHelper.startPage(communityComment.getPage(),communityComment.getPageSize());
		List<CommunityComment> list = communityCommentDao.selectCommentAndReplayByCommunityId(communityComment);
		PageInfo pageInfo = new PageInfo(list);
		communityComment.setRows(list);
		communityComment.setTotal((new Long(pageInfo.getTotal())).intValue());
		return list;
	}

	@Override
	public void deleteCommunityCommentByCommentId(Integer id) {
		CommunityComment communityComment =new CommunityComment();
		communityComment.setCommunityId(id);
		communityCommentDao.deleteCommunityCommentByCommunity(communityComment);
	}
}
