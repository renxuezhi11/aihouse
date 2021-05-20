package com.aihouse.aihouseservice.newhouse;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.SolrNewHouse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface NewHouseSearchService {

    /**
     * app首页新房推荐查询
     * @param lng
     * @param lat
     * @return
     */
    public ResultInfo<SolrNewHouse> recommendHouse(String lng, String lat, Integer start, Integer rows);

    /**
     * 新房添加索引
     * @param id
     */
    public void addNewHouseIndex(Integer id);

    /**
     * 新房索引删除
     * @param id
     */
    public void deleteNewHouseIndex(Integer id);

    /**
     * 索引搜索筛选
     * @param request
     * @return
     */
    public ResultInfo<SolrNewHouse> searchNewHouse(HttpServletRequest request);

    public  Map<String,Object>  queryInfo(Integer id);


    /**
     * app 详细信息
     * @param id
     * @return
     */
    public Map<String,Object> queryDetail(Integer id);

    public List<Map<String, Object>> queryBroker(Integer id);

    public List<Map<String,Object>> getNewHouseAllBroker(Integer id);

    /**
     *楼盘详细信息
     * @param id
     * @return
     */
    public Map<String,Object> queryMoreDetail(Integer id);

    public ResultInfo<SolrNewHouse> getKeywordComplet(String keyword);
}
