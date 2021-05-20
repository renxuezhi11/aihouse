package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.AppAsk;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *提问表 dao
 */
@Mapper
public interface AppAskDao  extends BaseDao<AppAsk> {

    List<AppAsk> selectListByKey(AppAsk appAsk);

    List<AppAsk> showListByKey(AppAsk appAsk);

    Integer countUnCheckQuestion(AppAsk appAsk);

    List<AppAsk> selectListByScreen(AppAsk appAsk);

}
