package com.aihouse.aihouseservice.officehouse;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.SolrOfficeHouse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 写字楼查询solr service
 */
public interface OfficeHouseSearchService {

    /**
     * 删除索引 写字楼
     * @param id
     */
    public void deleteOfficeHouseIndex(Integer id);

    /**
     * 建立索引 写字楼
     * @param id
     */
    public void addOfficeHouseIndex(Integer id);

    /**
     * app 搜索筛选
     * @param request
     * @return
     */
    public ResultInfo<SolrOfficeHouse> searchOfficeHouse(HttpServletRequest request);

    public Map<String,Object> queryInfo(Integer id);

    public Map<String,Object> queryDetail(Integer id);

    public ResultInfo<SolrOfficeHouse> getKeywordComplet(String keyword);
}
