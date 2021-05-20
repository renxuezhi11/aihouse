package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousecore.utils.SolrTool;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihousedao.dao.AreaDao;
import com.aihouse.aihousedao.dao.SysNewHouseTypeDao;
import com.aihouse.aihousedao.dao.newhouse.NewHouseDao;
import com.aihouse.aihousedao.dao.users.UsersDao;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 新房搜索service
 */
@Service
public class NewHouseServiceSearchImpl implements NewHouseSearchService {

    //自动注入solr客户端
    @Resource
    private SolrClient solrClient;

    @Resource
    private NewHouseDao newHouseDao;

    @Resource
    private SysNewHouseTypeDao sysNewHouseTypeDao;

    @Resource
    private AreaDao areaDao;

    @Resource
    private UsersDao usersDao;

    public static final String coreName="newhouse_core";
    /**
     * app新房推荐查询
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    @Override
    public ResultInfo<SolrNewHouse> recommendHouse(String lng,String lat,Integer start,Integer rows) {
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
        ResultInfo<SolrNewHouse> resultInfo=SolrTool.queryList(SolrNewHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    /**
     * 新房添加索引
     * @param id
     */
    @Override
    @Async
    public void addNewHouseIndex(Integer id) {
        if(id!=null){
            try {
                Map<String, Object> map = newHouseDao.selectByIdForSolr(id);
                if (map != null) {
                    map.put("averagePrice", ((BigDecimal) map.get("averagePrice")).doubleValue());
                    SysNewHouseType sysNewHouseType = new SysNewHouseType();
                    sysNewHouseType.setNewHouseId(id);
                    List<SysNewHouseType> list = sysNewHouseTypeDao.selectAll(sysNewHouseType);
                    if (map.get("houseType") != null) {
                        String houseType = map.get("houseType").toString();
                        Set<String> houseTypeSet = new HashSet<>();
                        for (String str : houseType.split(",")) {
                            if(!str.equals(""))
                            houseTypeSet.add(str);
                        }
                        map.put("houseType", houseTypeSet);
                    }
                    if (list.size() > 0) {
                        Set<Double> coveredArea = new HashSet<>();
                        Set<Integer> room = new HashSet<>();
                        for (SysNewHouseType s : list) {
                            coveredArea.add(s.getCoveredArea().doubleValue());
                            room.add(s.getRoom());
                        }
                        map.put("coveredArea", coveredArea);
                        map.put("room", room);
                    }
                    SolrTool.addMap(map, solrClient, coreName);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 新房索引删除
     * @param id
     */
    @Override
    @Async
    public void deleteNewHouseIndex(Integer id) {
        if(id!=null){
            try {
                SolrTool.delete(id + "", solrClient, coreName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("id");
        }

    }

    /**
     * 索引搜索筛选
     * @param request
     * @return
     */
    @Override
    public ResultInfo<SolrNewHouse> searchNewHouse(HttpServletRequest request) {
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
            String [] areas=(String[])request.getParameter("area").split(",");
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
            String [] near=(String[])request.getParameter("near").split(",");
            if(!near[0].equals("0")){
                solrQuery.set("d", near[0]);
                //solrQuery.set("fq", "{!geofilt}");
                fq.add("{!geofilt}");
            }
        }
        if(request.getParameter("price")!=null){
            String [] price=(String[])request.getParameter("price").split(",");
            String [] pp=price[0].split("-");
            if(pp.length==1){
                //solrQuery.set("fq","averagePrice:["+pp[0]+" TO *]");
                fq.add("averagePrice:["+pp[0]+" TO *]");
            }else{
                if(!pp[0].equals("")){
                    //solrQuery.set("fq","averagePrice:["+pp[0]+" TO "+pp[1]+"]");
                    fq.add("averagePrice:["+pp[0]+" TO "+pp[1]+"]");
                }else{
                    //solrQuery.set("fq","averagePrice:[ 0 TO "+pp[1]+"]");
                    fq.add("averagePrice:[ 0 TO "+pp[1]+"]");
                }
            }
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
            System.out.println(stringBuffer.toString());
           // solrQuery.set("fq",stringBuffer.toString());
            fq.add(stringBuffer.toString());
        }
        if(request.getParameter("coveredArea")!=null){//面积
            String [] coveredArea=request.getParameter("coveredArea").split(",");;
            String ss=request.getParameter("coveredArea");;
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
            if(!fixture[0].equals("非毛坯")){
                //solrQuery.set("fq","fixture:"+fixture[0]);
                fq.add("fixture:"+fixture[0]);
            }else{
                //solrQuery.set("fq","!fixture:毛坯");
                fq.add("!fixture:毛坯");
            }
        }
        if(request.getParameter("sellStage")!=null){//在售
            String[] sellStage=request.getParameter("sellStage").split(",");;
            //solrQuery.set("fq","sellStage:"+sellStage[0]);
            fq.add("sellStage:"+sellStage[0]);
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
        String[] ss=new String[fq.size()];
        for(int i=0;i<fq.size();i++){
            ss[i]=fq.get(i);
        }
        solrQuery.set("fq",ss);
        if(request.getParameter("sort")!=null){// createtime desc,averagePrice asc,averagePrice desc
            String[] houseType=request.getParameter("sort").split(",");;
            if(!houseType[0].equals("")){
                solrQuery.set("sort", houseType[0]);
            }
        }
        solrQuery.setStart(Integer.parseInt(request.getParameter("start").toString()));
        solrQuery.setRows(Integer.parseInt(request.getParameter("rows").toString()));
        solrQuery.set("fl","*");
//        solrQuery.set("defType","edismax");
       // solrQuery.set("mm","100%");
        ResultInfo<SolrNewHouse> resultInfo=SolrTool.queryList(SolrNewHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }

    /**
     * 查询新房 图片，评分，浏览量
     * @param id
     * @return
     */
    @Override
    public  Map<String,Object>  queryInfo(Integer id) {
        return newHouseDao.queryInfo(id);
    }

    /**
     * app 查询详细信息
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "newhouse",key = "#id",unless = "#result==null")
    public Map<String, Object> queryDetail(Integer id) {
        return newHouseDao.queryDetail(id);
    }

    /**
     * 查询楼盘入驻推荐经纪人
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "newhousebroker",key = "#id",unless = "#result==null")
    public List<Map<String, Object>> queryBroker(Integer id) {
        return usersDao.queryBroker(id);
    }

    /**
     * 查询楼盘所有入驻经纪人
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "newhouseallbroker",key="#id",unless = "#result==null")
    public List<Map<String, Object>> getNewHouseAllBroker(Integer id) {
        return usersDao.getNewHouseAllBroker(id);
    }

    /**
     * 楼盘详细信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryMoreDetail(Integer id) {
        return newHouseDao.queryMoreDetail(id);
    }

    @Override
    public ResultInfo<SolrNewHouse> getKeywordComplet(String keyword) {
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.set("q","name:\""+keyword+"\"");
        solrQuery.set("fl","id,name,averagePrice");
        solrQuery.set("sort", "query({!v=name:"+keyword+"}) asc");
        ResultInfo<SolrNewHouse> resultInfo=SolrTool.queryList(SolrNewHouse.class,solrQuery,solrClient,coreName);
        return resultInfo;
    }
}
