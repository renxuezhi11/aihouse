package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.PriceNoticeService;
import com.aihouse.aihouseservice.SysNewHouseBrokerageService;
import com.aihouse.aihouseservice.SysNewHouseTypeService;
import com.aihouse.aihouseservice.newhouse.*;
import com.aihouse.aihouseservice.users.UserCollectService;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * app新房查询
 */
@CrossOrigin
@RestController
public class NewHouseSearchController {

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private UserSearchService userSearchService;

    @Resource
    private NewHouseCommentService newHouseCommentService;

    @Resource
    private SysNewHouseTypeService sysNewHouseTypeService;

    @Resource
    private UserHistoryService userHistoryService;

    @Resource
    private NewHouseImgService newHouseImgService;


    @Resource
    private NewHouseStateService newHouseStateService;

    @Resource
    private UserCollectService userCollectService;

    @Resource
    private PriceNoticeService priceNoticeService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private NewHouseBrokerService newHouseBrokerService;

    @Resource
    private SysNewHouseBrokerageService sysNewHouseBrokerageService;

    /**
     * app首页新房推荐查询（根据经纬度查询附近的楼盘，按照距离排序）
     * @param request
     * @param response
     * @param lng
     * @param lat
     * @return
     */
    @RequestMapping(value = "app/getRecommendNewHouse",method = RequestMethod.POST)
    public DataRes recommendHouse(HttpServletRequest request, HttpServletResponse response,String lng,String lat,Integer start,Integer rows){
        System.out.println(request.getParameter("start"));;
        ResultInfo<SolrNewHouse> data=newHouseSearchService.recommendHouse(lng, lat, start, rows);
        //待完善其他信息
        for(SolrNewHouse s:data.getList()){
            Map newHouse=newHouseSearchService.queryInfo(s.getId());
            if(newHouse.get("page_view")!=null){
                s.setPageView(Integer.parseInt(newHouse.get("page_view").toString()));
            }else{
                s.setPageView(0);
            }
            if(newHouse.get("score")!=null){
                s.setScore(Double.parseDouble(newHouse.get("score").toString()));
            }else{
                s.setScore(0.0);
            }
            if(newHouse.get("picture")!=null) {
                s.setPicture(newHouse.get("picture").toString());
            }else{
                s.setPicture("");
            }
            s.setAreaName(newHouse.get("areaname").toString());
            s.setStreetsName(newHouse.get("streesname").toString());
        }
        return DataRes.success(data);
    }

    /**
     * 关键字补全接口
     * @param keyword
     * @return
     */
    @RequestMapping(value = "app/newhouse/getKeywordComplet")
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
                list.add(map);
            }
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * app新房搜索 筛选功能
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchNewHouse",method = RequestMethod.POST)
    public DataRes searchNewHouse(HttpServletRequest request,HttpServletResponse response){

        ResultInfo<SolrNewHouse> data=newHouseSearchService.searchNewHouse(request);
        //完善其他动态信息
        for(SolrNewHouse s:data.getList()){
            Map newHouse=newHouseSearchService.queryInfo(s.getId());
            if(newHouse.get("page_view")!=null){
                s.setPageView(Integer.parseInt(newHouse.get("page_view").toString()));
            }else{
                s.setPageView(0);
            }
            if(newHouse.get("score")!=null){
                s.setScore(Double.parseDouble(newHouse.get("score").toString()));
            }else{
                s.setScore(0.0);
            }
            if(newHouse.get("picture")!=null) {
                s.setPicture(newHouse.get("picture").toString());
            }else{
                s.setPicture("");
            }
            s.setAreaName(newHouse.get("areaname").toString());
            s.setStreetsName(newHouse.get("streesname").toString());
        }
        if(request.getParameter("keyword")!=null&&!request.getParameter("keyword").equals("")){
            //添加用户搜索记录
            if(request.getParameter("flag")!=null&&request.getParameter("flag").equals("1")) {//关键字
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    if (redisUtil.get(RedisConstants.USER_TOKEN + userId) != null) {
                        SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
                        UserSearch userSearch = new UserSearch();
                        userSearch.setType(1);
                        userSearch.setContent(request.getParameter("keyword"));
                        userSearch.setUserId(sessionUser.getUsers().getId());
                        userSearchService.insert(userSearch);
                    }
                }
                //热门搜索redis,暂不分地区
                redisUtil.zAdd("newhouseHotSearch", request.getParameter("keyword"), 1);
            }
        }
        return DataRes.success(data);
    }

    /**
     * 获取新房热搜关键字
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "app/getNewHouseHotSearch",method = RequestMethod.POST)
    public DataRes hotSearch(Integer start,Integer end,HttpServletRequest request){
        Set<Object> set=redisUtil.zreverseRange("newhouseHotSearch",start,end);

        Set<String> search=new HashSet<>();
        if(request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            UserSearch userSearch = new UserSearch();
            userSearch.setPage(1);
            userSearch.setPageSize(10);
            userSearch.setType(1);
            userSearch.setUserId(Integer.parseInt(request.getHeader("token").split(SessionUser.FLAG)[1]));
            userSearch.setOrderByString(" order by create_time desc");
            userSearchService.selectAllByPaging(userSearch);
            for (UserSearch s : (List<UserSearch>) userSearch.getRows()) {
                search.add(s.getContent());
            }
        }
        //long set=redisUtil.zAdd("newhouseHotSearch","asdsd1",1);
        Map<String,Object> map=new HashMap<>();
        map.put("hotsearch",set);
        map.put("historysearch",search);
        return DataRes.success(map);
    }

    /**
     * app 新房详情页面查询接口
     * @return
     */
    @RequestMapping(value = "app/getNewHouseDetail/{id}",method = RequestMethod.POST)
    public DataRes getNewHouseDetail(@PathVariable("id") Integer id,HttpServletRequest request,HttpServletResponse response){
        if(id!=null){
            Map<String,Object> map=newHouseSearchService.queryDetail(id);
            //置业顾问
            List list=newHouseSearchService.queryBroker(id);
            map.put("broker",list);
            //评论
            if(request.getHeader("token")!=null&&!request.getHeader("token").equals("")) {
                List<Map<String, Object>> listComment = newHouseCommentService.selectAllComment(id, Integer.parseInt(request.getHeader("token").split(SessionUser.FLAG)[1]), 1, 5);
                map.put("comment", listComment);
            }else{
                List<Map<String, Object>> listComment = newHouseCommentService.selectAllComment(id, 0, 1, 5);
                map.put("comment", listComment);
            }
            //户型
            SysNewHouseType sysNewHouseType=new SysNewHouseType();
            sysNewHouseType.setNewHouseId(id);
            sysNewHouseType.setOrderByString(" order by covered_area asc");
            List<SysNewHouseType> houseTypes=sysNewHouseTypeService.selectAll(sysNewHouseType);
            if(houseTypes.size()>0){//总价
                if(houseTypes.get(0).getTotalPrice()!=null) {
                    map.put("totalprice", houseTypes.get(0).getTotalPrice() + "万/套起");
                }else{
                    if(houseTypes.get(0).getCoveredArea()!=null){
                        map.put("totalprice" ,houseTypes.get(0).getCoveredArea().multiply(new BigDecimal(map.get("average_price").toString())).divide(new BigDecimal(10000)).intValue()+ "万/套起");
                    }else {
                        map.put("totalprice", "暂无数据");
                    }
                }
                Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);//降序排列
                    }
                });
                for(SysNewHouseType s:houseTypes){
                    if(s.getRoom()!=null){
                        sortSet.add(s.getRoom().toString());
                    }
                }
                List<String> list1 = new ArrayList<>(sortSet);
                if(list1.size()>0) {
                    String str = String.join("/", list1);
                    map.put("type", str + "居");
                }else{
                    map.put("type","暂无数据");
                }
                map.put("coveredarea",houseTypes.get(0).getCoveredArea().intValue()+"-"+houseTypes.get(houseTypes.size()-1).getCoveredArea().intValue()+"m²");
            }else{
                map.put("totalprice","暂无");
                map.put("type","暂无");
                map.put("coveredarea","暂无");
            }
            map.put("housetype",houseTypes);

         /*   //开盘
            NewHouseState newHouseState=new NewHouseState();
            newHouseState.setNewHouseId(id);
            newHouseState.setType(1);
            newHouseState.setOrderByString("order by createtime desc");
            List<NewHouseState> listState=newHouseStateService.selectAll(newHouseState);
            if(listState.size()>0){
                map.put("state",listState.get(0));
            }else{
                map.put("state","");
            }*/
            //浏览记录
            if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
                //map.put("role",sessionUser.getUsers().getRole());
                if(sessionUser.getUsers().getRole()==6){//合伙人
                    //是否入驻
                    NewHouseBroker newHouseBroker=new NewHouseBroker();
                    newHouseBroker.setNewHouseId(id);
                    newHouseBroker.setBrokerId(sessionUser.getUsers().getId());
                    newHouseBroker.setStatus(0);
                    List<NewHouseBroker> newHouseBrokerList=newHouseBrokerService.selectAll(newHouseBroker);
                    if(newHouseBrokerList!=null&&newHouseBrokerList.size()>0){
                        map.put("houseBroker",true);
                    }else{
                        map.put("houseBroker",false);
                    }
                    //楼盘佣金
                    SysNewHouseBrokerage sysNewHouseBrokerage=new SysNewHouseBrokerage();
                    sysNewHouseBrokerage.setNewHouseId(id);
                    sysNewHouseBrokerage.setIsDel(0);
                    sysNewHouseBrokerage.setOrderByString(" order by createtime desc");
                    List<SysNewHouseBrokerage> sysNewHouseBrokerageList=sysNewHouseBrokerageService.selectAll(sysNewHouseBrokerage);
                    if(sysNewHouseBrokerageList!=null&&sysNewHouseBrokerageList.size()>0){
                        if(sysNewHouseBrokerageList.get(0).getBrokerageModel()==1){
                            if(sysNewHouseBrokerageList.get(0).getBrokerageMoney()!=null) {
                                map.put("brokerage", sysNewHouseBrokerageList.get(0).getBrokerageScale().toString() + "%+"+sysNewHouseBrokerageList.get(0).getBrokerageMoney().intValue());
                            }else{
                                map.put("brokerage", sysNewHouseBrokerageList.get(0).getBrokerageScale().toString() + "%");
                            }
                        }else{
                            map.put("brokerage",sysNewHouseBrokerageList.get(0).getBrokerageMoney().intValue()+"元/套");
                        }
                    }else{
                        map.put("brokerage","暂无数据");
                    }
                }
                //是否收藏
                UserCollect userCollect=new UserCollect();
                userCollect.setHouseId(id);
                userCollect.setUserId(Integer.parseInt(userId));
                userCollect.setCollectType(1);
                List list1=userCollectService.selectAll(userCollect);
                if(list1.size()>0){
                    map.put("isCollect",true);
                }else{
                    map.put("isCollect",false);
                }
                PriceNotice priceNotice=new PriceNotice();
                priceNotice.setHouseId(id);
                priceNotice.setUserId(Integer.parseInt(userId));
                priceNotice.setHouseType(1);
                list1=priceNoticeService.selectAll(priceNotice);
                if(list1.size()>0){
                    map.put("isNotice",true);
                }else{
                    map.put("isNotice",false);
                }
                if (redisUtil.get(RedisConstants.USER_TOKEN + userId) != null) {
                    String picture="";
                    if(map.get("img_url")!=null) {
                        picture = map.get("img_url").toString();
                    }
                    double price= new BigDecimal(map.get("average_price").toString()).doubleValue() ;
                    String unit="元/平";
                    String area=map.get("areaname").toString();
                    String street=map.get("streesname").toString();
                    String title=map.get("name").toString();
                    userHistoryService.userHistoryLog(sessionUser.getUsers().getId(),1,id,picture,price,unit,area,street,title,"");
                }
            }
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取楼盘图片
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getNewHouseImg/{id}",method = RequestMethod.POST)
    public DataRes getNewHouseImg(@PathVariable("id") Integer id){
        if(id!=null){
            List<Map<String,Object>> list=newHouseImgService.queryImgType(id);
            List<Map<String,Object>> tar=new ArrayList<>();
            list.forEach(t->{
                NewHouseImg newHouseImg=new NewHouseImg();
                newHouseImg.setNewHouseId(id);
                newHouseImg.setImgType(Integer.parseInt(t.get("id").toString()));
                newHouseImg.setIsDel(0);//未删除的图片
                Map<String,Object> map=new HashMap<>();
                map.putAll(t);
                tar.add(map);
                t.put("data",newHouseImgService.queryImgByTypeAndHouseId(newHouseImg));
            });
            Map<String,Object> map=new HashMap<>();
            map.put("tabBars",tar);
            map.put("imgs",list);
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取楼盘入驻所有经纪人
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getNewHouseAllBroker/{id}",method = RequestMethod.POST)
    public DataRes getNewHouseAllBroker(@PathVariable("id") Integer id){
        if(id!=null){
            List<Map<String,Object>> list=newHouseSearchService.getNewHouseAllBroker(id);
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 楼盘详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getNewHouseMoreDetail/{id}",method = RequestMethod.POST)
    public DataRes getNewHouseMoreDetail(@PathVariable("id") Integer id){
        if(id!=null){
            Map<String,Object>map=newHouseSearchService.queryMoreDetail(id);
            NewHouseState newHouseState=new NewHouseState();
            newHouseState.setNewHouseId(id);
            newHouseState.setOrderByString("order by createtime desc");
            List<NewHouseState> listState=newHouseStateService.selectAll(newHouseState);
            map.put("state",listState);
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取户型详情
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getNewHouseTypeDetail/{id}",method = RequestMethod.POST)
    public DataRes getNewHouseTypeDetail(@PathVariable("id") Integer id){
        if(id!=null){
            SysNewHouseType sysNewHouseType=new SysNewHouseType();
            sysNewHouseType.setId(id);
            sysNewHouseType=sysNewHouseTypeService.selectByPrimaryKey(sysNewHouseType);
            return DataRes.success(sysNewHouseType);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
