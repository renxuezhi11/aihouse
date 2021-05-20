package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AppAskComentDao;
import com.aihouse.aihousedao.bean.AppAskComent;
import com.aihouse.aihousedao.bean.AskCommentPraise;
import java.util.List;

import com.aihouse.aihouseservice.AskCommentPraiseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.aihouse.aihousedao.dao.AskCommentPraiseDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *问题回复点赞表 serverImpl
 */
@Service
@Transactional
public class AskCommentPraiseServiceImpl   implements AskCommentPraiseService {

	/**
	 * 回复点赞表
	 */
	@Resource
	private AppAskComentDao appAskComentDao;



	/**
	 * 注入dao
	 */
	@Resource
	private AskCommentPraiseDao askCommentPraiseDao;
	/**
	 * 初始化
	 */
	@Override
	public AskCommentPraiseDao initDao(){
		return askCommentPraiseDao;
	}


	/**
	 * 级联查询(带分页) 回复表--回复点赞表
	 */
	@Override
	public AskCommentPraise selectAppAskComentAndAskCommentPraise(AskCommentPraise askCommentPraise){
		askCommentPraise = this.selectAllByPaging(askCommentPraise);
		if(askCommentPraise!=null && askCommentPraise.getRows()!=null){
			askCommentPraise.getRows().forEach(t->{
				AskCommentPraise data= (AskCommentPraise) t;
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setId(data.getAskCommentId());
				data.setAppAskComent(appAskComentDao.selectByPrimaryKey(appAskComent));
			});
		}
		return askCommentPraise;

	}


	/**
	 * 级联条件查询回复表--回复点赞表
	 */
	@Override
	public List<AskCommentPraise> selectAppAskComentAndAskCommentPraiseByCondition(AskCommentPraise askCommentPraise){
		List<AskCommentPraise> datas = this.selectByCondition(askCommentPraise);
		if(datas!=null){
			datas.forEach(t->{
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setId(t.getAskCommentId());
				t.setAppAskComent(appAskComentDao.selectByPrimaryKey(appAskComent));
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 回复表--回复点赞表
	 */
	@Override
	public Integer deleteAppAskComentAndAskCommentPraise(AskCommentPraise askCommentPraise){
		askCommentPraise = askCommentPraiseDao.selectByPrimaryKey(askCommentPraise);
		if(askCommentPraise!=null){
			AppAskComent appAskComent=new AppAskComent();
			appAskComent.setId(askCommentPraise.getAskCommentId());
			appAskComentDao.deleteByPrimaryKey(appAskComent);
		}
		return askCommentPraiseDao.deleteByPrimaryKey(askCommentPraise);

	}

	@Override
	public Integer deleteAppAskCommentPraiseByAskId(Long id) {
		return askCommentPraiseDao.deleteAppAskCommentPraiseByAskId(id);
	}

	@Override
	public Integer deleteAppAskCommentPraiseByCommentId(Long id) {
		AskCommentPraise askCommentPraise=new AskCommentPraise();
		askCommentPraise.setAskCommentId(id.intValue());
		return askCommentPraiseDao.deleteAskCommentPraiseByAppAskComent(askCommentPraise);
	}
}
