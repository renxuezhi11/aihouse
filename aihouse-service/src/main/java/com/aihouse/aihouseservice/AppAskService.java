package com.aihouse.aihouseservice;

import java.util.List;
import com.aihouse.aihousedao.bean.AppAsk;
import com.aihouse.aihousedao.dao.AppAskDao;


/**
 *提问表 service
 */
public interface AppAskService extends BaseService<AppAsk,AppAskDao>{




	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	public AppAsk selectAppAskAndAppAskComent(AppAsk appAsk);
	/**
	 * 级联查询(带分页) 问答题--提问回复表
	 */
	public List<AppAsk> selectAppAskAndAppAskComentByCondition(AppAsk appAsk);
	/**
	 * 级联删除(根据主键删除) 问答题--提问回复表
	 */
	public Integer deleteAppAskAndAppAskComent(AppAsk appAsk);

	public List<AppAsk> selectUncheckedAppAskComentByCondition(AppAsk appAsk);

	public List<AppAsk> selectAppAskAndAppAskComentForAskDetail(AppAsk appAsk);

	public List<AppAsk> selectRecommendAskList(AppAsk appAsk);

	public List<AppAsk> selectRecommendAskScreenList(AppAsk appAsk);

}
