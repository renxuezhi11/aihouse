package com.aihouse.aihousedao.dao.newhouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.NewHouse;

import java.util.Map;

public interface NewHouseDao extends BaseDao<NewHouse>{

    /**
     *查询索引需要的信息
     * @param id
     * @return
     */
    public Map<String,Object> selectByIdForSolr(Integer id);

    /**
     * 查询动态信息
     * @param id
     * @return
     */
    public Map<String,Object>  queryInfo(Integer id);

    /**
     *页面展示信息
     * @param id
     * @return
     */
    public Map<String,Object> queryDetail(Integer id);

    /**
     * 楼盘详细信息
     * @param id
     * @return
     */
    public Map<String,Object> queryMoreDetail(Integer id);
}
