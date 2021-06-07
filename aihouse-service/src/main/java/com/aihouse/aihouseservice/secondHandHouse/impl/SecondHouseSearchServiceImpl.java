package com.aihouse.aihouseservice.secondHandHouse.impl;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousecore.utils.SolrTool;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihousedao.dao.secondHandHouse.SecondHandHouseDao;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 二手房查询service
 */
@Service
public class SecondHouseSearchServiceImpl implements SecondHouseSearchService {
    //自动注入solr客户端
    @Resource
    private SolrClient solrClient;

    @Resource
    private SecondHandHouseDao secondHandHouseDao;

    @Resource
    private AreaDao areaDao;

    private static  final  String coreName="sendhouse_core";

    /**
     * app 二手房推荐查询
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    @Override
    public ResultInfo<SolrSecondHouse> recommendSecondHouse(String lng, String lat, Integer start, Integer rows,Integer sale) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","houseReal:1");
        if(lng!=null &&lat!=null){
            solrQuery.set("pt",lat+","+lng);//当前经纬度
            solrQuery.set("sfield", "loc"); //经纬度的字段
            solrQuery.set("sort", "geodist() asc"); //距离排序
        }
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
        if(sale!=null) {
            StringBuffer str = new StringBuffer();
            str.append("isSale:").append(sale);
            String[] ss = new String[1];
            ss[0] = str.toString();
            solrQuery.set("fq", ss);
        }
        solrQuery.set("defType","edismax");
        String scoreMethod="sum(linear(houseReal,1000,200),sub(30,div(ms(NOW,createtime),86400000)))";
        solrQuery.set("bf", scoreMethod);
        solrQuery.set("fl","*");
        ResultInfo<SolrSecondHouse> resultInfo= SolrTool.queryList(SolrSecondHouse.class,solrQuery,solrClient,"sendhouse_core");
        return resultInfo;
    }

    /**
     * 建立索引，二手房
     * @param id
     */
    @Override
    @Async
    public void addSecondHouseIndex(Integer id) {
        if(id!=null){
            Map<String,Object> map=secondHandHouseDao.selectByIdForSolr(id);
            if(map!=null){
                Set<String> feature=new HashSet<>();
                if(map.get("feature")!=null){
                    for(String str:map.get("feature").toString().split(",")){
                        feature.add(str);
                    }
                }
                if(!ObjectUtils.isEmpty(map.get("userType"))&&map.get("userType").toString().equals("0")){
                    feature.add("业主直卖");
                }
                if(!ObjectUtils.isEmpty(map.get("isLift"))&&map.get("isLift").toString().equals(0)){
                    feature.add("有电梯");
                }
                map.put("feature",feature);
                map.remove("userType");
                map.remove("isLift");
                SolrTool.addMap(map,solrClient,coreName);
            }
        }
    }

    /**
     * 删除索引，二手房
     * @param id
     */
    @Override
    @Async
    public void deleteSecondHouseIndex(Integer id) {
        if(id!=null){
            SolrTool.delete(id+"",solrClient,coreName);
        }
    }

    @Override
    public ResultInfo<SolrSecondHouse> searchSecondHouse(HttpServletRequest request) {
        SolrQuery solrQuery=new SolrQuery();
        if(request.getParameter("keyword")!=null){
            solrQuery.set("q","villageName:\""+request.getParameter("keyword")+"\"");
        }else{
            solrQuery.set("q","*:*");
        }
//        if(request.getParameter("lng")!=null &&request.getParameter("lat")!=null){
//            solrQuery.set("pt",request.getParameter("lat")+","+request.getParameter("lng"));//当前经纬度
//            solrQuery.set("sfield", "loc"); //经纬度的字段
//            solrQuery.set("sort", "geodist() asc"); //距离排序
//        }
        List<String> fq=new ArrayList<>();
        if(request.getParameter("area")!=null){//区域筛选
            String [] areas=(String[])request.getParameter("area").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<areas.length;i++) {
                Area area = new Area();
                area.setId(Integer.parseInt(areas[i]));
                area = areaDao.selectByPrimaryKey(area);
                if (area.getLevel() == 2) {
                    stringBuffer.append("cityId:").append(areas[i]);
                } else if(area.getLevel()==3){
                    stringBuffer.append("areaId:").append(areas[i]);
                }else if(area.getLevel()==4){
                    stringBuffer.append("streesId:").append(areas[i]);
                }
                if(i!=areas.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("near")!=null){//附近筛选
            String [] near=(String[])request.getParameter("near").split(",");;
            if(!near[0].equals("0")){
                solrQuery.set("d", near[0]);
               // solrQuery.set("fq", "{!geofilt}");
                fq.add("{!geofilt}");
            }
        }
        if(request.getParameter("price")!=null){//价格
            String [] price=(String[])request.getParameter("price").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for (int i=0;i<price.length;i++) {
                String[] pp = price[i].split("-");
                if (pp.length == 1) {
                    stringBuffer.append("price:[").append(pp[0]).append(" TO *]");
                    //solrQuery.set("fq", "averagePrice:[" + pp[0] + " TO *]");
                } else {
                    if(!pp[0].equals("")){
                        stringBuffer.append("price:[").append(pp[0]).append(" TO ").append(pp[1]).append("]");
                    }else{
                        stringBuffer.append("price:[").append(0).append(" TO ").append(pp[1]).append("]");
                    }
                    //solrQuery.set("fq", "averagePrice:[" + pp[0] + " TO " + pp[1] + "]");
                }
                if(i!=price.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq", stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("room")!=null){//房型
            String [] room=request.getParameter("room").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<room.length;i++){
                if(room[i].contains("-")){
                    String[] str=room[i].split("-");
                    stringBuffer.append("room:[").append(str[0]).append(" TO *]");
                }else{
                    stringBuffer.append("room:").append(room[i]);
                }
                if(i!=room.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("coveredArea")!=null){//面积
            String [] coveredArea=request.getParameter("coveredArea").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<coveredArea.length;i++){
                String [] pp=coveredArea[i].split("-");
                if(pp.length==1){
                    stringBuffer.append("coveredArea:["+pp[0]+" TO *]");
                }else{
                    stringBuffer.append("coveredArea:["+pp[0]+" TO "+pp[1]+"]");
                }
                if(i!=coveredArea.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("fixture")!=null){//装修
            String[] fixture=request.getParameter("fixture").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<fixture.length;i++){
                stringBuffer.append("fixture:").append(fixture[i]);
                if(i!=fixture.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("houseType")!=null){//性质
            String[] houseType=request.getParameter("houseType").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<houseType.length;i++){
                stringBuffer.append("houseType:").append(houseType[i]);
                if(i!=houseType.length-1){
                    stringBuffer.append(" OR ");
                }
            }
           // solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("feature")!=null){//特色
            String[] feature=request.getParameter("feature").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<feature.length;i++){
                stringBuffer.append("feature:").append(feature[i]);
                if(i!=feature.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("orientations")!=null){//朝向
            String[] orientations=request.getParameter("orientations").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<orientations.length;i++){
                stringBuffer.append("orientations:").append(orientations[i]);
                if(i!=orientations.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("floor")!=null){//楼层
            String [] floor=request.getParameter("floor").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<floor.length;i++){
                String [] pp=floor[i].split("-");
                if(pp.length==1){
                    stringBuffer.append("floor:["+pp[0]+" TO *]");
                }else{
                    stringBuffer.append("floor:["+pp[0]+" TO "+pp[1]+"]");
                }
                if(i!=floor.length-1){
                    stringBuffer.append(" OR ");
                }
            }
           // solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        System.out.println("11111111111111sale=="+(ObjectUtils.isEmpty(request.getParameter("sale"))?"null":request.getParameter("sale")));
        if(!ObjectUtils.isEmpty(request.getParameter("sale"))){
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("isSale:").append(request.getParameter("sale"));
            fq.add(stringBuffer.toString());
        }else{
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("isSale:").append(0);
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("sort")!=null){// createtime desc,price asc,price desc,coveredArea asc,coveredArea desc
            String[] houseType=request.getParameter("sort").split(",");;
            if(!houseType[0].equals("")){
                solrQuery.set("sort", houseType[0]);
            }
        }
        String[] ss=new String[fq.size()];
        for(int i=0;i<fq.size();i++){
            ss[i]=fq.get(i);
        }
        solrQuery.set("fq",ss);
        solrQuery.setStart(Integer.parseInt(request.getParameter("start").toString()));
        solrQuery.setRows(Integer.parseInt(request.getParameter("rows").toString()));
        solrQuery.set("fl","*");
        solrQuery.set("defType","edismax");
        //String scoreMethod="sum(linear(houseReal,1000,200),sub(30,div(ms(NOW,createtime),86400000)))";
        //solrQuery.set("bf", scoreMethod);
        ResultInfo<SolrSecondHouse> resultInfo= SolrTool.queryList(SolrSecondHouse.class,solrQuery,solrClient,"sendhouse_core");
        return resultInfo;
    }

    @Override
    public Map queryInfo(Integer id) {
        return secondHandHouseDao.queryInfo(id);
    }

    @Override
    //@Cacheable(value = "secondhouse",key = "#id",unless = "#result==null")
    public Map<String, Object> queryDetail(Integer id) {
        return secondHandHouseDao.queryDetail(id);
    }

    @Override
    public ResultInfo<SolrSecondHouse> getKeywordComplet(String keyword) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","villageName:\""+keyword+"\"");
        solrQuery.set("fl","id,villageName,price,isSale");
        solrQuery.set("sort", "query({!v=villageName:"+keyword+"}) asc");
        ResultInfo<SolrSecondHouse> resultInfo=SolrTool.queryList(SolrSecondHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

}
