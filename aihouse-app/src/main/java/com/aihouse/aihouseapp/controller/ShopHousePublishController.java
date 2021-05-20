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
import com.aihouse.aihouseservice.shophouse.ShopHouseImgService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商铺发布
 */
@CrossOrigin
@RestController
public class ShopHousePublishController {

    @Resource
    private VillageService villageService;

    @Resource
    private AreaService areaService;

    @Value("${amap.key}")
    private String ak;
//    private static final String mapUrl="http://api.map.baidu.com/geocoder/v2/";
    private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";
    @Resource
    private ShopHouseService shopHouseService;

    @Resource
    private ShopHouseImgService shopHouseImgService;

    @Resource
    private ShopHouseSearchService shopHouseSearchService;

    @Resource
    private PriceLogService priceLogService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * app 删除商铺图片
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/shopHouse/deletImg")
    public DataRes deleteImg(@RequestParam("id")Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){
            ShopHouseImg shopHouseImg=new ShopHouseImg();
            shopHouseImg.setId(id);
            shopHouseImgService.deleteByPrimaryKey(shopHouseImg);
            return DataRes.success(ResponseCode.DELETE_IMG_SUCCESS);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 发布商铺
     * @param shopHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/shopHouse/publish")
    public DataRes publish(ShopHouse shopHouse, HttpServletRequest request, HttpServletResponse response){
        //如果小区存在
        if(shopHouse.getVillageId()!=null){
            Village village=new Village();
            village.setId(shopHouse.getVillageId());
            village=villageService.selectByPrimaryKey(village);
            shopHouse.setAdress(village.getAddress());
            shopHouse.setCityId(village.getCityid());
            shopHouse.setAreaId(village.getStreesid());
            shopHouse.setDistrictId(village.getAreaid());
            shopHouse.setLatitude(village.getLat());
            shopHouse.setLatitude(village.getLng());
        }else{//不存在 通过经纬度查询 小区城市，县区，街道
            StringBuilder str=new StringBuilder();
            str.append(mapUrl).append("?").append("location=").append(shopHouse.getLongitude()).append(",");
            str.append(shopHouse.getLatitude()).append("&radius=1000&extensions=all&key=");
            str.append(ak);
            String result=HttpClient.doGet(str.toString());
            System.out.println(result);
            Map resil=JSON.parseObject(result,HashMap.class);
            Map map=(Map)((Map) resil.get("regeocode")).get("addressComponent");
            Area area=new Area();
            area.setAreaname(map.get("township").toString());
            area.setPosition("%"+shopHouse.getCityId()+"%");
            List<Area> list=areaService.queryByCondition(area);
            if(list.size()>0){
                shopHouse.setAreaId(list.get(0).getId());
                shopHouse.setDistrictId(list.get(0).getParentId());
            }
        }
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
        shopHouse.setTelephone(sessionUser.getUsers().getTelephone());
        shopHouse.setCreateBy(Integer.parseInt(userId));
        int id=shopHouseService.insert(shopHouse);
        for(String s:shopHouse.getShopHouseImgList()){
            ShopHouseImg shopHouseImg=new ShopHouseImg();
            shopHouseImg.setShopID(shopHouse.getId());
            shopHouseImg.setImgUrl(s);
            shopHouseImgService.insert(shopHouseImg);
        }
        return DataRes.success("success");
    }

    /**
     * 获取用户发布的商铺
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserShopHouseList",method = RequestMethod.POST)
    public DataRes getUserShopHouseList(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        List<Map<String,Object>> list=shopHouseService.getUserShopHouseList(Integer.parseInt(userId));
        return DataRes.success(list);
    }

    /**
     * 修改用户发布的房源
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/editShopHouse/{id}",method = RequestMethod.POST)
    public DataRes editShopHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            //验证是否是用户发的房源
            Map<String,Object> map=shopHouseSearchService.queryDetail(id);
            if(map!=null) {
                if (map.get("create_by").toString().equals(request.getHeader("token").split(SessionUser.FLAG)[1])) {
                    ShopHouse shopHouse=new ShopHouse();
                    shopHouse.setId(id);
                    shopHouse=shopHouseService.selectByPrimaryKey(shopHouse);
                    if(shopHouse.getCheckStatus()!=0) {
                        ShopHouseImg shopHouseImg = new ShopHouseImg();
                        shopHouseImg.setShopID(id);
                        List<ShopHouseImg> list = shopHouseImgService.selectAll(shopHouseImg);
                        List<String> imgList = new ArrayList<>();
                        list.forEach(t -> {
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
    @RequestMapping(value = "app/updateFlagShopHouse/{id}",method = RequestMethod.POST)
    public DataRes updateFlag(@PathVariable("id") Integer id,Integer flag,HttpServletRequest request){
        if(id!=null&&flag!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            ShopHouse shopHouse=new ShopHouse();
            shopHouse.setId(id);
            List<ShopHouse> shopHouseList=shopHouseService.selectAll(shopHouse);
            if(shopHouseList.size()>0){
                if(shopHouseList.get(0).getCheckStatus()==1){
                    shopHouse.setFlag(flag);
                    int i = shopHouseService.updateShopHouse(shopHouse);
                    if (shopHouse.getFlag() == 1) {//上架
                        shopHouseSearchService.addShopHouseIndex(shopHouse.getId());
                    } else if (shopHouse.getFlag() == 2) {//下架
                        shopHouseSearchService.deleteShopHouseIndex(shopHouse.getId());
                    }
                    return DataRes.success(i);
                }else if(shopHouseList.get(0).getCheckStatus()==1){
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                }else{
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
    @RequestMapping(value = "app/deleteShopHouse/{id}",method = RequestMethod.POST)
    public DataRes deleteShopHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            ShopHouse shopHouse=new ShopHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            shopHouse.setId(id);
            shopHouse.setCreateBy(Integer.parseInt(userId));
            List<ShopHouse> shopHouseList=shopHouseService.selectAll(shopHouse);
            if(shopHouseList.size()>0){
                if(shopHouseList.get(0).getCheckStatus()==0){
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                }else{
                    if(shopHouseList.get(0).getFlag()==1){
                        return DataRes.error(ResponseCode.HOUSE_GROUNDING_ERROR);
                    }else{
                        shopHouse.setIsDel(1);
                        int i=shopHouseService.updateShopHouse(shopHouse);
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
    @RequestMapping(value = "app/modifyShopHouse/{id}",method = RequestMethod.POST)
    public DataRes modifyShopHouse(@PathVariable("id") Integer id,String[] imgList,String houseDesc,Double price,HttpServletRequest request){
        if(id!=null){
            int flag=0;
            int flag1=1;
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            ShopHouse shopHouse=new ShopHouse();
            shopHouse.setId(id);
            shopHouse=shopHouseService.selectByPrimaryKey(shopHouse);
            Double oldPrice=shopHouse.getMonthlyRent().doubleValue();
            Integer flagS=shopHouse.getFlag();
            if(shopHouse.getCreateBy()==Integer.parseInt(userId)) {
                shopHouse=new ShopHouse();
                shopHouse.setFlag(flagS);
                shopHouse.setId(id);
                if (houseDesc != null) {
                    shopHouse.setDescription(houseDesc);
                    shopHouse.setCheckStatus(0);
                    shopHouse.setFlag(0);
                    flag1=1;
                }
                if (price != null) {
                    shopHouse.setMonthlyRent(price);
                    flag = 1;
                }
                if (imgList != null && imgList.length > 0) {//先删除所有在插入
                    flag1=1;
                    shopHouse.setCheckStatus(0);
                    shopHouse.setFlag(0);
                    shopHouseImgService.deleteAllByHouseId(id);
                    for(String s:imgList){
                        ShopHouseImg shopHouseImg=new ShopHouseImg();
                        shopHouseImg.setShopID(shopHouse.getId());
                        shopHouseImg.setImgUrl(s);
                        shopHouseImgService.insert(shopHouseImg);
                    }
                }
                shopHouseService.updateShopHouse(shopHouse);
                if(flag1==1){
                    shopHouseSearchService.deleteShopHouseIndex(id);
                }
                if(flag==1){//价格变动
                    if(shopHouse.getFlag()==1&&flag1!=1){
                        shopHouseSearchService.addShopHouseIndex(id);
                    }
                    //添加价格变动记录
                    priceLogService.insert(id,4,oldPrice,price,"");
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
