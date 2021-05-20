package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.ResultInfo;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.PriceNoticeService;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseImgService;
import com.aihouse.aihouseservice.users.UserCollectService;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * app租房搜索
 */
@CrossOrigin
@RestController
public class RentHouseSearchController {

    @Resource
    private RentHouseSearchService rentHouseSearchService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserSearchService userSearchService;

    @Resource
    private UserCollectService userCollectService;

    @Resource
    private PriceNoticeService priceNoticeService;

    @Resource
    private SysRentHouseImgService sysRentHouseImgService;

    @Resource
    private UserHistoryService userHistoryService;

    /**
     *app首页租房查询
     * @param request
     * @param response
     * @param lng
     * @param lat
     * @param start
     * @param rows
     * @return
     */
    @RequestMapping(value = "app/getRecommendRentHouse",method = RequestMethod.POST)
    public DataRes recommendRentHouse(HttpServletRequest request, HttpServletResponse response, String lng, String lat, Integer start, Integer rows){
        ResultInfo<SolrRentHouse> data=rentHouseSearchService.recommendHouse(lng, lat, start, rows);
        //完善动态信息
        for(SolrRentHouse s:data.getList()){
            Map<String,Object> map=rentHouseSearchService.queryInfo(s.getId());
            s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
            s.setPageView(Integer.parseInt(map.get("page_view").toString()));
            if(map.get("nickname")!=null){
                s.setUserName(map.get("nickname").toString());
            }
            s.setPicture(map.get("picture").toString());
            s.setAreaName(map.get("areaname").toString());
            s.setStreetsName(map.get("streesname").toString());
        }

        return DataRes.success(data);
    }

    /**
     * 关键字补全
     * @param keyword
     * @return
     */
    @RequestMapping(value = "app/renthouse/getKeywordComplet",method = RequestMethod.POST)
    public DataRes getkeywordComplet(String keyword){
        if(keyword!=null &&!keyword.trim().equals("")){
            ResultInfo<SolrRentHouse> data=rentHouseSearchService.getkeywordComplet(keyword);
            List<Map<String,Object>> list=new ArrayList<>();
            for(SolrRentHouse s:data.getList()){
                Map<String,Object> map=rentHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getVillageName());
                map1.put("price",s.getRentFee());
                list.add(map1);
            }
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * app 搜索筛选
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchRentHouse",method = RequestMethod.POST)
    public DataRes searchRentHouse(HttpServletRequest request,HttpServletResponse response){
        ResultInfo<SolrRentHouse> data=rentHouseSearchService.searchRentHouse(request);
        //完动态信息
        for(SolrRentHouse s:data.getList()){
            Map<String,Object> map=rentHouseSearchService.queryInfo(s.getId());
            s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
            s.setPageView(Integer.parseInt(map.get("page_view").toString()));
            s.setUserName(map.get("nickname").toString());
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
                        userSearch.setType(3);
                        userSearch.setContent(request.getParameter("keyword"));
                        userSearch.setUserId(sessionUser.getUsers().getId());
                        userSearchService.insert(userSearch);
                    }
                }
                //热门搜索redis,暂不分地区
                redisUtil.zAdd("renthouseHotSearch", request.getParameter("keyword"), 1);
            }
        }
        return DataRes.success(data);
    }

    /**
     * 获取租房热搜关键字
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "app/getRentHouseHotSearch",method = RequestMethod.POST)
    public DataRes hotSearch(Integer start,Integer end,HttpServletRequest request){
        Set<Object> set=redisUtil.zreverseRange("renthouseHotSearch",start,end);
        //long set=redisUtil.zAdd("newhouseHotSearch","asdsd1",1);

        Set<String> search=new HashSet<>();
        if(request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            UserSearch userSearch = new UserSearch();
            userSearch.setPage(1);
            userSearch.setPageSize(10);
            userSearch.setType(3);
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
     * app租房详情页面接口
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getRentHouseDetail/{id}",method = RequestMethod.POST)
    public DataRes getRentHouseDetail(@PathVariable Integer id,HttpServletRequest request){
        if(id!=null){
            Map<String,Object> map=rentHouseSearchService.queryDetail(id);
            if(map.get("flag").toString().equals("1")) {
                if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    //是否收藏
                    UserCollect userCollect = new UserCollect();
                    userCollect.setHouseId(id);
                    userCollect.setUserId(Integer.parseInt(userId));
                    userCollect.setCollectType(3);
                    List list1 = userCollectService.selectAll(userCollect);
                    if (list1.size() > 0) {
                        map.put("isCollect", true);
                    } else {
                        map.put("isCollect", false);
                    }
                    PriceNotice priceNotice = new PriceNotice();
                    priceNotice.setHouseId(id);
                    priceNotice.setUserId(Integer.parseInt(userId));
                    priceNotice.setHouseType(3);
                    list1 = priceNoticeService.selectAll(priceNotice);
                    if (list1.size() > 0) {
                        map.put("isNotice", true);
                    } else {
                        map.put("isNotice", false);
                    }
                    String picture = map.get("img_url").toString();
                    double price = Double.parseDouble(map.get("rent_fee").toString());
                    String unit = "元/月";
                    String area = map.get("areaname").toString();
                    String street = map.get("streesname").toString();
                    String title = map.get("title").toString();
                    String content = map.get("covered_area").toString() + "平 " + map.get("village_name").toString();
                    userHistoryService.userHistoryLog(Integer.parseInt(userId), 3, id, picture, price, unit, area, street, title, content);
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
     * 获取租房图片列表
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getRentHouseImg/{id}",method = RequestMethod.POST)
    public DataRes getRentHouseImg(@PathVariable Integer id){
        if(id!=null){
            RentHouseImg rentHouseImg=new RentHouseImg();
            rentHouseImg.setRentHouse(id);
            List<RentHouseImg> list=sysRentHouseImgService.selectAll(rentHouseImg);
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
