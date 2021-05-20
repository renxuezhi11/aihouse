package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.HttpClient;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.PriceLogService;
import com.aihouse.aihouseservice.VillageService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseImgService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 写字楼发布
 */
@CrossOrigin
@RestController
public class OfficeHousePublishController {

    @Resource
    private VillageService villageService;

    @Resource
    private AreaService areaService;

    @Value("${amap.key}")
    private String ak;
    //private static final String mapUrl="http://api.map.baidu.com/geocoder/v2/";
    private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";

    @Resource
    private OfficeHouseService officeHouseService;

    @Resource
    private OfficeHouseImgService officeHouseImgService;

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;


    @Resource
    private PriceLogService priceLogService;

    @Resource
    private RedisUtil redisUtil;
    /**
     * app 删除写字楼图片
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/officeHouse/deletImg",method = RequestMethod.POST)
    public DataRes deleteImg(@RequestParam("id")Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){
            OfficeHouseImg officeHouseImg=new OfficeHouseImg();
            officeHouseImg.setId(id);
            officeHouseImgService.deleteByPrimaryKey(officeHouseImg);
            return DataRes.success(ResponseCode.DELETE_IMG_SUCCESS);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
    /**
     * 发布写字楼
     * @param officeHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/officeHouse/publish",method = RequestMethod.POST)
    public DataRes publish(OfficeHouse officeHouse, HttpServletRequest request, HttpServletResponse response){
        //如果小区存在
        if(officeHouse.getVillageId()!=null){
            Village village=new Village();
            village.setId(officeHouse.getVillageId());
            village=villageService.selectByPrimaryKey(village);
            officeHouse.setAdress(village.getAddress());
            officeHouse.setCityId(village.getCityid());
            officeHouse.setAreaId(village.getStreesid());
            officeHouse.setDistrictId(village.getAreaid());
            officeHouse.setLatitude(village.getLat());
            officeHouse.setLongitude(village.getLng());
        }else{//不存在 通过经纬度查询 小区城市，县区，街道
            StringBuilder str=new StringBuilder();
            str.append(mapUrl).append("?").append("location=").append(officeHouse.getLongitude()).append(",");
            str.append(officeHouse.getLatitude()).append("&radius=1000&extensions=all&key=");
            str.append(ak);
            String result=HttpClient.doGet(str.toString());
            System.out.println(result);
            Map resil=JSON.parseObject(result,HashMap.class);
            Map map=(Map)((Map) resil.get("regeocode")).get("addressComponent");
            Area area=new Area();
            area.setAreaname(map.get("township").toString());
            area.setPosition("%"+officeHouse.getCityId()+"%");
            List<Area> list=areaService.queryByCondition(area);
            if(list.size()>0){
                officeHouse.setAreaId(list.get(0).getId());
                officeHouse.setDistrictId(list.get(0).getParentId());
            }
        }
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
        officeHouse.setTelephone(sessionUser.getUsers().getTelephone());
        officeHouse.setUserId(Integer.parseInt(userId));
        int id=officeHouseService.insert(officeHouse);
        for(String s:officeHouse.getOfficeHouseImgList()){
            OfficeHouseImg officeHouseImg=new OfficeHouseImg();
            officeHouseImg.setOfficeID(officeHouse.getId());
            officeHouseImg.setImgDesc("");
            officeHouseImg.setImgUrl(s);;
            officeHouseImgService.insert(officeHouseImg);
        }
        return DataRes.success("success");
    }


    /**
     * 获取用户发布的商铺
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserOfficeHouseList",method = RequestMethod.POST)
    public DataRes getUserOfficeHouseList(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        List<Map<String,Object>> list=officeHouseService.getUserShopHouseList(Integer.parseInt(userId));
        return DataRes.success(list);
    }

    /**
     * 修改发布的写字楼
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/editOfficeHouse/{id}",method = RequestMethod.POST)
    public DataRes editSecondHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            //验证是否是用户发的房源
            Map<String,Object> map=officeHouseSearchService.queryDetail(id);
            if(map!=null) {
                if (map.get("user_id").toString().equals(request.getHeader("token").split(SessionUser.FLAG)[1])) {
                    OfficeHouse officeHouse=new OfficeHouse();
                    officeHouse.setId(id);
                    officeHouse=officeHouseService.selectByPrimaryKey(officeHouse);
                    if(officeHouse.getCheckStatus()!=0) {
                        OfficeHouseImg officeHouseImg = new OfficeHouseImg();
                        officeHouseImg.setOfficeID(id);
                        List<OfficeHouseImg> allImg = officeHouseImgService.selectAll(officeHouseImg);
                        List<String> imgList = new ArrayList<>();
                        allImg.forEach(t -> {
                            imgList.add(t.getImgUrl());
                        });
                        map.put("imgList", imgList);
                        return DataRes.success(map);
                    }else{
                        return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                    }
                } else {
                    return DataRes.error(ResponseCode.DATA_ERROR);
                }
            } else {
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 房源上下架
     * @param id
     * @param flag 1上架2下架
     * @param request
     * @return
     */
    @RequestMapping(value = "app/updateFlagOfficeHouse/{id}",method = RequestMethod.POST)
    public DataRes updateFlag(@PathVariable("id") Integer id,Integer flag,HttpServletRequest request){
        if(id!=null&&flag!=null){
            OfficeHouse officeHouse=new OfficeHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            officeHouse.setUserId(Integer.parseInt(userId));
            officeHouse.setId(id);
            List<OfficeHouse> rentHouse1=officeHouseService.selectAll(officeHouse);
            if(rentHouse1.size()>0) {
                if (rentHouse1.get(0).getCheckStatus() == 1) {//房源已审核
                    officeHouse.setFlag(flag);
                    int i = officeHouseService.updateOfficeHouse(officeHouse);
                    if (officeHouse.getFlag() == 1) {//上架
                        officeHouseSearchService.addOfficeHouseIndex(officeHouse.getId());
                    } else if (officeHouse.getFlag() == 2) {//下架
                        officeHouseSearchService.deleteOfficeHouseIndex(officeHouse.getId());
                    }
                    return DataRes.success(i);
                } else if (rentHouse1.get(0).getCheckStatus() == 0) {//房源审核中
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                } else {//房源审核失败
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 删除房源,更新删除的状态
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "app/deleteOfficeHouse/{id}",method = RequestMethod.POST)
    public DataRes deleteOfficeHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            OfficeHouse officeHouse=new OfficeHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            officeHouse.setUserId(Integer.parseInt(userId));
            officeHouse.setId(id);
            List<OfficeHouse> rentHouseList=officeHouseService.selectAll(officeHouse);
            if(rentHouseList.size()>0){
                if(rentHouseList.get(0).getCheckStatus()==0){
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                }else{
                    if(rentHouseList.get(0).getFlag()==1){
                        return DataRes.error(ResponseCode.HOUSE_GROUNDING_ERROR);
                    }else{
                        officeHouse.setIsDel(1);
                        int i=officeHouseService.updateOfficeHouse(officeHouse);
                        return DataRes.success("删除成功");
                    }
                }

            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
    /**
     * 修改
     * @param request
     * @param houseDesc
     * @param imgList
     * @param id
     * @param price
     * @return
     */
    @RequestMapping(value = "app/modifyOfficeHouse/{id}",method = RequestMethod.POST)
    public DataRes modifyOfficeHouse(@PathVariable("id") Integer id,String[] imgList,String houseDesc,Double price,HttpServletRequest request){
        if(id!=null){
            int flag=0;
            int flag1=0;
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            OfficeHouse officeHouse=new OfficeHouse();
            officeHouse.setId(id);
            officeHouse=officeHouseService.selectByPrimaryKey(officeHouse);
            Double oldPrice=officeHouse.getMonthlyRent();
            Integer flagS=officeHouse.getFlag();
            if(officeHouse.getUserId()==Integer.parseInt(userId)) {
                officeHouse=new OfficeHouse();
                officeHouse.setFlag(flagS);
                officeHouse.setId(id);
                if (houseDesc != null) {
                    officeHouse.setDescription(houseDesc);
                    officeHouse.setCheckStatus(0);
                    officeHouse.setFlag(0);
                    flag1=1;
                }
                if (price != null) {
                    officeHouse.setMonthlyRent(price);
                    flag = 1;
                }
                if (imgList != null && imgList.length > 0) {//先删除所有在插入
                    flag1=1;
                    officeHouse.setCheckStatus(0);
                    officeHouse.setFlag(0);
                    officeHouseImgService.deleteAllByHouseId(id);
                    for(String s:imgList){
                        OfficeHouseImg officeHouseImg=new OfficeHouseImg();
                        officeHouseImg.setOfficeID(officeHouse.getId());
                        officeHouseImg.setImgUrl(s);
                        officeHouseImgService.insert(officeHouseImg);
                    }
                }
                officeHouseService.updateOfficeHouse(officeHouse);
                if(flag1==1){
                    officeHouseSearchService.deleteOfficeHouseIndex(id);
                }
                if(flag==1){//价格变动
                    if(officeHouse.getFlag()==1&&flag1!=1){
                        officeHouseSearchService.addOfficeHouseIndex(id);
                    }
                    //添加价格变动记录
                    priceLogService.insert(id,5,oldPrice.doubleValue(),price.doubleValue(),"");
                }
                return DataRes.success("修改成功");
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
