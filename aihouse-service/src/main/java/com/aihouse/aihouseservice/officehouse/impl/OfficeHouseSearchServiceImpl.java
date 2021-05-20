package com.aihouse.aihouseservice.officehouse.impl;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousecore.utils.SolrTool;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.SolrNewHouse;
import com.aihouse.aihousedao.bean.SolrOfficeHouse;
import com.aihouse.aihousedao.bean.SolrRentHouse;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihousedao.dao.officehouse.OfficeHouseDao;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 写字楼 solr service
 */
@Service
public class OfficeHouseSearchServiceImpl implements OfficeHouseSearchService {

    @Resource
    private SolrClient solrClient;

    @Resource
    private OfficeHouseDao officeHouseDao;

    @Resource
    private AreaDao areaDao;

    private static final String coreName="officehouse_core";

    /**
     * 建立索引
     * @param id
     */
    @Override
    @Async
    public void addOfficeHouseIndex(Integer id) {
        if(id!=null){
            Map<String,Object> map=officeHouseDao.selectByIdForSolr(id);
            if(map!=null){
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
    public void deleteOfficeHouseIndex(Integer id) {
        if(id!=null){
            SolrTool.delete(id+"",solrClient,coreName);
        }
    }

    /**
     * 搜索筛选
     * @param request
     * @return
     */
    @Override
    public ResultInfo<SolrOfficeHouse> searchOfficeHouse(HttpServletRequest request) {
        SolrQuery solrQuery=new SolrQuery();
        if(request.getParameter("keyword")!=null){
            solrQuery.set("q","name:\""+request.getParameter("keyword")+"\"");
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
                String[] pp = price[i].split("-");
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
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("type")!=null){//类型
            String [] type=request.getParameter("type").split(",");;
            StringBuffer stringBuffer=new StringBuffer();
            for(int i=0;i<type.length;i++){
                stringBuffer.append("type:").append(type[i]);
                if(i!=type.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("sort")!=null){// createtime desc,monthlyRent asc,monthlyRent desc
            String[] houseType=request.getParameter("sort").split(",");
            if( !houseType[0].equals("")){
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
        ResultInfo<SolrOfficeHouse> resultInfo= SolrTool.queryList(SolrOfficeHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    /**
     * 查询动态信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryInfo(Integer id) {
        return officeHouseDao.queryInfo(id);
    }

    @Override
    @Cacheable(value = "officehouse",key = "#id",unless = "#result==null")
    public Map<String, Object> queryDetail(Integer id) {
        return officeHouseDao.queryDetail(id);
    }


    @Override
    public ResultInfo<SolrOfficeHouse> getKeywordComplet(String keyword) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","name:\""+keyword+"\"");
        solrQuery.set("fl","id,name,monthlyRent");
        solrQuery.set("sort", "query({!v=name:"+keyword+"}) asc");
        ResultInfo<SolrOfficeHouse> resultInfo=SolrTool.queryList(SolrOfficeHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }
}
