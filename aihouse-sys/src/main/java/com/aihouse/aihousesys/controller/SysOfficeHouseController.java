package com.aihouse.aihousesys.controller;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.OfficeHouse;
import com.aihouse.aihousedao.bean.OfficeHouseImg;
import com.aihouse.aihousedao.bean.ShopHouseImg;
import com.aihouse.aihouseservice.officehouse.OfficeHouseImgService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseService;
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
 * 写字楼出租系统管理
 */
@Controller
public class SysOfficeHouseController {

    @Resource
    private OfficeHouseService officeHouseService;

    @Resource
    private OfficeHouseImgService officeHouseImgService;

    @Resource
    private OfficeHouseSearchService officeHouseSearchService;

    /**
     * 跳转到写字楼出租房源页面
     * @return
     */
    @RequestMapping("officehouse/gotoList")
    @RequiresPermissions("officehouse/gotoList")
    public String gotoList(){
        return "sys/sys_office_house_list";
    }


    /**
     * 分页查询写字楼房源
     * @param officeHouse
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("officehouse/selectAll")
    @ResponseBody
    public DataRes selectList(OfficeHouse officeHouse, HttpServletResponse response, HttpServletRequest request){
        officeHouse.setOrderByString(" order by createtime desc");
        return DataRes.success(officeHouseService.selectAllByPaging(officeHouse));
    }

    /**
     * 房源上下架
     * @param officeHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("officehouse/updateFlag")
    @RequiresPermissions("officehouse/saveOffice")
    @ResponseBody
    public DataRes updateFlag(OfficeHouse officeHouse,HttpServletRequest request,HttpServletResponse response){
        int i=0;
        if(officeHouse.getId()!=null){
            try{
                officeHouseService.updateOfficeHouse(officeHouse);
                if(officeHouse.getFlag()!=null){
                    if(officeHouse.getFlag()==1){
                        officeHouseSearchService.addOfficeHouseIndex(officeHouse.getId());
                    }else{
                        officeHouseSearchService.deleteOfficeHouseIndex(officeHouse.getId());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return DataRes.success(0);
    }

    /**
     * 跳转到审核页面
     * @param officeHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("officehouse/gotoCheck")
    @RequiresPermissions("officehouse/checkOffice")
    public String gotoCheckShop(OfficeHouse officeHouse,HttpServletRequest request,HttpServletResponse response){
        if(officeHouse.getId()!=null){
            request.setAttribute("officehouse",officeHouseService.selectByPrimaryKey(officeHouse));
            OfficeHouseImg officeHouseImg=new OfficeHouseImg();
            officeHouseImg.setOfficeID(officeHouse.getId());
            officeHouseImg.setIsDel(0);
            request.setAttribute("imgList",officeHouseImgService.selectAll(officeHouseImg));
        }else{
            request.setAttribute("officehouse",officeHouse);
            request.setAttribute("imgList",null);
        }
        return "sys/sys_office_house_detail";
    }

    /**
     * 审核房源
     * @param officeHouse
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("officehouse/updateStatus")
    @RequiresPermissions("officehouse/checkOffice")
    @ResponseBody
    public DataRes updateStatus(OfficeHouse officeHouse,HttpServletResponse response,HttpServletRequest request){
        int i=0;
        if(officeHouse.getId()!=null){
            try {
                OfficeHouse officeHouse1=officeHouseService.selectByPrimaryKey(officeHouse);
                if(officeHouse.getCheckStatus()!=null){
                    if(officeHouse1.getCheckStatus()==0&&officeHouse1.getCheckStatus()!=officeHouse.getCheckStatus()){
                        officeHouseService.sendSms(officeHouse.getCheckStatus(),officeHouse1.getTelephone(),officeHouse.getStatusContent());
                    }
                }
                i=officeHouseService.updateOfficeHouse(officeHouse);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return DataRes.success(i);
    }


    /**
     * 删除商铺房源图片
     * @param officeHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("officehouse/deleteImg")
    @ResponseBody
    public DataRes deleteImg(OfficeHouseImg officeHouseImg, HttpServletRequest request, HttpServletResponse response){
        if(officeHouseImg.getId()!=null){
            OfficeHouseImg officeHouseImg1=officeHouseImgService.selectByPrimaryKey(officeHouseImg);
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            ossClientUtil.deleteFile(officeHouseImg1.getImgUrl());
            officeHouseImg.setIsDel(1);
            return DataRes.success(officeHouseImgService.update(officeHouseImg));
        }else{
            return DataRes.success(0);
        }
    }

    /**
     * 导出
     * @param officeHouse
     * @param request
     * @param response
     */
    @RequestMapping("officehouse/export")
    public void export(OfficeHouse officeHouse,HttpServletRequest request,HttpServletResponse response){
        List<OfficeHouse> list=officeHouseService.selectAll(officeHouse);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","房源id");
        header.put("title","标题");
        header.put("name","名称");
        header.put("description","房源描述");
        header.put("coveredArea","面积");
        header.put("floorLevels","楼层");
        header.put("totalFloor","总楼层");
        header.put("locationNumber","工位数");
        header.put("propertyFee","物业费");
        header.put("officeNumber","门牌号");
        header.put("subway","地铁");
        header.put("monthlyRent","租金");
        header.put("type_","类型");
        header.put("adress","地址");
        header.put("userId","用户id");
        header.put("createTime_","创建时间");
        header.put("rentWay_","出租方案");
        header.put("sex_","性别");
        header.put("contacts","联系人");
        header.put("telephone","联系方式");
        header.put("userType_","用户类型");
        header.put("rentTime","入驻时间");
        header.put("fixture","装修情况");
        header.put("flag_","上架状态");
        header.put("checkStatus_","审核状态");
        ExcelUtils.exportExcel("写字楼列表",header,list,response,request);
    }
}
