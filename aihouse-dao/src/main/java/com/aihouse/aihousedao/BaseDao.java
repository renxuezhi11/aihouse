package com.aihouse.aihousedao;

import java.util.List;

public interface BaseDao<T> {
    /*
    * */
    int deleteByPrimaryKey(T var1);

    int insert(T var1);

    T selectByPrimaryKey(T var1);

    List<T> queryByCondition(T var1);

    Integer update(T var1);

    List<T> selectAll(T var1);

    List<T> selectByCondition(T var1);
}