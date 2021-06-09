package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihouseservice.AppConditionListService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppConditionListServiceImpl implements AppConditionListService {

    @Resource
    private AreaDao areaDao;

    @Override
    @Cacheable(value = "newhouseindex",key = "#cityid")
    public List<Map<String, Object>> getNewHouseIndex(int cityid) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        cityMap.put("label","area");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            streetMap = new HashMap<String,String>();
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getShortName());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getShortName());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        neighbouringMap.put("label","near");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //单价区间条件
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        priceMap.put("label","price");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-8000");
        pricefanweiMap.put("value","8000以下");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","8000-10000");
        pricefanweiMap1.put("value","8000-10000");
        pricefanweiMap1.put("checked",false);
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","10000-12000");
        pricefanweiMap2.put("value","10000-12000");
        pricefanweiMap2.put("checked",false);
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","12000-15000");
        pricefanweiMap3.put("value","12000-15000");
        pricefanweiMap3.put("checked",false);
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","15000-");
        pricefanweiMap4.put("value","15000以上");
        pricefanweiMap4.put("checked",false);
        priceList.add(pricefanweiMap4);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //户型集合
        //标题写死房型
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","房型");
        houseTyMap.put("label","room");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //房型集合--房型编号:id以及房型名称:value
        houseTypeMap.put("id","1");
        houseTypeMap.put("value","一室");
        houseTypeMap.put("checked",false);
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","2");
        houseTypeMap1.put("value","二室");
        houseTypeMap1.put("checked",false);
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","3");
        houseTypeMap2.put("value","三室");
        houseTypeMap2.put("checked",false);
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","4");
        houseTypeMap3.put("value","四室");
        houseTypeMap3.put("checked",false);
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","5-");
        houseTypeMap4.put("value","五室以上");
        houseTypeMap4.put("checked",false);
        houseTypeList.add(houseTypeMap4);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        moreSonMap.put("label","coveredArea");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        acreageMap.put("checked",false);
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        acreageMap1.put("checked",false);
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        acreageMap2.put("checked",false);
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        acreageMap3.put("checked",false);
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        acreageMap4.put("checked",false);
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300-");
        acreageMap5.put("value","300m²以上");
        acreageMap5.put("checked",false);
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修状况");
        decorationMap.put("label","fixture");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","0");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","毛坯");
        decorationap.put("value","毛坯");
        decorationap.put("checked",false);
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","非毛坯");
        decorationap1.put("value","非毛坯");
        decorationap1.put("checked",false);
        decorationList.add(decorationap1);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);

        //更多集合里的售卖状态
        //售卖列表标题：售卖状态
        Map<String,Object> onSaleMap = new HashMap<String,Object>();
        onSaleMap.put("id","m099");
        onSaleMap.put("value","售卖状态");
        onSaleMap.put("label","sellStage");
        //选项类型：0---单选  1----可多选
        onSaleMap.put("checkbox","0");
        //售卖状态列表-----lists
        List<Map<String,Object>> onSaleList = new ArrayList<Map<String,Object>>();
        Map<String,Object> onSalep = new HashMap<String,Object>();
        onSalep.put("id","0");
        onSalep.put("value","在售");
        onSalep.put("checked",false);
        onSaleList.add(onSalep);
        Map<String,Object> onSalep1 = new HashMap<String,Object>();
        onSalep1.put("id","1");
        onSalep1.put("value","未开盘");
        onSalep1.put("checked",false);
        onSaleList.add(onSalep1);
        Map<String,Object> onSalep2 = new HashMap<String,Object>();
        onSalep2.put("id","2");
        onSalep2.put("value","售完");
        onSalep2.put("checked",false);
        onSaleList.add(onSalep2);
        onSaleMap.put("lists",onSaleList);
        moreList.add(onSaleMap);



        //性质
        Map<String,Object> onXzap = new HashMap<String,Object>();
        onXzap.put("id","m199");
        onXzap.put("value","性质");
        onXzap.put("label","houseType");
        onXzap.put("checkbox","1");
        List<Map<String,Object>> onXzList = new ArrayList<Map<String,Object>>();
        Map<String,Object> onXzp = new HashMap<String,Object>();
        onXzp.put("id","公寓");
        onXzp.put("value","公寓");
        onXzp.put("checked",false);
        onXzList.add(onXzp);
        Map<String,Object> onXzp1 = new HashMap<String,Object>();
        onXzp1.put("id","住宅");
        onXzp1.put("value","住宅");
        onXzp1.put("checked",false);
        onXzList.add(onXzp1);
        Map<String,Object> onXzp2 = new HashMap<String,Object>();
        onXzp2.put("id","别墅");
        onXzp2.put("value","别墅");
        onXzp2.put("checked",false);
        onXzList.add(onXzp2);
        Map<String,Object> onXzp3 = new HashMap<String,Object>();
        onXzp3.put("id","其他");
        onXzp3.put("value","其他");
        onXzp3.put("checked",false);
        onXzList.add(onXzp3);
        onXzap.put("lists",onXzList);
        moreList.add(onXzap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);


        //条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        sortMap.put("label","sort");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id"," createtime desc");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id","averagePrice asc ");
        sortp2.put("value","单价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id"," averagePrice desc ");
        sortp3.put("value","单价从高到低");
        sortList.add(sortp3);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }

    @Override
    @Cacheable(value = "entiretenancyindex",key = "#cityid")
    public List<Map<String, Object>> entireTenancyIndex(int cityid) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getAreaname());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getAreaname());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //价格关联表
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-1000");
        pricefanweiMap.put("value","1000以下");
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","1000-2000");
        pricefanweiMap1.put("value","1000-2000");
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","2000-3000");
        pricefanweiMap2.put("value","2000-3000");
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","3000-4000");
        pricefanweiMap3.put("value","3000-4000");
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","4000-6000");
        pricefanweiMap4.put("value","4000-6000");
        priceList.add(pricefanweiMap4);
        Map<String,Object> pricefanweiMap5 = new HashMap<String,Object>();
        pricefanweiMap5.put("id","6000-8000");
        pricefanweiMap5.put("value","6000-8000");
        priceList.add(pricefanweiMap5);
        Map<String,Object> pricefanweiMap6 = new HashMap<String,Object>();
        pricefanweiMap6.put("id","8000-");
        pricefanweiMap6.put("value","8000以上");
        priceList.add(pricefanweiMap6);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //户型集合
        //标题写死房型
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","房型");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //房型集合--房型编号:id以及房型名称:value
        houseTypeMap.put("id","1");
        houseTypeMap.put("value","一室");
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","2");
        houseTypeMap1.put("value","二室");
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","3");
        houseTypeMap2.put("value","三室");
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","4");
        houseTypeMap3.put("value","四室");
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","5");
        houseTypeMap4.put("value","五室以上");
        houseTypeList.add(houseTypeMap4);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300");
        acreageMap5.put("value","300m²以上");
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);
        //更多集合里的房源特色标签集合
        //房源特色标题
        Map<String,Object> featureMap = new HashMap<String,Object>();
        featureMap.put("id","m021");
        featureMap.put("value","房源特色");
        featureMap.put("checkbox","0");
        //选项类型：0---单选  1----可多选
        //房源特色标签列表
        List<Map<String,Object>> featureList = new ArrayList<Map<String,Object>>();
        Map<String,Object> featurep = new HashMap<String,Object>();
        featurep.put("id","电梯");
        featurep.put("value","电梯");
        featureList.add(featurep);
        Map<String,Object> featurep1 = new HashMap<String,Object>();
        featurep1.put("id","楼梯");
        featurep1.put("value","楼梯");
        featureList.add(featurep1);
        featureMap.put("lists",featureList);
        moreList.add(featureMap);
        //更多集合里的朝向集合
        //朝向集合标题
        Map<String,Object> orientationMap = new HashMap<String,Object>();
        orientationMap.put("id","m031");
        orientationMap.put("value","朝向");
        //选项类型：0---单选  1----可多选·
        orientationMap.put("checkbox","1");
        //朝向列表
        List<Map<String,Object>> orientationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> orientationp = new HashMap<String,Object>();
        orientationp.put("id","南北");
        orientationp.put("value","南北通");
        orientationList.add(orientationp);
        Map<String,Object> orientationp1 = new HashMap<String,Object>();
        orientationp1.put("id","北");
        orientationp1.put("value","朝北");
        orientationList.add(orientationp1);
        Map<String,Object> orientationp2 = new HashMap<String,Object>();
        orientationp2.put("id","东");
        orientationp2.put("value","朝东");
        orientationList.add(orientationp2);
        Map<String,Object> orientationp3 = new HashMap<String,Object>();
        orientationp3.put("id","南");
        orientationp3.put("value","朝南");
        orientationList.add(orientationp3);
        Map<String,Object> orientationp4 = new HashMap<String,Object>();
        orientationp4.put("id","西");
        orientationp4.put("value","朝西");
        orientationList.add(orientationp4);
        orientationMap.put("lists",orientationList);
        moreList.add(orientationMap);

        //更多集合里的楼层集合
        //楼层集合标题
        Map<String,Object> levelMap = new HashMap<String,Object>();
        levelMap.put("id","m041");
        levelMap.put("value","楼层");
        //选项类型：0---单选  1----可多选
        levelMap.put("checkbox","1");
        //楼层集合列表
        List<Map<String,Object>> levelList = new ArrayList<Map<String,Object>>();
        Map<String,Object> levelp = new HashMap<String,Object>();
        levelp.put("id","1-10");
        levelp.put("value","1-10楼");
        levelList.add(levelp);
        Map<String,Object> levelp1 = new HashMap<String,Object>();
        levelp1.put("id","11-20");
        levelp1.put("value","11-20楼");
        levelList.add(levelp1);
        Map<String,Object> levelp2 = new HashMap<String,Object>();
        levelp2.put("id","21-30");
        levelp2.put("value","21-30楼");
        levelList.add(levelp2);
        Map<String,Object> levelp3 = new HashMap<String,Object>();
        levelp3.put("id","30-");
        levelp3.put("value","31楼以上");
        levelList.add(levelp3);
        levelMap.put("lists",levelList);
        moreList.add(levelMap);


        //更多集合里的房屋类型集合
        //房屋类型标题：性质
        Map<String,Object> houseHomeMap = new HashMap<String,Object>();
        houseHomeMap.put("id","m056");
        houseHomeMap.put("value","性质");
        //选项类型：0---单选  1----可多选
        houseHomeMap.put("checkbox","1");
        //房屋类型集合----lists
        List<Map<String,Object>> houseHomeList = new ArrayList<Map<String,Object>>();
        Map<String,Object> househomeMap = new HashMap<String,Object>();
        househomeMap.put("id","sell1");
        househomeMap.put("value","公寓");
        houseHomeList.add(househomeMap);
        Map<String,Object> househomeMap1 = new HashMap<String,Object>();
        househomeMap1.put("id","sell2");
        househomeMap1.put("value","住宅");
        houseHomeList.add(househomeMap1);
        Map<String,Object> househomeMap2 = new HashMap<String,Object>();
        househomeMap2.put("id","sell3");
        househomeMap2.put("value","别墅");
        houseHomeList.add(househomeMap2);
        Map<String,Object> househomeMap3 = new HashMap<String,Object>();
        househomeMap3.put("id","selle4");
        househomeMap3.put("value","其他");
        houseHomeList.add(househomeMap3);
        houseHomeMap.put("lists",houseHomeList);
        moreList.add(houseHomeMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","1");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","seller1");
        decorationap.put("value","毛坯");
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","seller2");
        decorationap1.put("value","简装");
        decorationList.add(decorationap1);
        Map<String,Object> decorationap2 = new HashMap<String,Object>();
        decorationap2.put("id","seller3");
        decorationap2.put("value","精装");
        decorationList.add(decorationap2);
        Map<String,Object> decorationap3 = new HashMap<String,Object>();
        decorationap3.put("id","seller4");
        decorationap3.put("value","豪装");
        decorationList.add(decorationap3);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);

        //更多集合里的条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","1");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id","2");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id","3");
        sortp2.put("value","单价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id","4");
        sortp3.put("value","单价从高到低");
        sortList.add(sortp3);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }

    @Override
    @Cacheable(value = "joinrent",key="#cityid")
    public List<Map<String, Object>> joinRent(int cityid) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        cityMap.put("label","area");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            streetMap = new HashMap<String,String>();
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getShortName());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getShortName());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        neighbouringMap.put("label","near");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //价格关联表
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        priceMap.put("label","price");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-1000");
        pricefanweiMap.put("value","1000以下");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","1000-2000");
        pricefanweiMap1.put("value","1000-2000");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","2000-3000");
        pricefanweiMap2.put("value","2000-3000");
        pricefanweiMap2.put("checked",false);
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","3000-4000");
        pricefanweiMap3.put("value","3000-4000");
        pricefanweiMap3.put("checked",false);
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","4000-6000");
        pricefanweiMap4.put("value","4000-6000");
        pricefanweiMap4.put("checked",false);
        priceList.add(pricefanweiMap4);
        Map<String,Object> pricefanweiMap5 = new HashMap<String,Object>();
        pricefanweiMap5.put("id","6000-8000");
        pricefanweiMap5.put("value","6000-8000");
        pricefanweiMap5.put("checked",false);
        priceList.add(pricefanweiMap5);
        Map<String,Object> pricefanweiMap6 = new HashMap<String,Object>();
        pricefanweiMap6.put("id","8000-");
        pricefanweiMap6.put("value","8000以上");
        pricefanweiMap6.put("checked",false);
        priceList.add(pricefanweiMap6);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //户型集合
        //标题写死房型
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","户型");
        houseTyMap.put("label","room");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //房型集合--房型编号:id以及房型名称:value
        houseTypeMap.put("id","1");
        houseTypeMap.put("value","一室");
        houseTypeMap.put("checked",false);
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","2");
        houseTypeMap1.put("value","二室");
        houseTypeMap1.put("checked",false);
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","3");
        houseTypeMap2.put("value","三室");
        houseTypeMap2.put("checked",false);
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","4");
        houseTypeMap3.put("value","四室");
        houseTypeMap3.put("checked",false);
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","5-");
        houseTypeMap4.put("value","五室以上");
        houseTypeMap4.put("checked",false);
        houseTypeList.add(houseTypeMap4);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        moreSonMap.put("label","coveredArea");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        acreageMap.put("checked",false);
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        acreageMap1.put("checked",false);
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        acreageMap2.put("checked",false);
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        acreageMap3.put("checked",false);
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        acreageMap4.put("checked",false);
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300");
        acreageMap5.put("value","300m²以上");
        acreageMap5.put("checked",false);
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);
        //更多集合里的房源特色标签集合
        //房源特色标题
        Map<String,Object> featureMap = new HashMap<String,Object>();
        featureMap.put("id","m021");
        featureMap.put("value","房源特色");
        featureMap.put("checkbox","0");
        featureMap.put("label","feature");
        //选项类型：0---单选  1----可多选
        //房源特色标签列表
        List<Map<String,Object>> featureList = new ArrayList<Map<String,Object>>();
        Map<String,Object> featurep = new HashMap<String,Object>();
        featurep.put("id","电梯");
        featurep.put("value","电梯");
        featurep.put("checked",false);
        featureList.add(featurep);
        Map<String,Object> featurep1 = new HashMap<String,Object>();
        featurep1.put("id","楼梯");
        featurep1.put("value","楼梯");
        featurep1.put("checked",false);
        featureList.add(featurep1);
        featureMap.put("lists",featureList);
        moreList.add(featureMap);
        //更多集合里的朝向集合
        //朝向集合标题
        Map<String,Object> orientationMap = new HashMap<String,Object>();
        orientationMap.put("id","m031");
        orientationMap.put("value","朝向");
        orientationMap.put("label","orientations");
        //选项类型：0---单选  1----可多选·
        orientationMap.put("checkbox","1");
        //朝向列表
        List<Map<String,Object>> orientationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> orientationp = new HashMap<String,Object>();
        orientationp.put("id","南北");
        orientationp.put("value","南北通");
        orientationp.put("checked",false);
        orientationList.add(orientationp);
        Map<String,Object> orientationp1 = new HashMap<String,Object>();
        orientationp1.put("id","北");
        orientationp1.put("value","朝北");
        orientationp1.put("checked",false);
        orientationList.add(orientationp1);
        Map<String,Object> orientationp2 = new HashMap<String,Object>();
        orientationp2.put("id","东");
        orientationp2.put("value","朝东");
        orientationp2.put("checked",false);
        orientationList.add(orientationp2);
        Map<String,Object> orientationp3 = new HashMap<String,Object>();
        orientationp3.put("id","南");
        orientationp3.put("checked",false);
        orientationp3.put("value","朝南");
        orientationList.add(orientationp3);
        Map<String,Object> orientationp4 = new HashMap<String,Object>();
        orientationp4.put("id","西");
        orientationp4.put("value","朝西");
        orientationp4.put("checked",false);
        orientationList.add(orientationp4);
        orientationMap.put("lists",orientationList);
        moreList.add(orientationMap);

        //更多集合里的楼层集合
        //楼层集合标题
        Map<String,Object> levelMap = new HashMap<String,Object>();
        levelMap.put("id","m041");
        levelMap.put("value","楼层");
        //选项类型：0---单选  1----可多选
        levelMap.put("checkbox","1");
        levelMap.put("label","floor");
        //楼层集合列表
        List<Map<String,Object>> levelList = new ArrayList<Map<String,Object>>();
        Map<String,Object> levelp = new HashMap<String,Object>();
        levelp.put("id","1-10");
        levelp.put("value","1-10楼");
        levelp.put("checked",false);
        levelList.add(levelp);
        Map<String,Object> levelp1 = new HashMap<String,Object>();
        levelp1.put("id","11-20");
        levelp1.put("value","11-20楼");
        levelp1.put("checked",false);
        levelList.add(levelp1);
        Map<String,Object> levelp2 = new HashMap<String,Object>();
        levelp2.put("id","21-30");
        levelp2.put("value","21-30楼");
        levelp2.put("checked",false);
        levelList.add(levelp2);
        Map<String,Object> levelp3 = new HashMap<String,Object>();
        levelp3.put("id","30-");
        levelp3.put("value","31楼以上");
        levelp3.put("checked",false);
        levelList.add(levelp3);
        levelMap.put("lists",levelList);
        moreList.add(levelMap);


        //更多集合里的房屋类型集合
        //房屋类型标题：性质
        Map<String,Object> houseHomeMap = new HashMap<String,Object>();
        houseHomeMap.put("id","m056");
        houseHomeMap.put("value","性质");
        //选项类型：0---单选  1----可多选
        houseHomeMap.put("checkbox","1");
        houseHomeMap.put("label","houseType");
        //房屋类型集合----lists
        List<Map<String,Object>> houseHomeList = new ArrayList<Map<String,Object>>();
        Map<String,Object> househomeMap = new HashMap<String,Object>();
        househomeMap.put("id","公寓");
        househomeMap.put("checked",false);
        househomeMap.put("value","公寓");
        houseHomeList.add(househomeMap);
        Map<String,Object> househomeMap1 = new HashMap<String,Object>();
        househomeMap1.put("id","住宅");
        househomeMap1.put("value","住宅");
        househomeMap1.put("checked",false);
        houseHomeList.add(househomeMap1);
        Map<String,Object> househomeMap2 = new HashMap<String,Object>();
        househomeMap2.put("id","别墅");
        househomeMap2.put("value","别墅");
        househomeMap2.put("checked",false);
        houseHomeList.add(househomeMap2);
        Map<String,Object> househomeMap3 = new HashMap<String,Object>();
        househomeMap3.put("id","其他");
        househomeMap3.put("value","其他");
        househomeMap3.put("checked",false);
        houseHomeList.add(househomeMap3);
        houseHomeMap.put("lists",houseHomeList);
        moreList.add(houseHomeMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","1");
        decorationMap.put("label","fixture");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","毛坯");
        decorationap.put("value","毛坯");
        decorationap.put("checked",false);
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","简装修");
        decorationap1.put("value","简装");
        decorationap1.put("checked",false);
        decorationList.add(decorationap1);
        Map<String,Object> decorationap2 = new HashMap<String,Object>();
        decorationap2.put("id","精装修");
        decorationap2.put("value","精装");
        decorationap2.put("checked",false);
        decorationList.add(decorationap2);
        Map<String,Object> decorationap3 = new HashMap<String,Object>();
        decorationap3.put("id","豪华装修");
        decorationap3.put("value","豪装");
        decorationap3.put("checked",false);
        decorationList.add(decorationap3);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);

        //更多集合里的条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        sortMap.put("label","sort");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id","createtime desc");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id","rentFee asc");
        sortp2.put("value","单价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id","rentFee desc");
        sortp3.put("value","单价从高到低");
        sortList.add(sortp3);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }

    @Override
    @Cacheable(value = "officepremises",key = "#cityid")
    public List<Map<String, Object>> officepremises(int cityid) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        cityMap.put("label","area");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            streetMap = new HashMap<String,String>();
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getShortName());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getShortName());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        neighbouringMap.put("label","near");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //价格关联表
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        priceMap.put("label","price");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-1000");
        pricefanweiMap.put("value","1000以下");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","1000-2000");
        pricefanweiMap1.put("value","1000-2000");
        pricefanweiMap1.put("checked",false);
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","2000-3000");
        pricefanweiMap2.put("value","2000-3000");
        pricefanweiMap2.put("checked",false);
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","3000-4000");
        pricefanweiMap3.put("value","3000-4000");
        pricefanweiMap3.put("checked",false);
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","4000-6000");
        pricefanweiMap4.put("value","4000-6000");
        pricefanweiMap4.put("checked",false);
        priceList.add(pricefanweiMap4);
        Map<String,Object> pricefanweiMap5 = new HashMap<String,Object>();
        pricefanweiMap5.put("id","6000-8000");
        pricefanweiMap5.put("value","6000-8000");
        pricefanweiMap5.put("checked",false);
        priceList.add(pricefanweiMap5);
        Map<String,Object> pricefanweiMap6 = new HashMap<String,Object>();
        pricefanweiMap6.put("id","8000-");
        pricefanweiMap6.put("value","8000以上");
        pricefanweiMap6.put("checked",false);
        priceList.add(pricefanweiMap6);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //类型集合
        //标题写死类型
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","类型");
        houseTyMap.put("label","type");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //类型集合--类型编号:id以及类型名称:value
        houseTypeMap.put("id","1");
        houseTypeMap.put("value","纯写字楼");
        houseTypeMap.put("checked",false);
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","2");
        houseTypeMap1.put("value","商住两用楼");
        houseTypeMap1.put("checked",false);
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","3");
        houseTypeMap2.put("value","商业综合体楼");
        houseTypeMap2.put("checked",false);
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","4");
        houseTypeMap3.put("value","酒店写字楼");
        houseTypeMap3.put("checked",false);
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","5");
        houseTypeMap4.put("value","产业园区");
        houseTypeMap4.put("checked",false);
        houseTypeList.add(houseTypeMap4);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        moreSonMap.put("label","coveredArea");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        acreageMap.put("checked",false);
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        acreageMap1.put("checked",false);
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        acreageMap2.put("checked",false);
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        acreageMap3.put("checked",false);
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        acreageMap4.put("checked",false);
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300-");
        acreageMap5.put("value","300m²以上");
        acreageMap5.put("checked",false);
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","1");
        decorationMap.put("label","fixture");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","seller1");
        decorationap.put("value","毛坯");
        decorationap.put("checked",false);
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","简装修");
        decorationap1.put("value","简装");
        decorationap1.put("checked",false);
        decorationList.add(decorationap1);
        Map<String,Object> decorationap2 = new HashMap<String,Object>();
        decorationap2.put("id","精装修");
        decorationap2.put("value","精装");
        decorationap2.put("checked",false);
        decorationList.add(decorationap2);
        Map<String,Object> decorationap3 = new HashMap<String,Object>();
        decorationap3.put("id","豪华装修");
        decorationap3.put("value","豪装");
        decorationap3.put("checked",false);
        decorationList.add(decorationap3);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);

        //更多集合里的朝向集合
        //朝向集合标题
//        Map<String,Object> orientationMap = new HashMap<String,Object>();
//        orientationMap.put("id","m031");
//        orientationMap.put("value","朝向");
//        //选项类型：0---单选  1----可多选·
//        orientationMap.put("checkbox","1");
//        //朝向列表
//        List<Map<String,Object>> orientationList = new ArrayList<Map<String,Object>>();
//        Map<String,Object> orientationp = new HashMap<String,Object>();
//        orientationp.put("id","南北");
//        orientationp.put("value","南北通");
//        orientationList.add(orientationp);
//        Map<String,Object> orientationp1 = new HashMap<String,Object>();
//        orientationp1.put("id","北");
//        orientationp1.put("value","朝北");
//        orientationList.add(orientationp1);
//        Map<String,Object> orientationp2 = new HashMap<String,Object>();
//        orientationp2.put("id","东");
//        orientationp2.put("value","朝东");
//        orientationList.add(orientationp2);
//        Map<String,Object> orientationp3 = new HashMap<String,Object>();
//        orientationp3.put("id","南");
//        orientationp3.put("value","朝南");
//        orientationList.add(orientationp3);
//        Map<String,Object> orientationp4 = new HashMap<String,Object>();
//        orientationp4.put("id","西");
//        orientationp4.put("value","朝西");
//        orientationList.add(orientationp4);
//        orientationMap.put("lists",orientationList);
//        moreList.add(orientationMap);

        //更多集合里的楼层集合
        //楼层集合标题
        Map<String,Object> levelMap = new HashMap<String,Object>();
        levelMap.put("id","m041");
        levelMap.put("value","楼层");
        //选项类型：0---单选  1----可多选
        levelMap.put("checkbox","1");
        levelMap.put("label","floor");
        //楼层集合列表
        List<Map<String,Object>> levelList = new ArrayList<Map<String,Object>>();
        Map<String,Object> levelp = new HashMap<String,Object>();
        levelp.put("id","1-10");
        levelp.put("value","1-10楼");
        levelp.put("checked",false);
        levelList.add(levelp);
        Map<String,Object> levelp1 = new HashMap<String,Object>();
        levelp1.put("id","11-20");
        levelp1.put("value","11-20楼");
        levelp1.put("checked",false);
        levelList.add(levelp1);
        Map<String,Object> levelp2 = new HashMap<String,Object>();
        levelp2.put("id","21-30");
        levelp2.put("value","21-30楼");
        levelList.add(levelp2);
        Map<String,Object> levelp3 = new HashMap<String,Object>();
        levelp3.put("id","30-");
        levelp3.put("value","31楼以上");
        levelp3.put("checked",false);
        levelList.add(levelp3);
        levelMap.put("lists",levelList);
        moreList.add(levelMap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);

        //更多集合里的条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        sortMap.put("label","sort");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id"," createtime desc ");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id"," monthlyRent asc");
        sortp2.put("value","单价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id"," monthlyRent desc");
        sortp3.put("value","单价从高到低");
        sortList.add(sortp3);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }

    @Override
    //@Cacheable(value = "secondhandhouseindex",key="#cityid")
    public List<Map<String, Object>> secondhandhouseIndex(int cityid) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        cityMap.put("label","area");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            streetMap = new HashMap<String,String>();
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getShortName());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getShortName());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        neighbouringMap.put("label","near");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //价格关联表
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        priceMap.put("label","price");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-30");
        pricefanweiMap.put("value","30万以下");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","50-80");
        pricefanweiMap1.put("value","50万-80万");
        pricefanweiMap1.put("checked",false);
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","80-100");
        pricefanweiMap2.put("value","80万-100万");
        pricefanweiMap2.put("checked",false);
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","100-150");
        pricefanweiMap3.put("value","100万-150万");
        pricefanweiMap3.put("checked",false);
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","150-200");
        pricefanweiMap4.put("value","150万-200万");
        pricefanweiMap4.put("checked",false);
        priceList.add(pricefanweiMap4);
        Map<String,Object> pricefanweiMap5 = new HashMap<String,Object>();
        pricefanweiMap5.put("id","200-300");
        pricefanweiMap5.put("value","200万-300万");
        pricefanweiMap5.put("checked",false);
        priceList.add(pricefanweiMap5);
        Map<String,Object> pricefanweiMap6 = new HashMap<String,Object>();
        pricefanweiMap6.put("id","300-");
        pricefanweiMap6.put("value","300万以上");
        pricefanweiMap6.put("checked",false);
        priceList.add(pricefanweiMap6);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //户型集合
        //标题写死房型
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","房型");
        houseTyMap.put("label","room");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //房型集合--房型编号:id以及房型名称:value
        houseTypeMap.put("id","1");
        houseTypeMap.put("value","一室");
        houseTypeMap.put("checked",false);
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","2");
        houseTypeMap1.put("value","二室");
        houseTypeMap1.put("checked",false);
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","3");
        houseTypeMap2.put("value","三室");
        houseTypeMap2.put("checked",false);
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","4");
        houseTypeMap3.put("value","四室");
        houseTypeMap3.put("checked",false);
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","5-");
        houseTypeMap4.put("value","五室以上");
        houseTypeMap4.put("checked",false);
        houseTypeList.add(houseTypeMap4);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        moreSonMap.put("label","coveredArea");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        acreageMap.put("checked",false);
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        acreageMap1.put("checked",false);
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        acreageMap2.put("checked",false);
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        acreageMap3.put("checked",false);
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        acreageMap4.put("checked",false);
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300");
        acreageMap5.put("value","300m²以上");
        acreageMap5.put("checked",false);
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);
        //更多集合里的房源特色标签集合
        //房源特色标题
        Map<String,Object> featureMap = new HashMap<String,Object>();
        featureMap.put("id","m021");
        featureMap.put("value","房源特色");
        featureMap.put("checkbox","1");
        featureMap.put("label","feature");
        //选项类型：0---单选  1----可多选
        //房源特色标签列表
        List<Map<String,Object>> featureList = new ArrayList<Map<String,Object>>();
        Map<String,Object> featurep = new HashMap<String,Object>();
        featurep.put("id","学区房");
        featurep.put("value","学区房");
        featurep.put("checked",false);
        featureList.add(featurep);
        Map<String,Object> featurep1 = new HashMap<String,Object>();
        featurep1.put("id","笋盘急售");
        featurep1.put("value","笋盘急售");
        featurep1.put("checked",false);
        featureList.add(featurep1);
        Map<String,Object> featurep2 = new HashMap<String,Object>();
        featurep2.put("id","有电梯");
        featurep2.put("value","有电梯");
        featurep2.put("checked",false);
        featureList.add(featurep2);
        featureMap.put("lists",featureList);
        moreList.add(featureMap);
        //更多集合里的朝向集合
        //朝向集合标题
        Map<String,Object> orientationMap = new HashMap<String,Object>();
        orientationMap.put("id","m031");
        orientationMap.put("value","朝向");
        //选项类型：0---单选  1----可多选·
        orientationMap.put("checkbox","1");
        orientationMap.put("label","orientations");
        //朝向列表
        List<Map<String,Object>> orientationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> orientationp = new HashMap<String,Object>();
        orientationp.put("id","南北");
        orientationp.put("value","南北通");
        orientationp.put("checked",false);
        orientationList.add(orientationp);
        Map<String,Object> orientationp1 = new HashMap<String,Object>();
        orientationp1.put("id","北");
        orientationp1.put("value","朝北");
        orientationp1.put("checked",false);
        orientationList.add(orientationp1);
        Map<String,Object> orientationp2 = new HashMap<String,Object>();
        orientationp2.put("id","东");
        orientationp2.put("value","朝东");
        orientationp2.put("checked",false);
        orientationList.add(orientationp2);
        Map<String,Object> orientationp3 = new HashMap<String,Object>();
        orientationp3.put("id","南");
        orientationp3.put("value","朝南");
        orientationList.add(orientationp3);
        Map<String,Object> orientationp4 = new HashMap<String,Object>();
        orientationp4.put("id","西");
        orientationp4.put("value","朝西");
        orientationp4.put("checked",false);
        orientationList.add(orientationp4);
        orientationMap.put("lists",orientationList);
        moreList.add(orientationMap);

        //更多集合里的楼层集合
        //楼层集合标题
        Map<String,Object> levelMap = new HashMap<String,Object>();
        levelMap.put("id","m041");
        levelMap.put("value","楼层");
        //选项类型：0---单选  1----可多选
        levelMap.put("checkbox","1");
        levelMap.put("label","floor");
        //楼层集合列表
        List<Map<String,Object>> levelList = new ArrayList<Map<String,Object>>();
        Map<String,Object> levelp = new HashMap<String,Object>();
        levelp.put("id","1-10");
        levelp.put("value","1-10楼");
        levelp.put("checked",false);
        levelList.add(levelp);
        Map<String,Object> levelp1 = new HashMap<String,Object>();
        levelp1.put("id","11-20");
        levelp1.put("value","11-20楼");
        levelp1.put("checked",false);
        levelList.add(levelp1);
        Map<String,Object> levelp2 = new HashMap<String,Object>();
        levelp2.put("id","21-30");
        levelp2.put("value","21-30楼");
        levelp2.put("checked",false);
        levelList.add(levelp2);
        Map<String,Object> levelp3 = new HashMap<String,Object>();
        levelp3.put("id","30-");
        levelp3.put("value","31楼以上");
        levelp3.put("checked",false);
        levelList.add(levelp3);
        levelMap.put("lists",levelList);
        moreList.add(levelMap);


        //更多集合里的房屋类型集合
        //房屋类型标题：性质
        Map<String,Object> houseHomeMap = new HashMap<String,Object>();
        houseHomeMap.put("id","m056");
        houseHomeMap.put("value","性质");
        //选项类型：0---单选  1----可多选
        houseHomeMap.put("checkbox","1");
        houseHomeMap.put("label","houseType");
        //房屋类型集合----lists
        List<Map<String,Object>> houseHomeList = new ArrayList<Map<String,Object>>();
        Map<String,Object> househomeMap = new HashMap<String,Object>();
        househomeMap.put("id","公寓");
        househomeMap.put("value","公寓");
        househomeMap.put("checked",false);
        houseHomeList.add(househomeMap);
        Map<String,Object> househomeMap1 = new HashMap<String,Object>();
        househomeMap1.put("id","住宅");
        househomeMap1.put("value","住宅");
        househomeMap1.put("checked",false);
        houseHomeList.add(househomeMap1);
        Map<String,Object> househomeMap2 = new HashMap<String,Object>();
        househomeMap2.put("id","别墅");
        househomeMap2.put("value","别墅");
        househomeMap2.put("checked",false);
        houseHomeList.add(househomeMap2);
        Map<String,Object> househomeMap3 = new HashMap<String,Object>();
        househomeMap3.put("id","其他");
        househomeMap3.put("value","其他");
        househomeMap3.put("checked",false);
        houseHomeList.add(househomeMap3);
        houseHomeMap.put("lists",houseHomeList);
        moreList.add(houseHomeMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","1");
        decorationMap.put("label","fixture");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","毛坯");
        decorationap.put("value","毛坯");
        decorationap.put("checked",false);
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","简装修");
        decorationap1.put("value","简装");
        decorationap1.put("checked",false);
        decorationList.add(decorationap1);
        Map<String,Object> decorationap2 = new HashMap<String,Object>();
        decorationap2.put("id","精装修");
        decorationap2.put("value","精装");
        decorationap2.put("checked",false);
        decorationList.add(decorationap2);
        Map<String,Object> decorationap3 = new HashMap<String,Object>();
        decorationap3.put("id","豪华装修");
        decorationap3.put("value","豪装");
        decorationap3.put("checked",false);
        decorationList.add(decorationap3);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);

        //更多集合里的条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        sortMap.put("label","sort");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id"," createtime desc ");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id"," price asc ");
        sortp2.put("value","总价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id"," price desc ");
        sortp3.put("value","总价从高到低");
        sortList.add(sortp3);
        Map<String,Object> sortp6 = new HashMap<String,Object>();
        sortp6.put("id"," coveredArea asc ");
        sortp6.put("value","面积从低到高");
        sortList.add(sortp6);
        Map<String,Object> sortp7 = new HashMap<String,Object>();
        sortp7.put("id"," coveredArea desc ");
        sortp7.put("value","面积从高到低");
        sortList.add(sortp7);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }


    @Override
    @Cacheable(value = "shopindex",key="#cityid")
    public List<Map<String, Object>> shopindex(int cityid) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        Integer cityId = cityid;
        Area area = new Area();
        //设置市Id
        area.setParentId(cityId);
        List<Area> citylist =areaDao.selectByCondition(area);
        //地域集合
        //市
        //根据市Id查询所有区
        Map<String,Object> areaMap = new HashMap<String,Object>();
        areaMap.put("value","one");
        areaMap.put("name","区域");
        List<Map<String,Object>> itemList = new ArrayList<>();
        Map<String,Object> cityMap = new HashMap<String,Object>();
        cityMap.put("id",cityId+"");
        cityMap.put("value","惠州");
        cityMap.put("label","area");
        /*
         * 区列表
         * */
        List<Map<String,Object>> quMapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> quMap = new HashMap<>();
        quMap.put("id",cityId+"");
        quMap.put("value","不限");
        quMapList.add(quMap);
        Map<String,String> streetMap = new HashMap<String,String>();

        for(Area quArea:citylist){
            streetMap = new HashMap<String,String>();
            Map<String,Object> quMap2 = new HashMap<String,Object>();
            List<Map<String,String>> streetMaplist = new ArrayList<Map<String,String>>();
            quMap2.put("id",quArea.getId()+"");
            quMap2.put("value",quArea.getShortName());
            Area streetQuery = new Area();
            streetQuery.setParentId(quArea.getId());
            //查询当前区下的所有街道集合
            List<Area> streetList = areaDao.selectByCondition(streetQuery);
            /*
             * 不限写死
             * */
            streetMap.put("id",quArea.getId()+"");
            streetMap.put("value","不限");
            //片区集合
            streetMaplist.add(streetMap);
            for(Area streetArea : streetList){
                Map<String,String> streetMap2 = new HashMap<String,String>();
                //片区编号
                streetMap2.put("id",streetArea.getId()+"");
                //片区名
                streetMap2.put("value",streetArea.getShortName());
                streetMap2.put("checked","false");
                streetMaplist.add(streetMap2);
            }
            //置入每个区的街道
            quMap2.put("children",streetMaplist);
            quMapList.add(quMap2);
        }
        /*
         * 区列表
         * */
        cityMap.put("children",quMapList);
        itemList.add(cityMap);
        areaMap.put("item",itemList);
        //距离编号集合
        //附近写死
        Map<String,Object> neighbouringMap = new HashMap<String,Object>();
        neighbouringMap.put("id","0");
        neighbouringMap.put("value","附近");
        neighbouringMap.put("label","near");
        List<Map<String,Object>> distancelist = new ArrayList<Map<String,Object>>();
        Map<String,Object> distanceMap = new HashMap<String,Object>();
        distanceMap.put("id","0");
        distanceMap.put("value","不限");
        distancelist.add(distanceMap);
        Map<String,Object> distanceMap1 = new HashMap<String,Object>();
        distanceMap1.put("id","0.5");
        distanceMap1.put("value","500m以内");
        distancelist.add(distanceMap1);
        Map<String,Object> distanceMap2 = new HashMap<String,Object>();
        distanceMap2.put("id","1");
        distanceMap2.put("value","1km以内");
        distancelist.add(distanceMap2);
        Map<String,Object> distanceMap3 = new HashMap<String,Object>();
        distanceMap3.put("id","2");
        distanceMap3.put("value","2km以内");
        distancelist.add(distanceMap3);
        Map<String,Object> distanceMap4 = new HashMap<String,Object>();
        distanceMap4.put("id","3");
        distanceMap4.put("value","3km以内");
        distancelist.add(distanceMap4);
        Map<String,Object> distanceMap5 = new HashMap<String,Object>();
        distanceMap5.put("id","4");
        distanceMap5.put("value","4km以内");
        distancelist.add(distanceMap5);
        Map<String,Object> distanceMap6 = new HashMap<String,Object>();
        distanceMap6.put("id","5");
        distanceMap6.put("value","5km以内");
        distancelist.add(distanceMap6);
        //children Map下放置距离集合编号
        //有距离编号以及距离名
        neighbouringMap.put("children",distancelist);
        itemList.add(neighbouringMap);
        areaMap.put("item",itemList);
        resultList.add(areaMap);

        //价格关联表
        //价格标题写死
        Map<String,Object> priceMap = new HashMap<String,Object>();
        priceMap.put("value","two");
        priceMap.put("name","价格");
        priceMap.put("label","price");
        List<Map<String,Object>> priceList = new ArrayList<>();
        Map<String,Object> pricefanweiMap = new HashMap<String,Object>();
        pricefanweiMap.put("id","0-3000");
        pricefanweiMap.put("value","3000以下");
        priceList.add(pricefanweiMap);
        Map<String,Object> pricefanweiMap1 = new HashMap<String,Object>();
        pricefanweiMap1.put("id","3000-5000");
        pricefanweiMap1.put("value","3000-5000");
        pricefanweiMap.put("checked",false);
        priceList.add(pricefanweiMap1);
        Map<String,Object> pricefanweiMap2 = new HashMap<String,Object>();
        pricefanweiMap2.put("id","5000-8000");
        pricefanweiMap2.put("value","5000-8000");
        pricefanweiMap2.put("checked",false);
        priceList.add(pricefanweiMap2);
        Map<String,Object> pricefanweiMap3 = new HashMap<String,Object>();
        pricefanweiMap3.put("id","8000-12000");
        pricefanweiMap3.put("value","8000-12000");
        pricefanweiMap3.put("checked",false);
        priceList.add(pricefanweiMap3);
        Map<String,Object> pricefanweiMap4 = new HashMap<String,Object>();
        pricefanweiMap4.put("id","12000-15000");
        pricefanweiMap4.put("value","12000-15000");
        pricefanweiMap4.put("checked",false);
        priceList.add(pricefanweiMap4);
        Map<String,Object> pricefanweiMap5 = new HashMap<String,Object>();
        pricefanweiMap5.put("id","15000-20000");
        pricefanweiMap5.put("value","15000-20000");
        pricefanweiMap5.put("checked",false);
        priceList.add(pricefanweiMap5);
        Map<String,Object> pricefanweiMap6 = new HashMap<String,Object>();
        pricefanweiMap6.put("id","20000-");
        pricefanweiMap6.put("value","20000以上");
        pricefanweiMap6.put("checked",false);
        priceList.add(pricefanweiMap6);
        //priceList装载价格区间的Map
        priceMap.put("item",priceList);
        //价格id:id以及价格区间描述:value
        resultList.add(priceMap);

        //行业集合
        //标题写死行业
        Map<String,Object> houseTyMap = new HashMap<String,Object>();
        houseTyMap.put("value","three");
        houseTyMap.put("name","行业");
        houseTyMap.put("label","operation");
        List<Map<String,Object>> houseTypeList = new ArrayList<>();
        Map<String,Object> houseTypeMap = new HashMap<String,Object>();
        //行业集合--行业编号:id以及行业名称:value
        houseTypeMap.put("id","超市零售");
        houseTypeMap.put("value","超市零售");
        houseTypeMap.put("checked",false);
        houseTypeList.add(houseTypeMap);
        Map<String,Object> houseTypeMap1 = new HashMap<String,Object>();
        houseTypeMap1.put("id","电子通讯");
        houseTypeMap1.put("value","电子通讯");
        houseTypeMap1.put("checked",false);
        houseTypeList.add(houseTypeMap1);
        Map<String,Object> houseTypeMap2 = new HashMap<String,Object>();
        houseTypeMap2.put("id","家具建材");
        houseTypeMap2.put("value","家具建材");
        houseTypeMap2.put("checked",false);
        houseTypeList.add(houseTypeMap2);
        Map<String,Object> houseTypeMap3 = new HashMap<String,Object>();
        houseTypeMap3.put("id","服饰鞋包");
        houseTypeMap3.put("value","服饰鞋包");
        houseTypeMap3.put("checked",false);
        houseTypeList.add(houseTypeMap3);
        Map<String,Object> houseTypeMap4 = new HashMap<String,Object>();
        houseTypeMap4.put("id","生活服务");
        houseTypeMap4.put("value","生活服务");
        houseTypeMap4.put("checked",false);
        houseTypeList.add(houseTypeMap4);
        Map<String,Object> houseTypeMap6 = new HashMap<String,Object>();
        houseTypeMap6.put("id","餐饮美食");
        houseTypeMap6.put("value","餐饮美食");
        houseTypeMap6.put("checked",false);
        houseTypeList.add(houseTypeMap6);
        Map<String,Object> houseTypeMap7 = new HashMap<String,Object>();
        houseTypeMap7.put("id","休闲娱乐");
        houseTypeMap7.put("value","休闲娱乐");
        houseTypeMap7.put("checked",false);
        houseTypeList.add(houseTypeMap7);
        Map<String,Object> houseTypeMap8 = new HashMap<String,Object>();
        houseTypeMap8.put("id","其他");
        houseTypeMap8.put("value","其他");
        houseTypeMap8.put("checked",false);
        houseTypeList.add(houseTypeMap8);
        houseTyMap.put("item",houseTypeList);
        resultList.add(houseTyMap);

        //更多条件集合
        //标题写死更多选项
        Map<String,Object> moreMap = new HashMap<String,Object>();
        moreMap.put("value","0");
        moreMap.put("name","更多");
        List<Map<String,Object>> moreList = new ArrayList<>();
        //外面用item集合装载
        //更多集合里的建筑面积集合
        //标题写死建筑面积
        Map<String,Object> moreSonMap = new HashMap<String,Object>();
        moreSonMap.put("id","m021");
        moreSonMap.put("value","建筑面积");
        //选项类型：0---单选  1----可多选
        moreSonMap.put("checkbox","1");
        moreSonMap.put("label","coveredArea");
        //面积列表lists
        List<Map<String,Object>> sonList = new ArrayList<Map<String,Object>>();
        Map<String,Object> acreageMap = new HashMap<String,Object>();
        acreageMap.put("id","0-50");
        acreageMap.put("value","50m²");
        acreageMap.put("checked",false);
        sonList.add(acreageMap);
        Map<String,Object> acreageMap1 = new HashMap<String,Object>();
        acreageMap1.put("id","50-80");
        acreageMap1.put("value","50-80m²");
        acreageMap1.put("checked",false);
        sonList.add(acreageMap1);
        Map<String,Object> acreageMap2 = new HashMap<String,Object>();
        acreageMap2.put("id","110-150");
        acreageMap2.put("value","110-150m²");
        acreageMap2.put("checked",false);
        sonList.add(acreageMap2);
        Map<String,Object> acreageMap3 = new HashMap<String,Object>();
        acreageMap3.put("id","150-200");
        acreageMap3.put("value","150-200m²");
        acreageMap3.put("checked",false);
        sonList.add(acreageMap3);
        Map<String,Object> acreageMap4 = new HashMap<String,Object>();
        acreageMap4.put("id","200-300");
        acreageMap4.put("value","200-300m²");
        acreageMap4.put("checked",false);
        sonList.add(acreageMap4);
        Map<String,Object> acreageMap5 = new HashMap<String,Object>();
        acreageMap5.put("id","300-");
        acreageMap5.put("value","300m²以上");
        acreageMap5.put("checked",false);
        sonList.add(acreageMap5);
        moreSonMap.put("lists",sonList);
        moreList.add(moreSonMap);

        //更多集合里的装修程度集合
        //装修列表标题：装修程度
        Map<String,Object> decorationMap = new HashMap<String,Object>();
        decorationMap.put("id","m088");
        decorationMap.put("value","装修状况");
        //选项类型：0---单选  1----可多选
        decorationMap.put("checkbox","0");
        decorationMap.put("label","fixture");
        //装修程度列表-----lists
        List<Map<String,Object>> decorationList = new ArrayList<Map<String,Object>>();
        Map<String,Object> decorationap = new HashMap<String,Object>();
        decorationap.put("id","毛坯");
        decorationap.put("value","毛坯");
        decorationap.put("checked",false);
        decorationList.add(decorationap);
        Map<String,Object> decorationap1 = new HashMap<String,Object>();
        decorationap1.put("id","简装修");
        decorationap1.put("value","简装");
        decorationap1.put("checked",false);
        decorationList.add(decorationap1);
        Map<String,Object> decorationap2 = new HashMap<String,Object>();
        decorationap2.put("id","精装修");
        decorationap2.put("value","精装");
        decorationap2.put("checked",false);
        decorationList.add(decorationap2);
        Map<String,Object> decorationap3 = new HashMap<String,Object>();
        decorationap3.put("id","豪华装修");
        decorationap3.put("value","豪装");
        decorationap3.put("checked",false);
        decorationList.add(decorationap3);
        decorationMap.put("lists",decorationList);
        moreList.add(decorationMap);

        //更多集合里的售卖状态集合
        //售卖状态标题：售卖状态
//        Map<String,Object> saleMap = new HashMap<String,Object>();
//        saleMap.put("id","m099");
//        saleMap.put("value","售卖状态");
//        //选项类型：0---单选  1----可多选
//        saleMap.put("checkbox","0");
//        //售卖状态列表-----lists
//        List<Map<String,Object>> saleList = new ArrayList<Map<String,Object>>();
//        Map<String,Object> salep = new HashMap<String,Object>();
//        salep.put("id","0");
//        salep.put("value","未开盘");
//        saleList.add(salep);
//        Map<String,Object> salep1 = new HashMap<String,Object>();
//        salep1.put("id","1");
//        salep1.put("value","在售");
//        saleList.add(salep1);
//        saleMap.put("lists",saleList);
//        moreList.add(saleMap);
//
//        //更多集合里的性质集合
//        //性质标题：性质
//        Map<String,Object> natureMap = new HashMap<String,Object>();
//        natureMap.put("id","m199");
//        natureMap.put("value","性质");
//        //选项类型：0---单选  1----可多选
//        natureMap.put("checkbox","1");
//        //售卖状态列表-----lists
//        List<Map<String,Object>> natureList = new ArrayList<Map<String,Object>>();
//        Map<String,Object> naturep = new HashMap<String,Object>();
//        naturep.put("id","0");
//        naturep.put("value","公寓");
//        natureList.add(naturep);
//        Map<String,Object> naturep1 = new HashMap<String,Object>();
//        naturep1.put("id","1");
//        naturep1.put("value","住宅");
//        natureList.add(naturep1);
//        Map<String,Object> naturep2 = new HashMap<String,Object>();
//        naturep2.put("id","2");
//        naturep2.put("value","别墅");
//        natureList.add(naturep2);
//        Map<String,Object> naturep3 = new HashMap<String,Object>();
//        naturep3.put("id","3");
//        naturep3.put("value","商铺");
//        natureList.add(naturep3);
//        Map<String,Object> naturep4 = new HashMap<String,Object>();
//        naturep4.put("id","4");
//        naturep4.put("value","写字楼");
//        natureList.add(naturep4);
//        natureMap.put("lists",natureList);
//        moreList.add(natureMap);
        moreMap.put("item",moreList);
        resultList.add(moreMap);

        //更多集合里的条件排序集合
        //排序类型标题:排序
        Map<String,Object> sortMap = new HashMap<String,Object>();
        sortMap.put("id","4");
        sortMap.put("name","排序");
        sortMap.put("label","sort");
        //选项类型列表-------lists
        List<Map<String,Object>> sortList = new ArrayList<Map<String,Object>>();
        Map<String,Object> sortp = new HashMap<String,Object>();
        sortp.put("id","");
        sortp.put("value","默认排序");
        sortList.add(sortp);
        Map<String,Object> sortp1 = new HashMap<String,Object>();
        sortp1.put("id","  createtime desc ");
        sortp1.put("value","最新发布");
        sortList.add(sortp1);
        Map<String,Object> sortp2 = new HashMap<String,Object>();
        sortp2.put("id"," monthlyRent asc");
        sortp2.put("value","单价从低到高");
        sortList.add(sortp2);
        Map<String,Object> sortp3 = new HashMap<String,Object>();
        sortp3.put("id"," monthlyRent desc");
        sortp3.put("value","单价从高到低");
        sortList.add(sortp3);
        sortMap.put("item",sortList);
        resultList.add(sortMap);
        return resultList;
    }
}
