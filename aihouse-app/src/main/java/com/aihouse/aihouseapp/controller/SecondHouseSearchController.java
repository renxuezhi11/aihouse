package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.PriceNoticeService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.users.UserCollectService;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.apache.commons.math3.dfp.DfpField;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * app二手房查询
 */
@CrossOrigin
@RestController
public class SecondHouseSearchController {


    @Resource
    private SecondHouseSearchService secondHouseSearchService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserSearchService userSearchService;

    @Resource
    private UserCollectService userCollectService;

    @Resource
    private PriceNoticeService priceNoticeService;

    @Resource
    private UserHistoryService userHistoryService;

    @Resource
    private SecondHandHouseImgService secondHandHouseImgService;
    /**
     * app首页二手房推荐查询（根据经纬度查询附近的房源，按照距离排序）
     * @param request
     * @param response
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    @RequestMapping(value = "app/getRecommendSecondHouse",method = RequestMethod.POST)
    public DataRes recommendSecondHouse(HttpServletRequest request, HttpServletResponse response,String lng,String lat,Integer start,Integer rows,Integer sale){
        ResultInfo<SolrSecondHouse> data=secondHouseSearchService.recommendSecondHouse(lng, lat, start, rows,sale);
        //待完善动态信息
        for(SolrSecondHouse s:data.getList()){
            Map map=secondHouseSearchService.queryInfo(s.getId());
            if(map!=null) {
                s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
                s.setPageView(Integer.parseInt(map.get("page_view").toString()));
                s.setPicture(ObjectUtils.isEmpty(map.get("picture"))? null:map.get("picture").toString());
                if (map.get("nickname") != null) {
                    s.setUserName(map.get("nickname").toString());
                }
                if(map.get("areaname")!=null) {
                    s.setAreaName(map.get("areaname").toString());
                }else{
                    s.setAreaName("");
                }
                if(map.get("streesname")!=null) {
                    s.setStreetsName(map.get("streesname").toString());
                }else{
                    s.setStreetsName("");
                }
                if (s.getPrice() != null && s.getCoveredArea() != null) {
                    Double ss=s.getPrice() * 10000/s.getCoveredArea();
                    s.setUnitPrice(ss.intValue());
                }
                if(map.get("schoolName")!=null){
                    s.setSchoolName(map.get("schoolName").toString());
                }
            }

        }
        return DataRes.success(data);
    }

    /**
     * app 二手房搜索，筛选接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchSecondHouse",method = RequestMethod.POST)
    public DataRes searchSecondHouse(HttpServletRequest request,HttpServletResponse response){
        ResultInfo<SolrSecondHouse> data=secondHouseSearchService.searchSecondHouse(request);
        //待完善动态信息
        for(SolrSecondHouse s:data.getList()){
            Map map=secondHouseSearchService.queryInfo(s.getId());
            if(map!=null) {
                s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
                s.setPageView(Integer.parseInt(map.get("page_view").toString()));
                s.setPicture(ObjectUtils.isEmpty(map.get("picture"))?"":map.get("picture").toString());
                if (map.get("nickname") != null) {
                    s.setUserName(map.get("nickname").toString());
                }
                if(map.get("areaname")!=null) {
                    s.setAreaName(map.get("areaname").toString());
                }
                if(map.get("streesname")!=null) {
                    s.setStreetsName(map.get("streesname").toString());
                }
                if (s.getPrice() != null && s.getCoveredArea() != null) {
                    Double ss=s.getPrice() * 10000/s.getCoveredArea();
                    s.setUnitPrice(ss.intValue());
                }
                if(map.get("schoolName")!=null){
                    s.setSchoolName(map.get("schoolName").toString());
                }
            }
        }
        if(request.getParameter("keyword")!=null&&!request.getParameter("keyword").equals("")){
            //添加用户搜索记录
            if(request.getParameter("flag")!=null&&request.getParameter("flag").equals("1")) {//关键字
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    if (redisUtil.get(RedisConstants.USER_TOKEN + userId) != null) {
                        SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
                        UserSearch userSearch = new UserSearch();
                        userSearch.setType(2);
                        userSearch.setContent(request.getParameter("keyword"));
                        userSearch.setUserId(sessionUser.getUsers().getId());
                        userSearchService.insert(userSearch);
                    }
                }
                //热门搜索redis,暂不分地区
                redisUtil.zAdd("secondhouseHotSearch", request.getParameter("keyword"), 1);
            }
        }
        return DataRes.success(data);
    }
    /**
     * 关键字补全接口
     * @param keyword
     * @return
     */
    @RequestMapping(value = "app/secondhouse/getKeywordComplet")
    public DataRes getKeywordComplet(String keyword){
        if(keyword!=null &&!keyword.trim().equals("")){
            ResultInfo<SolrSecondHouse> data=secondHouseSearchService.getKeywordComplet(keyword);
            List<Map<String,Object>> list=new ArrayList<>();
            for(SolrSecondHouse s:data.getList()) {
                Map map=secondHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getVillageName());
                map1.put("price",s.getPrice());
                list.add(map1);
            }
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取二手房热搜关键字
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "app/getSecondHouseHotSearch",method = RequestMethod.POST)
    public DataRes hotSearch(Integer start,Integer end,HttpServletRequest request){
        Set<Object> set=redisUtil.zreverseRange("secondhouseHotSearch",start,end);

        Set<String> search=new HashSet<>();
        if(request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            UserSearch userSearch = new UserSearch();
            userSearch.setPage(1);
            userSearch.setPageSize(10);
            userSearch.setType(2);
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
     * app 二手房详情页面
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/getSecondHousedetail/{id}",method = RequestMethod.POST)
    public DataRes getSecondHousedetail(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){
            Map<String,Object> map=secondHouseSearchService.queryDetail(id);
            if(map.get("flag").toString().equals("1")) {
                map.put("unitprice", new BigDecimal(map.get("price").toString()).multiply(new BigDecimal(10000)).divide(new BigDecimal(map.get("covered_area").toString()),2,ROUND_HALF_DOWN).intValue());
                /*double ss = Double.parseDouble(map.get("floor").toString()) / Double.parseDouble(map.get("total_floor").toString());
                if (ss > 0 && ss < 0.33) {
                    map.put("fl", "低层");
                } else if (ss > 0.33 && ss < 0.66) {
                    map.put("fl", "中层");
                } else {
                    map.put("fl", "高层");
                }*/
                //map.remove("floor");
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    //是否收藏
                    UserCollect userCollect = new UserCollect();
                    userCollect.setHouseId(id);
                    userCollect.setUserId(Integer.parseInt(userId));
                    userCollect.setCollectType(2);
                    List list1 = userCollectService.selectAll(userCollect);
                    if (list1.size() > 0) {
                        map.put("isCollect", true);
                    } else {
                        map.put("isCollect", false);
                    }
                    PriceNotice priceNotice = new PriceNotice();
                    priceNotice.setHouseId(id);
                    priceNotice.setUserId(Integer.parseInt(userId));
                    priceNotice.setHouseType(2);
                    list1 = priceNoticeService.selectAll(priceNotice);
                    if (list1.size() > 0) {
                        map.put("isNotice", true);
                    } else {
                        map.put("isNotice", false);
                    }
                    String picture = ObjectUtils.isEmpty(map.get("img_url"))?"":map.get("img_url").toString();
                    double price = (double) map.get("price");
                    String unit = "万";
                    String area="";
                    String street="";
                    if(map.get("areaname")!=null) {
                        area = map.get("areaname").toString();
                    }
                    if(map.get("streesname")!=null) {
                        street = map.get("streesname").toString();
                    }
                    String title = map.get("title").toString();
                    String content = map.get("room").toString() + "室" + map.get("hall").toString() + "厅 " + map.get("covered_area").toString() + "平 " + map.get("village_name").toString();
                    userHistoryService.userHistoryLog(Integer.parseInt(userId), 2, id, picture, price, unit, area, street, title, content);
                }
                return DataRes.success(map);
            }else{
                return DataRes.error(ResponseCode.HOUSE_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取二手房图片列表
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getSecondHouseImg/{id}",method = RequestMethod.POST)
    public DataRes getSecondHouseImg(@PathVariable("id") Integer id){
        if(id!=null){
            SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
            secondHandHouseImg.setSecondHouse(id);
            secondHandHouseImg.setIsDel(0);
            secondHandHouseImg.setImgType(1);
            List<SecondHandHouseImg> list=secondHandHouseImgService.selectAll(secondHandHouseImg);
            List<String> array=new ArrayList<>();
            list.forEach(t->{
                array.add(t.getImgUrl());
            });
            return DataRes.success(array);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

}
