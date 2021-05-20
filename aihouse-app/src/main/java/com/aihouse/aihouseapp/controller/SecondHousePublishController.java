package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.*;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;
import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.PriceLogService;
import com.aihouse.aihouseservice.VillageService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 二手房发布
 */
@CrossOrigin
@RestController
public class SecondHousePublishController {

    @Value("${auto.code.filePath}")
    private String filePath;

    @Value("${amap.key}")
    private String ak;

    //private static final String mapUrl="http://api.map.baidu.com/geocoder/v2/";
    private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";
    @Resource
    private SecondHandHouseImgService secondHandHouseImgService;

    @Resource
    private SecondHandHouseService secondHandHouseService;

    @Resource
    private VillageService villageService;

    @Resource
    private AreaService areaService;

    @Resource
    private SecondHouseSearchService secondHouseSearchService;

    @Resource
    private PriceLogService priceLogService;

    @Resource
    private RedisUtil redisUtil;
    /**
     *上传图片--单图片上传
     * @param request
     * @param response
     * @return 文件保存服务器路径
     */
    @RequestMapping(value = "app/uploadImg",method = RequestMethod.POST)
    public DataRes uploadImg(@RequestParam("file") MultipartFile uploadfile,HttpServletRequest request, HttpServletResponse response){
        //System.out.println(uploadfile.getOriginalFilename());
        String filename = UUID.randomUUID().toString().replaceAll("-", "")+"."+uploadfile.getOriginalFilename().split("\\.")[1];
        // 获得输入流：
        String fileDir=DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/");
        String newFileName=DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename;
        try{
            InputStream input = uploadfile.getInputStream();
            File p = new File(filePath + fileDir);
            if (!p.exists()) {
                p.mkdirs();
            }
            File file = new File(p, newFileName);
            uploadfile.transferTo(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DataRes.success(fileDir+ newFileName);
    }

    /**
     * 删除二手房图片
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/secondHouse/deletImg")
    public DataRes deleteImg(@RequestParam("id")Long id,HttpServletRequest request,HttpServletResponse response){
        if(id!=null){
            SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
            secondHandHouseImg.setId(id);
            secondHandHouseImgService.deleteByPrimaryKey(secondHandHouseImg);
            return DataRes.success(ResponseCode.DELETE_IMG_SUCCESS);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 发布二手房
     * @param secondHandHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/secondHouse/publish")
    public DataRes publish(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
        //如果小区存在
        if(secondHandHouse.getVillageId()!=null){
            //secondHandHouse.getVillage().setId(secondHandHouse.getVillageId());
            Village village=new Village();
            village.setId(secondHandHouse.getVillageId());
            village=villageService.selectByPrimaryKey(village);
            secondHandHouse.setAddress(village.getAddress());
            secondHandHouse.setCityid(village.getCityid());
            secondHandHouse.setAreaid(village.getAreaid());
            secondHandHouse.setStreesid(village.getStreesid());
            secondHandHouse.setLat(village.getLat());
            secondHandHouse.setLng(village.getLng());
        }else{//不存在 通过经纬度查询 小区城市，县区，街道
            StringBuilder str=new StringBuilder();
            str.append(mapUrl).append("?").append("location=").append(secondHandHouse.getLng()).append(",");
            str.append(secondHandHouse.getLat()).append("&radius=1000&extensions=all&key=");
            str.append(ak);
            String result=HttpClient.doGet(str.toString());
            System.out.println(result);
            Map resil=JSON.parseObject(result,HashMap.class);
            Map map=(Map)((Map) resil.get("regeocode")).get("addressComponent");
            Area area=new Area();
            area.setAreaname(map.get("township").toString());
            area.setPosition("%"+secondHandHouse.getCityid()+"%");
            List<Area> list=areaService.queryByCondition(area);
            if(list.size()>0){
                secondHandHouse.setStreesid(list.get(0).getId());
                secondHandHouse.setAreaid(list.get(0).getParentId());
            }
        }
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
        secondHandHouse.setTelephone(sessionUser.getUsers().getTelephone());
        secondHandHouse.setUserId(Integer.parseInt(userId));
        secondHandHouse.setStatus(0);
        int id=secondHandHouseService.insert(secondHandHouse);
        for(String s:secondHandHouse.getSecondHandHouseImgList()){
            SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
            secondHandHouseImg.setSecondHouse(secondHandHouse.getId());
            secondHandHouseImg.setImgType(1);
            secondHandHouseImg.setImgUrl(s);
            secondHandHouseImgService.insert(secondHandHouseImg);
        }
        for(String s:secondHandHouse.getSecondHandHouseAgreementList()){
            SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
            secondHandHouseImg.setSecondHouse(secondHandHouse.getId());
            secondHandHouseImg.setImgType(5);
            secondHandHouseImg.setImgUrl(s);
            secondHandHouseImgService.insert(secondHandHouseImg);
        }
        return DataRes.success("success");
    }


    /**
     * 获取用户发布的二手房
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserSecondHouseList",method = RequestMethod.POST)
    public DataRes geUserSecondHouseList(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        List<Map<String,Object>> map=secondHandHouseService.geUserSecondHouseList(Integer.parseInt(userId));
        return DataRes.success(map);
    }

    /**
     * 修改发布的二手房
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/editSecondHouse/{id}",method = RequestMethod.POST)
    public DataRes editSecondHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            //验证是否是用户发的房源
            Map<String,Object> map=secondHouseSearchService.queryDetail(id);
            if(map!=null) {
                if (map.get("user_id").toString().equals(request.getHeader("token").split(SessionUser.FLAG)[1])) {
                        SecondHandHouse secondHandHouse=new SecondHandHouse();
                        secondHandHouse.setId(id);
                        secondHandHouse=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
                        if(secondHandHouse.getStatus()!=0) {
                            SecondHandHouseImg secondHandHouseImg = new SecondHandHouseImg();
                            secondHandHouseImg.setImgType(1);
                            secondHandHouseImg.setSecondHouse(id);
                            List<SecondHandHouseImg> allImg = secondHandHouseImgService.selectAll(secondHandHouseImg);
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
    @RequestMapping(value = "app/updateFlagSecondHouse/{id}",method = RequestMethod.POST)
    public DataRes updateFlag(@PathVariable("id") Integer id,Integer flag,HttpServletRequest request){
        if(id!=null&&flag!=null){
            SecondHandHouse secondHandHouse=new SecondHandHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            secondHandHouse.setUserId(Integer.parseInt(userId));
            secondHandHouse.setId(id);
            secondHandHouse.setFlag(flag);
            SecondHandHouse secondHandHouse1=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
            if(secondHandHouse1.getStatus()==1) {//房源审核通过
                int i = secondHandHouseService.updateSecondHouse(secondHandHouse);
                if (secondHandHouse.getFlag() == 1) {//上架
                    secondHouseSearchService.addSecondHouseIndex(secondHandHouse.getId());
                } else if (secondHandHouse.getFlag() == 2) {//下架
                    secondHouseSearchService.deleteSecondHouseIndex(secondHandHouse.getId());
                }
                return DataRes.success(i);
            }else if(secondHandHouse1.getStatus()==0){//房源审核中
                return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
            }else{//房源审核失败
                return DataRes.error(ResponseCode.HOUSE_CHECK_ERROR);
            }

        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 删除房源
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "app/deleteSecondHouse/{id}",method = RequestMethod.POST)
    public DataRes deleteSecondHouse(@PathVariable("id") Integer id,HttpServletRequest request){
        if(id!=null){
            SecondHandHouse secondHandHouse=new SecondHandHouse();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            secondHandHouse.setUserId(Integer.parseInt(userId));
            secondHandHouse.setId(id);
            List<SecondHandHouse> secondHandHouseList=secondHandHouseService.selectAll(secondHandHouse);
            if(secondHandHouseList.size()>0){
                if(secondHandHouseList.get(0).getStatus()==0) {//审核中
                    return DataRes.error(ResponseCode.HOUSE_CHECK_ING);
                }else{
                    if(secondHandHouseList.get(0).getFlag()!=1) {
                        secondHandHouse.setIsDel(1);
                        int i = secondHandHouseService.updateSecondHouse(secondHandHouse);
                        return DataRes.success("删除成功");
                    }else{
                        return DataRes.error(ResponseCode.HOUSE_GROUNDING_ERROR);
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
    @RequestMapping(value = "app/modifySecondHouse/{id}",method = RequestMethod.POST)
    public DataRes modifySecondHouse(@PathVariable("id") Integer id,String[] imgList,String houseDesc,Double price,HttpServletRequest request){
        if(id!=null){
            int flag=0;
            int flag1=0;
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            SecondHandHouse secondHandHouse=new SecondHandHouse();
            secondHandHouse.setId(id);
            secondHandHouse=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
            Double oldPrice=secondHandHouse.getPrice().doubleValue();
            Integer flagS=secondHandHouse.getFlag();
            if(secondHandHouse.getUserId()==Integer.parseInt(userId)) {
                secondHandHouse=new SecondHandHouse();
                secondHandHouse.setFlag(flagS);
                secondHandHouse.setId(id);
                if (houseDesc != null) {
                    secondHandHouse.setHouseDesc(houseDesc);
                    secondHandHouse.setStatus(0);
                    secondHandHouse.setFlag(0);
                    flag1=1;
                }
                if (price != null) {
                    secondHandHouse.setPrice(new BigDecimal(price));
                    flag = 1;
                }
                if (imgList != null && imgList.length> 0) {//先删除所有在插入
                    flag1=1;
                    secondHandHouse.setStatus(0);
                    secondHandHouse.setFlag(0);
                    secondHandHouseImgService.deleteAllByHouseId(id);
                    for(String s:imgList){
                        SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
                        secondHandHouseImg.setSecondHouse(secondHandHouse.getId());
                        secondHandHouseImg.setImgType(1);
                        secondHandHouseImg.setImgUrl(s);
                        secondHandHouseImgService.insert(secondHandHouseImg);
                    }
                }
                secondHandHouseService.updateSecondHouse(secondHandHouse);
                if(flag1==1){
                    secondHouseSearchService.deleteSecondHouseIndex(id);
                }
                if(flag==1){//价格变动
                    if(secondHandHouse.getFlag()==1&&flag1!=1){
                        secondHouseSearchService.addSecondHouseIndex(id);
                    }
                    //添加价格变动记录
                    priceLogService.insert(id,2,oldPrice,price,"");
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
