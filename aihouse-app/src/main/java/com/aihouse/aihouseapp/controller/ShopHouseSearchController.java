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
import com.aihouse.aihouseservice.shophouse.ShopHouseImgService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import com.aihouse.aihouseservice.users.UserCollectService;
import com.aihouse.aihouseservice.users.UserHistoryService;
import com.aihouse.aihouseservice.users.UserSearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * app 商铺搜索
 */
@CrossOrigin
@RestController
public class ShopHouseSearchController {

    @Resource
    private ShopHouseSearchService shopHouseSearchService;

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
    private ShopHouseImgService shopHouseImgService;
    /**
     * 搜索筛选
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchShopHouse",method = RequestMethod.POST)
    public DataRes searchShopHouse(HttpServletRequest request, HttpServletResponse response){
        ResultInfo<SolrShopHouse> data=shopHouseSearchService.searchShopHouse(request);
        for(SolrShopHouse s:data.getList()){
            Map<String,Object>map=shopHouseSearchService.queryInfo(s.getId());
            if(map.get("collect_view")!=null) {
                s.setCountCellect(Integer.parseInt(map.get("collect_view").toString()));
            }
            if(map.get("page_view")!=null) {
                s.setPageView(Integer.parseInt(map.get("page_view").toString()));
            }
            if(map.get("picture")!=null) {
                s.setPicture(map.get("picture").toString());
            }
            if(map.get("nickname")!=null){
                s.setUserName(map.get("nickname").toString());
            }
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
                        userSearch.setType(4);
                        userSearch.setContent(request.getParameter("keyword"));
                        userSearch.setUserId(sessionUser.getUsers().getId());
                        userSearchService.insert(userSearch);
                    }
                }
                //热门搜索redis,暂不分地区
                redisUtil.zAdd("shophouseHotSearch", request.getParameter("keyword"), 1);
            }
        }
        return DataRes.success(data);
    }


    /**
     * 获取店铺热搜关键字
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "app/getShopHouseHotSearch",method = RequestMethod.POST)
    public DataRes hotSearch(Integer start,Integer end,HttpServletRequest request){
        Set<Object> set=redisUtil.zreverseRange("shophouseHotSearch",start,end);
        //long set=redisUtil.zAdd("newhouseHotSearch","asdsd1",1);

        Set<String> search=new HashSet<>();
        if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
            UserSearch userSearch = new UserSearch();
            userSearch.setPage(1);
            userSearch.setPageSize(10);
            userSearch.setType(4);
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
     * 关键字补全
     * @param keyword
     * @return
     */
    @RequestMapping(value = "app/shophouse/getKeywordComplet",method = RequestMethod.POST)
    public DataRes getKeywordComplet(String keyword){
        if(keyword!=null &&!keyword.trim().equals("")){
            ResultInfo<SolrShopHouse> data=shopHouseSearchService.getKeywordComplet(keyword);
            List<Map<String,Object>> list=new ArrayList<>();
            for(SolrShopHouse s:data.getList()){
                Map<String,Object>map=shopHouseSearchService.queryInfo(s.getId());
                Map<String,Object> map1=new HashMap<>();
                map1.put("areaname",map.get("areaname").toString());
                map1.put("streesname",map.get("streesname").toString());
                map1.put("name",s.getShopName());
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
    @RequestMapping(value = "app/getShopHouseDetail/{id}",method = RequestMethod.POST)
    public DataRes getShopHouseDetail(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            Map<String,Object> map=shopHouseSearchService.queryDetail(id);
            if (request.getHeader("token") != null && !request.getHeader("token").equals("")) {
                String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                //是否收藏
                UserCollect userCollect = new UserCollect();
                userCollect.setHouseId(id);
                userCollect.setUserId(Integer.parseInt(userId));
                userCollect.setCollectType(4);
                List list1 = userCollectService.selectAll(userCollect);
                if (list1.size() > 0) {
                    map.put("isCollect", true);
                } else {
                    map.put("isCollect", false);
                }
                PriceNotice priceNotice = new PriceNotice();
                priceNotice.setHouseId(id);
                priceNotice.setUserId(Integer.parseInt(userId));
                priceNotice.setHouseType(4);
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
                String content = map.get("covered_area").toString() + "平 " + map.get("shop_name").toString();
                userHistoryService.userHistoryLog(Integer.parseInt(userId), 4, id, picture, price, unit, area, street, title, content);
            }
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取商铺出租的图片列表
     * @param id
     * @return
     */
    @RequestMapping(value = "app/getShopHouseImg/{id}",method = RequestMethod.POST)
    public DataRes getShopHouseImg(@PathVariable("id") Integer id){
        if(id!=null){
            ShopHouseImg shopHouseImg=new ShopHouseImg();
            shopHouseImg.setShopID(id);
            List<ShopHouseImg> list=shopHouseImgService.selectAll(shopHouseImg);
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
