package com.aihouse.aihouseservice.shophouse;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.SolrShopHouse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ShopHouseSearchService {

    public void deleteShopHouseIndex(Integer id);

    public void addShopHouseIndex(Integer id);

    public ResultInfo<SolrShopHouse> searchShopHouse(HttpServletRequest request);

    public Map<String,Object> queryInfo(Integer id);

    public Map<String,Object> queryDetail(Integer id);

    public ResultInfo<SolrShopHouse> getKeywordComplet(String keyword);
}
