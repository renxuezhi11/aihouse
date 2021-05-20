package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserCollect;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import com.aihouse.aihouseservice.users.UserCollectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户收藏controller
 */
@CrossOrigin
@RestController
public class UserCollectController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserCollectService userCollectService;

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private SecondHouseSearchService secondHouseSearchService;

    @Resource
    private RentHouseSearchService rentHouseSearchService;

    @Resource
    private ShopHouseSearchService shopHouseSearchService;

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;
    /**
     * 用户收藏房源
     * @param id
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "app/userCollectHouse",method = RequestMethod.POST)
    public DataRes collectHouse(Integer id, Integer type, HttpServletRequest request){
        if(id!=null&&type!=null) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
            UserCollect userCollect = new UserCollect();
            userCollect.setUserId(sessionUser.getUsers().getId());
            userCollect.setCollectType(type);
            userCollect.setHouseId(id);
            List<UserCollect> list = userCollectService.selectAll(userCollect);
            if (list.size() > 0) {//用户已收藏
                return DataRes.success("已收藏");
            } else {//用户未收藏
                userCollectService.insert(userCollect);
                return DataRes.success("收藏成功");
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 用户删除收藏
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/cancleCollectHouse",method = RequestMethod.POST)
    public DataRes cancleCollectHouse(Integer id,Integer type,HttpServletRequest request){
        if(id!=null) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
            UserCollect userCollect = new UserCollect();
            userCollect.setHouseId(id);
            userCollect.setUserId(Integer.parseInt(userId));
            userCollect.setCollectType(type);
            List<UserCollect> list = userCollectService.selectAll(userCollect);
            if (list.size() > 0) {
                userCollectService.deleteByPrimaryKey(list.get(0));
            }
            return DataRes.success("删除收藏成功");
        }else {
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 用户删除收藏
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "app/cancleCollectHouses",method = RequestMethod.POST)
    public DataRes cancleCollectHouses(Integer[] ids,HttpServletRequest request){
        if(ids!=null) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            userCollectService.deleteUserCollect(ids,Integer.parseInt(userId));
            return DataRes.success("删除收藏成功");
        }else {
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取用户的收藏列表
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserCollectList",method = RequestMethod.POST)
    public DataRes getUserCollectList(HttpServletRequest request){
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
            System.out.println(sessionUser.getToken());
            UserCollect userCollect=new UserCollect();
            userCollect.setUserId(sessionUser.getUsers().getId());
            userCollect.setOrderByString(" order by createtime desc ");
            List<UserCollect> list=userCollectService.selectAll(userCollect);
            List<UserCollect> listResult=new ArrayList<>();
            for(UserCollect s:list){
                Map<String,Object> result=new HashMap<>();
                if(s.getCollectType()==1){
                   Map<String,Object> map= newHouseSearchService.queryDetail(s.getHouseId());
                   if(map!=null) {
                       result.put("house_title", map.get("name"));
                       result.put("house_picture", map.get("img_url"));
                       result.put("house_price", map.get("average_price"));
                       result.put("house_unit", "元/平");
                       result.put("house_area", map.get("areaname"));
                       result.put("house_street", map.get("streesname"));
                       result.put("house_content", "");
                       result.put("house_flag",map.get("flag"));
                       s.setMap(result);
                       listResult.add(s);
                   }
                }else if(s.getCollectType()==2){
                    Map<String,Object> map= secondHouseSearchService.queryDetail(s.getHouseId());
                    if(map!=null) {
                        result.put("house_title", map.get("title"));
                        result.put("house_picture", map.get("img_url"));
                        result.put("house_price", map.get("price"));
                        result.put("house_unit", "万");
                        result.put("house_area", map.get("areaname"));
                        result.put("house_street", map.get("streesname"));
                        result.put("house_content", map.get("hall").toString() + "室" + map.get("hall").toString() + "厅 " + map.get("covered_area").toString() + "平 " + map.get("village_name").toString());
                        result.put("house_flag",map.get("flag"));
                        s.setMap(result);
                        listResult.add(s);
                    }
                }else if(s.getCollectType()==3){
                    Map<String,Object> map= rentHouseSearchService.queryDetail(s.getHouseId());
                    if(map!=null) {
                        result.put("house_title", map.get("title"));
                        result.put("house_picture", map.get("img_url"));
                        result.put("house_price", map.get("rent_fee"));
                        result.put("house_unit", "元/月");
                        result.put("house_area", map.get("areaname"));
                        result.put("house_street", map.get("streesname"));
                        result.put("house_content", map.get("covered_area").toString() + "平 " + map.get("village_name").toString());
                        result.put("house_flag",map.get("flag"));
                        s.setMap(result);
                        listResult.add(s);
                    }
                }else if(s.getCollectType()==4){
                    Map<String,Object> map= shopHouseSearchService.queryDetail(s.getHouseId());
                    if(map!=null) {
                        result.put("house_title", map.get("title"));
                        result.put("house_picture", map.get("img_url"));
                        result.put("house_price", map.get("monthly_rent"));
                        result.put("house_unit", "元/月");
                        result.put("house_area", map.get("areaname"));
                        result.put("house_street", map.get("streesname"));
                        result.put("house_content", map.get("covered_area").toString() + "平 " + map.get("shop_name").toString());
                        result.put("house_flag",map.get("flag"));
                        s.setMap(result);
                        listResult.add(s);
                    }
                }else if(s.getCollectType()==5){
                    Map<String,Object> map= officeHouseSearchService.queryDetail(s.getHouseId());
                    if(map!=null) {
                        result.put("house_title", map.get("title"));
                        result.put("house_picture", map.get("img_url"));
                        result.put("house_price", map.get("monthly_rent"));
                        result.put("house_unit", "元/月");
                        result.put("house_area", map.get("areaname"));
                        result.put("house_street", map.get("streesname"));
                        result.put("house_content", map.get("covered_area").toString() + "平 " + map.get("name").toString());
                        result.put("house_flag",map.get("flag"));
                        s.setMap(result);
                        listResult.add(s);
                    }
                }
            }
            return DataRes.success(listResult);
    }
}
