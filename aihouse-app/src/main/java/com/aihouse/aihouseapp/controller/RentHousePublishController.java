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
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseImgService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseService;
import com.alibaba.fastjson.JSON;
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
 * app租房发布
 */
@CrossOrigin
@RestController
public class RentHousePublishController {

    @Resource
    private SysRentHouseService sysRentHouseService;

    @Resource
    private SysRentHouseImgService sysRentHouseImgService;


    @Resource
    private VillageService villageService;

    @Resource
    private AreaService areaService;

    @Resource
    private RentHouseSearchService rentHouseSearchService;

    @Resource
    private PriceLogService priceLogService;

    @Resource
    private RedisUtil redisUtil;

    @Value("${amap.key}")
    private String ak;
//    private static final String mapUrl="http://api.map.baidu.com/geocoder/v2/";
    private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";
    /**
     * app 删除租房图片
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/rentHouse/deletImg")
    public DataRes deleteImg(@RequestParam("id")Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){
            RentHouseImg rentHouseImg=new RentHouseImg();
            rentHouseImg.setId(id);
            sysRentHouseImgService.deleteByPrimaryKey(rentHouseImg);
            return DataRes.success(ResponseCode.DELETE_IMG_SUCCESS);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 发布租房
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/rentHouse/publish")
    public DataRes publish(RentHouse rentHouse, HttpServletRequest request, HttpServletResponse response){
        //如果小区存在
        if(rentHouse.getVillageId()!=null){
            Village village=new Village();
            village.setId(rentHouse.getVillageId());
            village=villageService.selectByPrimaryKey(village);
            rentHouse.setAddress(village.getAddress());
            rentHouse.setCityId(village.getCityid());
            rentHouse.setAreaId(village.getAreaid());
            rentHouse.setStreetsId(village.getStreesid());
            rentHouse.setLat(village.getLat());
            rentHouse.setLng(village.getLng());
        }else{
            //不存在 通过经纬度查询 小区城市，县区，街道
            StringBuilder str=new StringBuilder();
            str.append(mapUrl).append("?").append("location=").append(rentHouse.getLng()).append(",");
            str.append(rentHouse.getLat()).append("&radius=1000&extensions=all&key=");
            str.append(ak);
            String result=HttpClient.doGet(str.toString());
            System.out.println(result);
            Map resil=JSON.parseObject(result,HashMap.class);
            Map map=(Map)((Map) resil.get("regeocode")).get("addressComponent");
            Area area=new Area();
            area.setAreaname(map.get("township").toString());
            area.setPosition("%"+rentHouse.getCityId()+"%");
            List<Area> list=areaService.queryByCondition(area);
            if(list.size()>0){
                rentHouse.setStreetsId(list.get(0).getId());
                rentHouse.setAreaId(list.get(0).getParentId());
            }
        }
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
        rentHouse.setTelephone(sessionUser.getUsers().getTelephone());
        rentHouse.setUserId(Integer.parseInt(userId));
        int id=sysRentHouseService.insert(rentHouse);
        for(String s:rentHouse.getRentHouseImgList()){
            RentHouseImg rentHouseImg=new RentHouseImg();
            rentHouseImg.setRentHouse(rentHouse.getId());
            rentHouseImg.setImgUrl(s);
            sysRentHouseImgService.insert(rentHouseImg);
        }
        return DataRes.success("success");
    }

    /**
     * 获取用户发布的租房列表
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserRentHouseList",method = RequestMethod.POST)
    public DataRes getUserRentHouseList(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        List<Map<String,Object>> map=sysRentHouseService.getUserRentHouseList(Integer.parseInt(userId));
        return DataRes.success(map);
    }

    /**
     * 修改发布的租房
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/editRentHouse/{id}",method = RequestMethod.POST)
    public DataRes editSecondHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            //验证是否是用户发的房源
            Map<String,Object> map=rentHouseSearchService.queryDetail(id);
            if(map!=null) {
                if (map.get("user_id").toString().equals(request.getHeader("token").split(SessionUser.FLAG)[1])) {
                    RentHouse rentHouse=new RentHouse();
                    rentHouse.setId(id);
                    rentHouse=sysRentHouseService.selectByPrimaryKey(rentHouse);
                    if(rentHouse.getStatus()!=0) {
                        RentHouseImg rentHouseImg = new RentHouseImg();
                        rentHouseImg.setRentHouse(id);
                        List<RentHouseImg> allImg = sysRentHouseImgService.selectAll(rentHouseImg);
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
            }else{
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
    @RequestMapping(value = "app/updateFlagRentHouse/{id}",method = RequestMethod.POST)
    public DataRes updateFlag(@PathVariable("id") Integer id,Integer flag,HttpServletRequest request){
        if(id!=null&&flag!=null){
            RentHouse rentHouse=new RentHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            rentHouse.setUserId(Integer.parseInt(userId));
            rentHouse.setId(id);
            List<RentHouse> rentHouse1=sysRentHouseService.selectAll(rentHouse);
            if(rentHouse1.size()>0) {
                if (rentHouse1.get(0).getStatus() == 1) {//房源已审核
                    rentHouse.setFlag(flag);
                    int i =  rentHouseSearchService.updateRentHouse(rentHouse);
                    if (rentHouse.getFlag() == 1) {//上架
                        rentHouseSearchService.addRentHouseIndex(rentHouse.getId());
                    } else if (rentHouse.getFlag() == 2) {//下架
                        rentHouseSearchService.deleteRentHouseIndex(rentHouse.getId());
                    }
                    return DataRes.success(i);
                } else if (rentHouse1.get(0).getStatus() == 0) {//房源审核中
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
    @RequestMapping(value = "app/deleteRentHouse/{id}",method = RequestMethod.POST)
    public DataRes deleteRentHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            RentHouse rentHouse=new RentHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            rentHouse.setUserId(Integer.parseInt(userId));
            rentHouse.setId(id);
            List<RentHouse> rentHouseList=sysRentHouseService.selectAll(rentHouse);
            if(rentHouseList.size()>0){
                if(rentHouseList.get(0).getStatus()==0){
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                }else{
                    if(rentHouseList.get(0).getFlag()==1){
                        return DataRes.error(ResponseCode.HOUSE_GROUNDING_ERROR);
                    }else{
                        rentHouse.setIsDel(1);
                        int i= rentHouseSearchService.updateRentHouse(rentHouse);
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
    @RequestMapping(value = "app/modifyRentHouse/{id}",method = RequestMethod.POST)
    public DataRes modifyRentHouse(@PathVariable("id") Integer id,String[] imgList,String houseDesc,Integer price,HttpServletRequest request){
        if(id!=null){
            int flag=0;
            int flag1=0;
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            RentHouse rentHouse=new RentHouse();
            rentHouse.setId(id);
            rentHouse=sysRentHouseService.selectByPrimaryKey(rentHouse);
            Integer oldPrice=rentHouse.getRentFee();
            Integer flagS=rentHouse.getFlag();
            if(rentHouse.getUserId()==Integer.parseInt(userId)) {
                rentHouse=new RentHouse();
                rentHouse.setFlag(flagS);
                rentHouse.setId(id);
                if (houseDesc != null) {
                    rentHouse.setHouseDesc(houseDesc);
                    rentHouse.setStatus(0);
                    rentHouse.setFlag(0);
                    flag1=1;
                }
                if (price != null) {
                    rentHouse.setRentFee(price);
                    flag = 1;
                }
                if (imgList != null && imgList.length > 0) {//先删除所有在插入
                    flag1=1;
                    rentHouse.setStatus(0);
                    rentHouse.setFlag(0);
                    sysRentHouseImgService.deleteAllByHouseId(id);
                    for(String s:imgList){
                        RentHouseImg rentHouseImg=new RentHouseImg();
                        rentHouseImg.setRentHouse(rentHouse.getId());
                        rentHouseImg.setImgUrl(s);
                        sysRentHouseImgService.insert(rentHouseImg);
                    }
                }
                rentHouseSearchService.updateRentHouse(rentHouse);
                if(flag1==1){
                    rentHouseSearchService.deleteRentHouseIndex(id);
                }
                if(flag==1){//价格变动
                    if(rentHouse.getFlag()==1&&flag1!=1){
                        rentHouseSearchService.addRentHouseIndex(id);
                    }
                    //添加价格变动记录
                    priceLogService.insert(id,3,oldPrice.doubleValue(),price.doubleValue(),"");
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
