package com.aihouse.aihouseservice.shophouse.impl;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousecore.utils.SolrTool;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.SolrOfficeHouse;
import com.aihouse.aihousedao.bean.SolrRentHouse;
import com.aihouse.aihousedao.bean.SolrShopHouse;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihousedao.dao.shophouse.ShopHouseDao;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ShopHouseSearchServiceImpl implements ShopHouseSearchService {

    @Resource
    private SolrClient solrClient;

    @Resource
    private ShopHouseDao shopHouseDao;

    @Resource
    private AreaDao areaDao;

    private static final String coreName="shophouse_core";

    @Override
    @Async
    public void addShopHouseIndex(Integer id) {
        if(id!=null){
            Map<String,Object> map=shopHouseDao.selectByIdForSolr(id);
            if(map!=null){
                if(map.get("operation")!=null){
                    Set<String> operation=new HashSet<>();
                    for(String str:map.get("operation").toString().split(",")){
                        operation.add(str);
                    }
                    map.put("operation",operation);
                }
                SolrTool.addMap(map,solrClient,coreName);
            }
        }
    }

    /**
     * 删除索引
     * @param id
     */
    @Override
    @Async
    public void deleteShopHouseIndex(Integer id) {
        if(id!=null){
            SolrTool.delete(id+"",solrClient,coreName);
        }
    }

    @Override
    public ResultInfo<SolrShopHouse> searchShopHouse(HttpServletRequest request) {
        SolrQuery solrQuery=new SolrQuery();
        if(request.getParameter("keyword")!=null){
            solrQuery.set("q","villageName:\""+request.getParameter("keyword")+"\"");
        }else{
            solrQuery.set("q","*:*");
        }
        if(request.getParameter("lng")!=null &&request.getParameter("lat")!=null){
            solrQuery.set("pt",request.getParameter("lat")+","+request.getParameter("lng"));//当前经纬度
            solrQuery.set("sfield", "loc"); //经纬度的字段
            solrQuery.set("sort", "geodist() asc"); //距离排序
        }
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
                //solrQuery.set("fq", "{!geofilt}");
                fq.add("{!geofilt}");
            }
        }
        if(request.getParameter("price")!=null){
            String [] price=(String[])request.getParameter("price").split(",");
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<price.length;i++) {
                String[] pp = price[0].split("-");
                if (pp.length == 1) {
                    //solrQuery.set("fq","monthlyRent:["+pp[0]+" TO *]");
                    //fq.add("monthlyRent:[" + pp[0] + " TO *]");
                    stringBuffer.append("monthlyRent:[" + pp[0] + " TO *]");
                } else {
                    if (!pp[0].equals("")) {
                        //solrQuery.set("fq","monthlyRent:["+pp[0]+" TO "+pp[1]+"]");
                        //fq.add("monthlyRent:[" + pp[0] + " TO " + pp[1] + "]");
                        stringBuffer.append("monthlyRent:[" + pp[0] + " TO " + pp[1] + "]");
                    } else {
                        //solrQuery.set("fq","monthlyRent:[0 TO "+pp[1]+"]");
                        //fq.add("monthlyRent:[0 TO " + pp[1] + "]");
                        stringBuffer.append("monthlyRent:[0 TO " + pp[1] + "]");
                    }
                }
                if(i!=price.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("operation")!=null){//行业
            String[] fixture=request.getParameter("operation").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<fixture.length;i++){
                stringBuffer.append("operation:").append(fixture[i]);
                if(i!=fixture.length-1){
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
           // solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("sort")!=null){// createtime desc,monthlyRent asc,monthlyRent desc
            String[] houseType=request.getParameter("sort").split(",");
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
//        solrQuery.set("defType","edismax");
//        solrQuery.set("mm","100%");

        ResultInfo<SolrShopHouse> resultInfo= SolrTool.queryList(SolrShopHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    @Override
    public Map<String, Object> queryInfo(Integer id) {
        return shopHouseDao.queryInfo(id);
    }

    @Override
    @Cacheable(value = "shophouse",key = "#id",unless = "#result==null")
    public Map<String, Object> queryDetail(Integer id) {
        return shopHouseDao.queryDetail(id);
    }

    @Override
    public ResultInfo<SolrShopHouse> getKeywordComplet(String keyword) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","shopName:\""+keyword+"\"");
        solrQuery.set("fl","id,shopName,monthlyRent");
        solrQuery.set("sort", "query({!v=shopName:"+keyword+"}) asc");
        ResultInfo<SolrShopHouse> resultInfo=SolrTool.queryList(SolrShopHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }
}
