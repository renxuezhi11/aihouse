package com.aihouse.aihouseservice.secondHandHouse;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.SolrSecondHouse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 二手房查询
 */
public interface SecondHouseSearchService {

    public ResultInfo<SolrSecondHouse> recommendSecondHouse(String lng, String lat, Integer start, Integer rows,Integer sale);

    /**
     * 建立索引 二手房
     * @param id
     */
    public void addSecondHouseIndex(Integer id);

    /**
     * 删除索引 二手房
     * @param id
     */
    public void deleteSecondHouseIndex(Integer id);

    /**
     * 搜索筛选
     * @param request
     * @return
     */
    public ResultInfo<SolrSecondHouse> searchSecondHouse(HttpServletRequest request);

    /**
     * 查询动态信息
     * @param id
     * @return
     */
    public Map queryInfo(Integer id);

    /**
     * 查询详细信息
     * @param id
     * @return
     */
    public Map<String,Object> queryDetail(Integer id);

    public ResultInfo<SolrSecondHouse> getKeywordComplet(String keyword);

}
