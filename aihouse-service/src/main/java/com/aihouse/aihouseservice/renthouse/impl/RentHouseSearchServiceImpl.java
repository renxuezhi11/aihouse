package com.aihouse.aihouseservice.renthouse.impl;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousecore.utils.SolrTool;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.RentHouse;
import com.aihouse.aihousedao.bean.SolrNewHouse;
import com.aihouse.aihousedao.bean.SolrRentHouse;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihousedao.dao.renthouse.SysRentHouseDao;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class RentHouseSearchServiceImpl implements RentHouseSearchService {

    //自动注入solr客户端
    @Resource
    private SolrClient solrClient;

    @Resource
    private SysRentHouseDao sysRentHouseDao;

    @Resource
    private AreaDao areaDao;

    private static final String coreName="renthouse_core";


    @Override
    public SysRentHouseDao initDao() {
        return this.sysRentHouseDao;
    }

    /**
     * app 首页推荐查询
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    @Override
    public ResultInfo<SolrRentHouse> recommendHouse(String lng, String lat, Integer start, Integer rows) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","*:*");
        if(lng!=null &&lat!=null){
            solrQuery.set("pt",lat+","+lng);//当前经纬度
            solrQuery.set("sfield", "loc"); //经纬度的字段
            solrQuery.set("sort", "geodist() asc"); //距离排序
        }
        solrQuery.setStart(start);
        solrQuery.setRows(rows);
        solrQuery.set("fl","*");
        ResultInfo<SolrRentHouse> resultInfo= SolrTool.queryList(SolrRentHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    /**
     * 添加索引
     * @param id
     */
    @Override
    @Async
    public void addRentHouseIndex(Integer id) {
        if(id!=null){
            Map<String,Object> map=sysRentHouseDao.selectByIdForSolr(id);
            if(map!=null){
                Set<String> feature=new HashSet<>();
                if(map.get("isLift").toString().equals(0)){
                    feature.add("电梯");
                }else{
                    feature.add("楼梯");
                }
                map.put("feature",feature);
                map.remove("isLift");
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
    public void deleteRentHouseIndex(Integer id) {
        if(id!=null){
            SolrTool.delete(id+"",solrClient,coreName);
        }
    }

    /**
     * 租房搜索筛选
     * @param request
     * @return
     */
    @Override
    public ResultInfo<SolrRentHouse> searchRentHouse(HttpServletRequest request) {
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
                    stringBuffer.append("cityId:").append(areas[0]);
                } else if(area.getLevel()==3){
                    stringBuffer.append("areaId:").append(areas[0]);
                }else if(area.getLevel()==4){
                    stringBuffer.append("streesId:").append(areas[0]);
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
                    //solrQuery.set("fq","rentFee:["+pp[0]+" TO *]");
                   // fq.add("rentFee:[" + pp[0] + " TO *]");
                    stringBuffer.append("rentFee:[" + pp[0] + " TO *]");
                } else {
                    if (!pp[0].equals("")) {
                        //solrQuery.set("fq","rentFee:["+pp[0]+" TO "+pp[1]+"]");
//                        fq.add("rentFee:[" + pp[0] + " TO " + pp[1] + "]");
                        stringBuffer.append("rentFee:[" + pp[0] + " TO " + pp[1] + "]");
                    } else {
                        //solrQuery.set("fq","rentFee:[ 0 TO "+pp[1]+"]");
//                        fq.add("rentFee:[ 0 TO " + pp[1] + "]");
                        stringBuffer.append("rentFee:[ 0 TO " + pp[1] + "]");
                    }
                }
                if(i!=price.length-1){
                    stringBuffer.append(" OR ");
                }
            }
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("room")!=null){
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
            String[] fixture=request.getParameter("fixture").split(",");
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
        if(request.getParameter("mode")!=null){//整租、合租
            String[] mode=request.getParameter("mode").split(",");;
            //solrQuery.set("fq","rentMode:"+mode[0]);
            fq.add("rentMode:"+mode[0]);
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
            //solrQuery.set("fq",stringBuffer.toString());
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
            //solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("sort")!=null){// createtime desc,rentFee asc,rentFee desc
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
        ResultInfo<SolrRentHouse> resultInfo= SolrTool.queryList(SolrRentHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }


    @Override
    public Map<String, Object> queryInfo(Integer id) {
        return sysRentHouseDao.queryInfo(id);
    }


    @Override
    @Cacheable(value = "rent",key = "#id",unless = "#result == null")
    public HashMap<String, Object> queryDetail(Integer id) {
        return sysRentHouseDao.queryDetail(id);
    }

    @Override
    public ResultInfo<SolrRentHouse> getkeywordComplet(String keyword) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","villageName:\""+keyword+"\"");
        solrQuery.set("fl","id,villageName,rentFee");
        solrQuery.set("sort", "query({!v=villageName:"+keyword+"}) asc");
        ResultInfo<SolrRentHouse> resultInfo=SolrTool.queryList(SolrRentHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    @CacheEvict(value = "rent",key = "#p0.id")
    @Override
    public int updateRentHouse(RentHouse rentHouse) {
        return this.update(rentHouse);
    }
}
