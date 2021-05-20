package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.AppAskComent;

import java.util.List;


/**
 *提问回复表 dao
 */
@Mapper
public interface AppAskComentDao  extends BaseDao<AppAskComent> {


	/**
	 * 根据问答题删除提问回复表
	 */
	public Integer deleteAppAskComentByAppAsk(AppAskComent appAskComent);

	Integer countCommentNotChecked(AppAskComent appAskComent);

	List<AppAskComent> selectMainCheckedList(AppAskComent appAskComent);

	Integer updateChecked(AppAskComent appAskComent);

	Integer countCommentNotMainChecked(AppAskComent appAskComent);


    Integer countReplayCount(AppAskComent resultAskComent);

	Integer countPraiseCount(AppAskComent resultAskComent);

}
