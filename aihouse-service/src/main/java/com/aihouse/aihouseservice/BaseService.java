package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BaseService<T extends Page, D extends BaseDao<T>> {
    D initDao();

    default int deleteByPrimaryKey(T t) {
        D baseDao = this.initDao();
        return baseDao.deleteByPrimaryKey(t);
    }

    @Transactional
    default int insert(T t) {
        D baseDao = this.initDao();
        return baseDao.insert(t);
    }

    default T selectByPrimaryKey(T t) {
        D baseDao = this.initDao();
        return baseDao.selectByPrimaryKey(t);
    }

    default List<T> queryByCondition(T t) {
        D baseDao = this.initDao();
        return baseDao.queryByCondition(t);
    }

    default T selectAllByPaging(T t)  {

        D baseDao = this.initDao();
        try {
            PageHelper.startPage(t.getPage(), t.getPageSize());
            List<T> lists = baseDao.selectAll(t);
            PageInfo pageInfo = new PageInfo(lists);
            t.setRows(lists);
            t.setTotal((new Long(pageInfo.getTotal())).intValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    @Transactional
    default int update(T t) {
        BaseDao<T> baseDao = this.initDao();
        return baseDao.update(t);
    }

    default List<T> selectAll(T t) {
        BaseDao<T> baseDao = this.initDao();
        return baseDao.selectAll(t);
    }

    default List<T> selectByCondition(T t) {
        D baseDao = this.initDao();
        return baseDao.selectByCondition(t);
    }

}