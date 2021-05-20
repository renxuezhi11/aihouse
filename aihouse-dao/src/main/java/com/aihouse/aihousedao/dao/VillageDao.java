package com.aihouse.aihousedao.dao;

import com.aihouse.aihousedao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.aihouse.aihousedao.bean.Village;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *小区表 dao
 */
@Mapper
public interface VillageDao extends BaseDao<Village> {
    public List<Map<String,Object>> selectAllByName(Village village);

    List<Map<String,Object>> getSource(@Param("start")Integer start, @Param("limit")Integer limit);
}
