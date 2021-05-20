package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.dao.AppAskComentDao;
import com.aihouse.aihousedao.bean.AppAsk;
import com.aihouse.aihousedao.bean.AppAskComent;
import java.util.List;

import com.aihouse.aihouseservice.AppAskService;
import com.aihouse.aihousedao.dao.AppAskDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *提问表 serverImpl
 */
@Service
@Transactional
public class AppAskServiceImpl   implements AppAskService {

	/**
	 * 提问回复表
	 */
	@Resource
	private AppAskComentDao appAskComentDao;



	/**
	 * 注入dao
	 */
	@Resource
	private AppAskDao appAskDao;
	/**
	 * 初始化
	 */
	@Override
	public AppAskDao initDao(){
		return appAskDao;
	}


	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	@Override
	public AppAsk selectAppAskAndAppAskComent(AppAsk appAsk){
		appAsk = this.selectAllByPaging(appAsk);
		if(appAsk!=null && appAsk.getRows()!=null){
			appAsk.getRows().forEach(t->{
				AppAsk data= (AppAsk) t;
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setAskId(data.getId());
				List<AppAskComent> lists = appAskComentDao.selectByCondition(appAskComent);
				data.setAppAskComentList(lists);
			});
		}
		return appAsk;

	}


	/**
	 * 构建主表 级联条件查询 问答题--提问回复表
	 */
	@Override
	public List<AppAsk> selectAppAskAndAppAskComentByCondition(AppAsk appAsk){
		List<AppAsk> datas = this.selectByCondition(appAsk);
		if(datas!=null){
			datas.forEach(t->{
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setAskId(t.getId());
				List<AppAskComent> lists = appAskComentDao.selectByCondition(appAskComent);
				t.setAppAskComentList(lists);
			});
		}
		return datas;

	}


	/**
	 * 级联删除(根据主表删除) 问答题--提问回复表
	 */
	@Override
	public Integer deleteAppAskAndAppAskComent(AppAsk appAsk){
		AppAskComent appAskComent=new AppAskComent();
		appAskComent.setAskId(appAsk.getId());
		appAskComentDao.deleteAppAskComentByAppAsk(appAskComent);
		return appAskDao.deleteByPrimaryKey(appAsk);

	}

	/**
	 * 查询未查看回复的条数
	 */
	@Override
	public List<AppAsk> selectUncheckedAppAskComentByCondition(AppAsk appAsk){
		List<AppAsk> datas = this.selectByCondition(appAsk);
		if(datas!=null){
			datas.forEach(t->{
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setAskId(t.getId());
				appAskComent.setIsChecked(AppAskComent.UNCHECKED);
				List<AppAskComent> lists = appAskComentDao.selectByCondition(appAskComent);
				t.setAppAskComentList(lists);
			});
		}
		return datas;

	}

	/**
	 * 构建主表 级联条件查询 问答题--提问回复表
	 */
	@Override
	public List<AppAsk> selectAppAskAndAppAskComentForAskDetail(AppAsk appAsk){
		List<AppAsk> datas = this.selectByCondition(appAsk);
		if(datas!=null){
			datas.forEach(t->{
				AppAskComent appAskComent=new AppAskComent();
				appAskComent.setAskId(t.getId());
				appAskComent.setPage(0);
				List<AppAskComent> lists = appAskComentDao.selectByCondition(appAskComent);
				t.setAppAskComentList(lists);
			});
		}
		return datas;

	}

	@Override
	public List<AppAsk> selectRecommendAskList(AppAsk appAsk) {
		PageHelper.startPage(appAsk.getPage(), appAsk.getPageSize());
		List<AppAsk> list = appAskDao.selectByCondition(appAsk);
		PageInfo pageInfo = new PageInfo(list);
		appAsk.setRows(list);
		appAsk.setTotal((new Long(pageInfo.getTotal())).intValue());
		return list;
	}

	@Override
	public List<AppAsk> selectRecommendAskScreenList(AppAsk appAsk) {
		PageHelper.startPage(appAsk.getPage(), appAsk.getPageSize());
		List<AppAsk> list = appAskDao.selectListByScreen(appAsk);
		PageInfo pageInfo = new PageInfo(list);
		appAsk.setRows(list);
		appAsk.setTotal((new Long(pageInfo.getTotal())).intValue());
		return list;
	}

}
