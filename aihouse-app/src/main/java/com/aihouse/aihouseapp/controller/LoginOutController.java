package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.HttpClient;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.VillageService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退出登录
 */
@CrossOrigin
@RestController
public class LoginOutController {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private VillageService villageService;


    @Resource
    private AreaService areaService;
    private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";
    private static final String changeLatLon="https://restapi.amap.com/v3/assistant/coordinate/convert";

    @Value("${amap.key}")
    private String ak;
    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/logout",method = RequestMethod.POST)
    public DataRes logout(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        redisUtil.del(RedisConstants.USER_TOKEN+userId);
        return DataRes.success("退出登录成功");
    }


    @RequestMapping(value = "app/addVillage",method = RequestMethod.POST)
    public DataRes addVillage(Integer start,Integer limit){
        List<Map<String,Object>> list=villageService.getSource(start,limit);
        if(list.size()>0){
            Village village=null;
            for(Map map:list){
                village=new Village();
                village.setVillageName(map.get("name").toString());
                if(map.get("latitude")!=null&&map.get("longitude")!=null&&!map.get("latitude").toString().equals("")&&!map.get("longitude").toString().equals("")){
                    String location="locations="+map.get("longitude").toString()+","+map.get("latitude").toString();
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(changeLatLon).append("?").append(location).append("&coordsys=baidu&output=json&key=").append(ak);
                    try {
                        String result= HttpClient.doGet(stringBuilder.toString());
                        Map resil= JSON.parseObject(result,HashMap.class);
                        String map1= resil.get("locations").toString();
                        String[] p=map1.split(",");
                        village.setLng(p[0]);
                        village.setLat(p[1]);

                        StringBuilder str = new StringBuilder();
                        str.append(mapUrl).append("?").append("location=").append(village.getLng()).append(",");
                        str.append(village.getLat()).append("&radius=1000&extensions=all&key=");
                        str.append(ak);
                        result = HttpClient.doGet(str.toString());
                        resil = JSON.parseObject(result, HashMap.class);
                        Map map2 = (Map) ((Map) resil.get("regeocode")).get("addressComponent");
                        Area area = new Area();
                        List<Area> list1 = null;
                        if (map2.get("city") != null && !map2.get("city").toString().equals("[]")) {
                            area.setAreaname(map2.get("city").toString());
                            list1 = areaService.queryByCondition(area);
                            if (list1.size() > 0) {
                                village.setCityid(list1.get(0).getId());
                                village.setProvinceid(list1.get(0).getParentId());
                            }
                        } else {
                            if (map2.get("province").toString().equals("上海市") || map2.get("province").toString().equals("天津市") || map2.get("province").toString().equals("重庆市") || map2.get("province").toString().equals("北京市")) {
                                area.setAreaname(map2.get("province").toString());
                                list1 = areaService.queryByCondition(area);
                                if (list1.size() > 0) {
                                    village.setCityid(list1.get(0).getId());
                                    village.setProvinceid(list1.get(0).getParentId());
                                }
                            } else {
                                area.setAreaname(map2.get("district").toString());
                                list1 = areaService.queryByCondition(area);
                                if (list1.size() > 0) {
                                    village.setCityid(list1.get(0).getId());
                                    village.setProvinceid(list1.get(0).getParentId());
                                }
                            }
                        }
                        area = new Area();
                        area.setAreaname(map2.get("township").toString());
                        area.setPosition("%" + village.getCityid() + "%");
                        list1 = areaService.queryByCondition(area);
                        if (list1.size() > 0) {
                            if (map2.get("city") != null && !map2.get("city").toString().equals("[]")) {
                                village.setStreesid(list1.get(0).getId());
                                village.setAreaid(list1.get(0).getParentId());
                            } else {
                                if (map2.get("province").toString().equals("上海市") || map2.get("province").toString().equals("天津市") || map2.get("province").toString().equals("重庆市") || map2.get("province").toString().equals("北京市")) {
                                    village.setStreesid(list1.get(0).getId());
                                    village.setAreaid(list1.get(0).getParentId());
                                } else {
                                    village.setAreaid(list1.get(0).getId());
                                }
                            }
                            village.setAddress(((Map) resil.get("regeocode")).get("formatted_address").toString());
                            villageService.insert(village);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
