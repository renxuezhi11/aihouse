package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AppAskComentDao;
import com.aihouse.aihousedao.bean.AppAskComent;
import com.aihouse.aihousedao.bean.AskCommentReply;
import java.util.List;
import com.aihouse.aihousedao.dao.AskCommentReplyDao;
import com.aihouse.aihouseservice.AskCommentReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *ahs_ask_comment_reply serverImpl
 */
@Service
@Transactional
public class AskCommentReplyServiceImpl   implements AskCommentReplyService {

	/**
	 * 回复评论表
	 */
	@Resource
	private AppAskComentDao appAskComentDao;
	/**
	 * 注入dao
	 */
	@Resource
	private AskCommentReplyDao askCommentReplyDao;
	/**
	 * 初始化
	 */
	@Override
	public AskCommentReplyDao initDao(){
		return askCommentReplyDao;
	}


	/**
	 * 级联查询(带分页) 回复表--回复评论表
	 */
	@Override
	public AskCommentReply selectAppAskComentAndAskCommentReply(AskCommentReply askCommentReply){
		askCommentReply = this.selectAllByPaging(askCommentReply);
		if(askCommentReply!=null && askCommentReply.getRows()!=null){
			askCommentReply.getRows().forEach(t->{
				AskCommentReply data= (AskCommentReply) t;
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setId(data.getCommentId());
				data.setAppAskComent(appAskComentDao.selectByPrimaryKey(appAskComent));
			});
		}
		return askCommentReply;

	}


	/**
	 * 级联条件查询回复表--回复评论表
	 */
	@Override
	public List<AskCommentReply> selectAppAskComentAndAskCommentReplyByCondition(AskCommentReply askCommentReply){
		List<AskCommentReply> datas = this.selectByCondition(askCommentReply);
		if(datas!=null){
			datas.forEach(t->{
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setId(t.getCommentId());
				t.setAppAskComent(appAskComentDao.selectByPrimaryKey(appAskComent));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 回复表--回复评论表
	 */
	@Override
	public Integer deleteAppAskComentAndAskCommentReply(AskCommentReply askCommentReply){
		askCommentReply = askCommentReplyDao.selectByPrimaryKey(askCommentReply);
		if(askCommentReply!=null){
			AppAskComent appAskComent=new AppAskComent();
			appAskComent.setId(askCommentReply.getCommentId());
			appAskComentDao.deleteByPrimaryKey(appAskComent);
		}
		return askCommentReplyDao.deleteByPrimaryKey(askCommentReply);

	}


	@Override
	public Integer deleteAppAskComentReplyByAskId(Long id) {
		return askCommentReplyDao.deleteAppAskComentReplyByAskId(id);
	}

	@Override
	public Integer deleteAppAskCommentReplyByCommentId(Long id) {
		AskCommentReply askCommentReply=new AskCommentReply();
		askCommentReply.setCommentId(id.intValue());
		return askCommentReplyDao.deleteAskCommentReplyByAppAskComent(askCommentReply);
	}
}
