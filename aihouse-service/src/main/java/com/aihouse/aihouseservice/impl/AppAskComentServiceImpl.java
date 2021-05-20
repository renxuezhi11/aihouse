package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.AskCommentPraise;
import com.aihouse.aihousedao.bean.AskCommentReply;
import com.aihouse.aihousedao.dao.AppAskDao;
import com.aihouse.aihousedao.bean.AppAsk;
import com.aihouse.aihousedao.bean.AppAskComent;
import java.util.List;
import com.aihouse.aihousedao.dao.AppAskComentDao;
import com.aihouse.aihousedao.dao.AskCommentPraiseDao;
import com.aihouse.aihousedao.dao.AskCommentReplyDao;
import com.aihouse.aihouseservice.AppAskComentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *提问回复表 serverImpl
 */
@Service
@Transactional
public class AppAskComentServiceImpl   implements AppAskComentService {


	/**
	 * 回复评论表
	 */
	@Resource
	private AskCommentReplyDao askCommentReplyDao;

	/**
	 * 提问回复表
	 */
	@Resource
	private AppAskDao appAskDao;

	/**
	 * 回复点赞表
	 */
	@Resource
	private AskCommentPraiseDao askCommentPraiseDao;

	/**
	 * 注入dao
	 */
	@Resource
	private AppAskComentDao appAskComentDao;
	/**
	 * 初始化
	 */
	@Override
	public AppAskComentDao initDao(){
		return appAskComentDao;
	}


	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	@Override
	public AppAskComent selectAppAskAndAppAskComent(AppAskComent appAskComent){
		appAskComent = this.selectAllByPaging(appAskComent);
		if(appAskComent!=null && appAskComent.getRows()!=null){
			appAskComent.getRows().forEach(t->{
				AppAskComent data= (AppAskComent) t;
				AppAsk appAsk=new AppAsk();
				appAsk.setId(data.getAskId());
				data.setAppAsk(appAskDao.selectByPrimaryKey(appAsk));
			});
		}
		return appAskComent;

	}


	/**
	 * 级联条件查询问答题--提问回复表
	 */
	@Override
	public List<AppAskComent> selectAppAskAndAppAskComentByCondition(AppAskComent appAskComent){
		List<AppAskComent> datas = this.selectByCondition(appAskComent);
		if(datas!=null){
			datas.forEach(t->{
				AppAsk appAsk=new AppAsk();
				appAsk.setId(t.getAskId());
				t.setAppAsk(appAskDao.selectByPrimaryKey(appAsk));
			});
		}
		return datas;

	}
	/**
	 * 级联删除(根据主表删除) 问答题--提问回复表
	 */
	@Override
	public Integer deleteAppAskAndAppAskComent(AppAskComent appAskComent){
		appAskComent = appAskComentDao.selectByPrimaryKey(appAskComent);
		if(appAskComent!=null){
			AppAsk appAsk=new AppAsk();
			appAsk.setId(appAskComent.getAskId());
			appAskDao.deleteByPrimaryKey(appAsk);
		}
		return appAskComentDao.deleteByPrimaryKey(appAskComent);

	}



	/**
	 * 级联查询(带分页) 回复表--回复点赞表
	 */
	@Override
	public AppAskComent selectAppAskComentAndAskCommentPraise(AppAskComent appAskComent){
		appAskComent = this.selectAllByPaging(appAskComent);
		if(appAskComent!=null && appAskComent.getRows()!=null){
			appAskComent.getRows().forEach(t->{
				AppAskComent data= (AppAskComent) t;
				AskCommentPraise askCommentPraise=new AskCommentPraise();
				askCommentPraise.setAskCommentId(data.getId());
				List<AskCommentPraise> lists = askCommentPraiseDao.selectByCondition(askCommentPraise);
				data.setAskCommentPraiseList(lists);
			});
		}
		return appAskComent;

	}


	/**
	 * 构建主表 级联条件查询 回复表--回复点赞表
	 */
	@Override
	public List<AppAskComent> selectAppAskComentAndAskCommentPraiseByCondition(AppAskComent appAskComent){
		List<AppAskComent> datas = this.selectByCondition(appAskComent);
		if(datas!=null){
			datas.forEach(t->{
				AskCommentPraise askCommentPraise=new AskCommentPraise();
				askCommentPraise.setAskCommentId(t.getId());
				List<AskCommentPraise> lists = askCommentPraiseDao.selectByCondition(askCommentPraise);
				t.setAskCommentPraiseList(lists);
			});
		}
		return datas;
	}

	@Override
	public List<AppAskComent> checkedAppAskComentAndAskCommentPraiseByCondition(AppAskComent appAskComent){
		List<AppAskComent> datas = this.selectByCondition(appAskComent);
		if(datas!=null){
			datas.forEach(t->{
				AskCommentPraise askCommentPraise=new AskCommentPraise();
				askCommentPraise.setAskCommentId(t.getId());
				askCommentPraise.setIsChecked(t.getIsChecked());
				List<AskCommentPraise> lists = askCommentPraiseDao.selectByCondition(askCommentPraise);
				t.setAskCommentPraiseList(lists);
			});
		}
		return datas;
	}


	/**
	 * 级联删除(根据主表删除) 回复表--回复点赞表
	 */
	@Override
	public Integer deleteAppAskComentAndAskCommentPraise(AppAskComent appAskComent){
		AskCommentPraise askCommentPraise=new AskCommentPraise();
		askCommentPraise.setAskCommentId(appAskComent.getId());
		askCommentPraiseDao.deleteAskCommentPraiseByAppAskComent(askCommentPraise);
		return appAskComentDao.deleteByPrimaryKey(appAskComent);

	}

	/**
	 * 级联查询(带分页) 回复表--回复评论表
	 */
	@Override
	public AppAskComent selectAppAskComentAndAskCommentReply(AppAskComent appAskComent){
		appAskComent = this.selectAllByPaging(appAskComent);
		if(appAskComent!=null && appAskComent.getRows()!=null){
			appAskComent.getRows().forEach(t->{
				AppAskComent data= (AppAskComent) t;
				AskCommentReply askCommentReply=new AskCommentReply();
				askCommentReply.setCommentId(data.getId());
				List<AskCommentReply> lists = askCommentReplyDao.selectByCondition(askCommentReply);
				data.setAskCommentReplyList(lists);
			});
		}
		return appAskComent;

	}


	/**
	 * 构建主表 级联条件查询 回复表--回复评论表
	 */
	@Override
	public List<AppAskComent> selectAppAskComentAndAskCommentReplyByCondition(AppAskComent appAskComent){
		List<AppAskComent> datas = this.selectByCondition(appAskComent);
		if(datas!=null){
			datas.forEach(t->{
				AskCommentReply askCommentReply=new AskCommentReply();
				askCommentReply.setCommentId(t.getId());
				List<AskCommentReply> lists = askCommentReplyDao.selectByCondition(askCommentReply);
				t.setAskCommentReplyList(lists);
			});
		}
		return datas;
	}

	@Override
	public List<AppAskComent> checkedAppAskComentAndAskCommentReplyByCondition(AppAskComent appAskComent){
		List<AppAskComent> datas = this.selectByCondition(appAskComent);
		if(datas!=null){
			datas.forEach(t->{
				AskCommentReply askCommentReply=new AskCommentReply();
				askCommentReply.setCommentId(t.getId());
				askCommentReply.setIsChecked(t.getIsChecked());
				List<AskCommentReply> lists = askCommentReplyDao.selectByCondition(askCommentReply);
				t.setAskCommentReplyList(lists);
			});
		}
		return datas;
	}

	@Override
	public List<AppAskComent> selectCommentForMyAnswer(AppAskComent appAskComent) {
		PageHelper.startPage(appAskComent.getPage(),appAskComent.getPageSize());
		List<AppAskComent> list = appAskComentDao.selectByCondition(appAskComent);
		PageInfo pageInfo = new PageInfo(list);
		appAskComent.setRows(list);
		appAskComent.setTotal((new Long(pageInfo.getTotal())).intValue());
		return list;
	}


	/**
	 * 级联删除(根据主表删除) 回复表--回复评论表
	 */
	@Override
	public Integer deleteAppAskComentAndAskCommentReply(AppAskComent appAskComent){
		AskCommentReply askCommentReply=new AskCommentReply();
		askCommentReply.setCommentId(appAskComent.getId());
		askCommentReplyDao.deleteAskCommentReplyByAppAskComent(askCommentReply);
		return appAskComentDao.deleteByPrimaryKey(appAskComent);

	}


	@Override
	public Integer deleteAppAskComentAskId(Long id) {
		AppAskComent appAskComent=new AppAskComent();
		appAskComent.setAskId(id);
		return appAskComentDao.deleteAppAskComentByAppAsk(appAskComent);
	}
}
