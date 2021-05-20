package com.aihouse.aihouseapp.controller;


import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController

public class SearchAllController {

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private SecondHouseSearchService secondHouseSearchService;

    @Resource
    private RentHouseSearchService rentHouseSearchService;

    @Resource
    private ShopHouseSearchService shopHouseSearchService;

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;

    @RequestMapping(value = "app/getAllKeywordComplet",method = RequestMethod.POST)
    public DataRes getKeywordComplet(String keyword){
        if(keyword!=null &&!keyword.trim().equals("")){
            ResultInfo<SolrNewHouse> data=newHouseSearchService.getKeywordComplet(keyword);
            List<Map<String,Object>> list=new ArrayList<>();
            for(SolrNewHouse s:data.getList()) {
                Map newHouse = newHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map=new HashMap<>();
                map.put("areaname",newHouse.get("areaname").toString());
                map.put("streesname",newHouse.get("streesname").toString());
                map.put("name",s.getName());
                map.put("price",s.getAveragePrice());
                map.put("type",1);
                list.add(map);
            }
            ResultInfo<SolrSecondHouse> data1=secondHouseSearchService.getKeywordComplet(keyword);
            for(SolrSecondHouse s:data1.getList()) {
                Map map=secondHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getVillageName());
                map1.put("price",s.getPrice());
                map1.put("type",2);
                map1.put("isSale",s.getIsSale());
                map1.put("id",s.getId());
                list.add(map1);
            }
            ResultInfo<SolrRentHouse> data3=rentHouseSearchService.getkeywordComplet(keyword);
            for(SolrRentHouse s:data3.getList()){
                Map<String,Object> map=rentHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getVillageName());
                map1.put("price",s.getRentFee());
                map1.put("type",3);
                list.add(map1);
            }

            ResultInfo<SolrShopHouse> data4=shopHouseSearchService.getKeywordComplet(keyword);
            for(SolrShopHouse s:data4.getList()){
                Map<String,Object>map=shopHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getShopName());
                map1.put("price",s.getMonthlyRent());
                map1.put("type",4);
                list.add(map1);
            }
            ResultInfo<SolrOfficeHouse> data5=officeHouseSearchService.getKeywordComplet(keyword);
            for(SolrOfficeHouse s:data5.getList()){
                Map<String,Object> map=officeHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getName());
                map1.put("price",s.getMonthlyRent());
                map1.put("type",5);
                list.add(map1);
            }
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
