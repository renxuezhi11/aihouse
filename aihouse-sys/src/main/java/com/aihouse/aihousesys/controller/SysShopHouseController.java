package com.aihouse.aihousesys.controller;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.RentHouseImg;
import com.aihouse.aihousedao.bean.ShopHouse;
import com.aihouse.aihousedao.bean.ShopHouseImg;
import com.aihouse.aihouseservice.shophouse.ShopHouseImgService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商铺出租系统管理
 */
@Controller
public class SysShopHouseController {

    @Resource
    private ShopHouseService shopHouseService;

    @Resource
    private ShopHouseImgService shopHouseImgService;

    @Resource
    private ShopHouseSearchService shopHouseSearchService;

    /**
     * 跳转到商铺出租房源页面
     * @return
     */
    @RequestMapping("shophouse/gotoList")
    @RequiresPermissions("shophouse/gotoList")
    public String gotoList(){
        return "sys/sys_shop_house_list";
    }


    /**
     * 分页查询商铺房源
     * @param shopHouse
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("shophouse/selectAll")
    @ResponseBody
    public DataRes selectList(ShopHouse shopHouse,HttpServletResponse response,HttpServletRequest request){
        shopHouse.setOrderByString(" order by create_time desc");
        return DataRes.success(shopHouseService.selectAllByPaging(shopHouse));
    }

    /**
     * 房源上下架
     * @param shopHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("shophouse/updateFlag")
    @RequiresPermissions("shophouse/saveShop")
    @ResponseBody
    public DataRes updateFlag(ShopHouse shopHouse,HttpServletRequest request,HttpServletResponse response){
        int i=0;
        if(shopHouse.getId()!=null){
            try{
                shopHouseService.updateShopHouse(shopHouse);
                if(shopHouse.getFlag()==1){
                    shopHouseSearchService.addShopHouseIndex(shopHouse.getId());
                }else{
                    shopHouseSearchService.deleteShopHouseIndex(shopHouse.getId());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return DataRes.success(0);
    }

    /**
     * 跳转到审核页面
     * @param shopHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("shophouse/gotoCheck")
    @RequiresPermissions("shophouse/checkShop")
    public String gotoCheckShop(ShopHouse shopHouse,HttpServletRequest request,HttpServletResponse response){
        if(shopHouse.getId()!=null){
            try {
                request.setAttribute("shophouse", shopHouseService.selectByPrimaryKey(shopHouse));
                ShopHouseImg shopHouseImg = new ShopHouseImg();
                shopHouseImg.setShopID(shopHouse.getId());
                shopHouseImg.setIsDel(0);
                request.setAttribute("imgList", shopHouseImgService.selectAll(shopHouseImg));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("shophouse",shopHouse);
            request.setAttribute("imgList",null);
        }
        return "sys/sys_shop_house_detail";
    }

    /**
     * 审核房源
     * @param shopHouse
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("shophouse/updateStatus")
    @RequiresPermissions("shophouse/checkShop")
    @ResponseBody
    public DataRes updateStatus(ShopHouse shopHouse,HttpServletResponse response,HttpServletRequest request){
        int i=0;
        if(shopHouse.getId()!=null){
            try {
                ShopHouse shopHouse1=shopHouseService.selectByPrimaryKey(shopHouse);
                if(shopHouse.getCheckStatus()!=null){
                    if(shopHouse1.getCheckStatus()==0&&shopHouse1.getCheckStatus()!=shopHouse.getCheckStatus()){
                        shopHouseService.sendSms(shopHouse.getCheckStatus(),shopHouse1.getTelephone(),shopHouse.getStatusContent());
                    }
                }
                i=shopHouseService.updateShopHouse(shopHouse);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return DataRes.success(i);
    }


    /**
     * 删除商铺房源图片
     * @param shopHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("shophouse/deleteImg")
    @ResponseBody
    public DataRes deleteImg(ShopHouseImg shopHouseImg, HttpServletRequest request, HttpServletResponse response){
        if(shopHouseImg.getId()!=null){
            ShopHouseImg shopHouseImg1=shopHouseImgService.selectByPrimaryKey(shopHouseImg);
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            ossClientUtil.deleteFile(shopHouseImg1.getImgUrl());
            shopHouseImg.setIsDel(1);
            return DataRes.success(shopHouseImgService.update(shopHouseImg));
        }else{
            return DataRes.success(0);
        }
    }

    /**
     * 导出
     * @param shopHouse
     * @param response
     * @param request
     */
    @RequestMapping("shophouse/export")
    public void export(ShopHouse shopHouse,HttpServletResponse response,HttpServletRequest request){
        List<ShopHouse> list=shopHouseService.selectAll(shopHouse);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","房源id");
        header.put("title","标题");
        header.put("coveredArea","面积");
        header.put("isFee_","是否包含物业费");
        header.put("propertyFee","物业费");
        header.put("faceWidth","面宽");
        header.put("standardTall","层高");
        header.put("floorLongth","进深");
        header.put("floorLevels","楼层");
        header.put("totalFloor","总楼层");
        header.put("status_","状态");
        header.put("adress","地址");
        header.put("ifTransfer_","是否装让");
        header.put("description","房源描述");
        header.put("createTime_","创建时间");
        header.put("createBy","用户id");
        header.put("monthlyRent","租金");
        header.put("transferFee","转让费");
        header.put("leaseMinimum","起租期");
        header.put("mortgagePayment_","出租方案");
        header.put("type_","商铺类型");
        header.put("checkStatus_","审核状态");
        header.put("fixture","装修情况");
        header.put("support","房屋配套");
        header.put("sex_","性别");
        header.put("userType_","用户状态");
        header.put("contacts","联系人");
        header.put("telephone","联系方式");
        header.put("operation","适合经营");
        header.put("shopName","商铺名称");
        header.put("flag_","上架状态");
        ExcelUtils.exportExcel("整租列表",header,list,response,request);
    }
}
