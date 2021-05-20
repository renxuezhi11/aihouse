package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.PriceNoticeService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseImgService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.users.UserCollectService;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * app 写字楼搜索
 */
@CrossOrigin
@RestController
public class OfficeHouseSearchController {

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;

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
    private OfficeHouseImgService officeHouseImgService;
    /**
     * app 搜索筛选
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchOfficeHouse",method = RequestMethod.POST)
    public DataRes searchOfficeHouse(HttpServletRequest request, HttpServletResponse response){
        ResultInfo<SolrOfficeHouse> data=officeHouseSearchService.searchOfficeHouse(request);
        for(SolrOfficeHouse s:data.getList()){
            Map<String,Object> map=officeHouseSearchService.queryInfo(s.getId());
            s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
            s.setPageView(Integer.parseInt((map.get("page_view").toString())));
            if(map.get("nickname")!=null){
                s.setUserName(map.get("nickname").toString());
            }
            s.setPicture(map.get("picture").toString());
            s.setAreaName(map.get("areaname").toString());
            s.setStreetsName(map.get("streesname").toString());
        }
        if(request.getParameter("keyword")!=null&&!request.getParameter("keyword").equals("")){
            //添加用户搜索记录
            if(request.getParameter("flag")!=null&&request.getParameter("flag").equals("1")) {//关键字
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    if (redisUtil.get(RedisConstants.USER_TOKEN + userId) != null) {
                        SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
                        UserSearch userSearch = new UserSearch();
                        userSearch.setType(5);
                        userSearch.setContent(request.getParameter("keyword"));
                        userSearch.setUserId(sessionUser.getUsers().getId());
                        userSearchService.insert(userSearch);
                    }
                }
                //热门搜索redis,暂不分地区
                redisUtil.zAdd("officehouseHotSearch", request.getParameter("keyword"), 1);
            }
        }
        return DataRes.success(data);
    }

    /**
     * 获取写字楼热搜关键字
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "app/getOfficeHouseHotSearch",method = RequestMethod.POST)
    public DataRes hotSearch(Integer start,Integer end,HttpServletRequest request){
        Set<Object> set=redisUtil.zreverseRange("officehouseHotSearch",start,end);
        //long set=redisUtil.zAdd("newhouseHotSearch","asdsd1",1);
        Set<String> search=new HashSet<>();
        if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            UserSearch userSearch = new UserSearch();
            userSearch.setPage(1);
            userSearch.setPageSize(10);
            userSearch.setType(5);
            userSearch.setUserId(Integer.parseInt(request.getHeader("token").split(SessionUser.FLAG)[1]));
            userSearch.setOrderByString(" order by create_time desc");
            userSearchService.selectAllByPaging(userSearch);
            for(UserSearch s:(List<UserSearch>)userSearch.getRows()){
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
     * 关键字自动补全
     * @param keyword
     * @return
     */
    @RequestMapping(value = "app/officehouse/getKeywordComplet")
    public DataRes getKeywordComplet(String keyword){
        if(keyword!=null &&!keyword.trim().equals("")){
            ResultInfo<SolrOfficeHouse> data=officeHouseSearchService.getKeywordComplet(keyword);
            List<Map<String,Object>> list=new ArrayList<>();
            for(SolrOfficeHouse s:data.getList()){
                Map<String,Object> map=officeHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                if(map.get("areaname")!=null) {
                    map1.put("areaname", map.get("areaname").toString());
                }
                if(map.get("streesname")!=null) {
                    map1.put("streesname", map.get("streesname").toString());
                }
                map1.put("name",s.getName());
                map1.put("price",s.getMonthlyRent());
                list.add(map1);
            }
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
    /**
     * app 获取详情页面接口
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getOfficeHouseDetail/{id}",method = RequestMethod.POST)
    public DataRes getOfficeHouseDetail(@PathVariable("id") Integer id, HttpServletRequest request){
        if(id!=null){
            Map<String,Object> map=officeHouseSearchService.queryDetail(id);
            if(map.get("flag").toString().equals("1")) {
                map.put("unitprice", new BigDecimal(map.get("monthly_rent").toString()).divide(new BigDecimal(map.get("covered_area").toString()),2,ROUND_HALF_DOWN).intValue());
                double ss = Double.parseDouble(map.get("floor_levels").toString()) / Double.parseDouble(map.get("total_floor").toString());
                if (ss > 0 && ss <= 0.33) {
                    map.put("fl", "低层");
                } else if (ss > 0.33 && ss <= 0.66) {
                    map.put("fl", "中层");
                } else {
                    map.put("fl", "高层");
                }
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    //是否收藏
                    UserCollect userCollect = new UserCollect();
                    userCollect.setHouseId(id);
                    userCollect.setUserId(Integer.parseInt(userId));
                    userCollect.setCollectType(5);
                    List list1 = userCollectService.selectAll(userCollect);
                    if (list1.size() > 0) {
                        map.put("isCollect", true);
                    } else {
                        map.put("isCollect", false);
                    }
                    PriceNotice priceNotice = new PriceNotice();
                    priceNotice.setHouseId(id);
                    priceNotice.setUserId(Integer.parseInt(userId));
                    priceNotice.setHouseType(5);
                    list1 = priceNoticeService.selectAll(priceNotice);
                    if (list1.size() > 0) {
                        map.put("isNotice", true);
                    } else {
                        map.put("isNotice", false);
                    }
                    String picture = map.get("img_url").toString();
                    double price = (double) map.get("monthly_rent");
                    String unit = "元/月";
                    String area = map.get("areaname").toString();
                    String street = map.get("streesname").toString();
                    String title = map.get("title").toString();
                    String content = map.get("covered_area").toString() + "平 " + map.get("name").toString();
                    userHistoryService.userHistoryLog(Integer.parseInt(userId), 5, id, picture, price, unit, area, street, title, content);
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
     * 获取写字楼图片列表
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getOfficeHouseImg/{id}",method = RequestMethod.POST)
    public DataRes getOfficeHouseImg(@PathVariable("id") Integer id){
        if(id!=null){
            OfficeHouseImg officeHouseImg=new OfficeHouseImg();
            officeHouseImg.setOfficeID(id);
            List<OfficeHouseImg> list=officeHouseImgService.selectAll(officeHouseImg);
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
