package com.aihouse.aihousesys.controller;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.RentHouse;
import com.aihouse.aihousedao.bean.RentHouseImg;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseImgService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseService;
import com.aihouse.aihouseservice.users.UsersService;
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
 * 租房管理
 */
@Controller
public class SysRentHouseController {

    @Resource
    private SysRentHouseService sysRentHouseService;

    @Resource
    private SysRentHouseImgService sysRentHouseImgService;

    @Resource
    private RentHouseSearchService rentHouseSearchService;



    /**
     * 跳转到整租页面
     * @return
     */
    @RequestMapping("renthouse/gotoWholeList")
    @RequiresPermissions("renthouse/gotoWholeList")
    public String gotoWholeList(){
        return "sys/sys_renthouse_whole_list";
    }

    /**
     * 跳转到合租页面
     * @return
     */
    @RequestMapping("renthouse/gotoJointList")
    @RequiresPermissions("renthouse/gotoJointList")
    public String gotoJointList(){
        return "sys/sys_renthouse_joint_list";
    }

    /**
     * 分页查询整租房源
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/selectWholeAll")
    @ResponseBody
    public DataRes selectWholeList(RentHouse rentHouse, HttpServletRequest request, HttpServletResponse response){
        rentHouse.setRentMode(1);
        rentHouse.setOrderByString("order by createtime desc");
        return DataRes.success(sysRentHouseService.selectAllByPaging(rentHouse));
    }


    /**
     * 分页查询合租房源
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/selectJointAll")
    @ResponseBody
    public DataRes selectJointList(RentHouse rentHouse, HttpServletRequest request, HttpServletResponse response){
        rentHouse.setRentMode(2);
        rentHouse.setOrderByString("order by createtime desc");
        return DataRes.success(sysRentHouseService.selectAllByPaging(rentHouse));
    }
    /**
     * 上下架房源
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/updateFlag")
    @ResponseBody
    public DataRes updateFlag(RentHouse rentHouse, HttpServletRequest request, HttpServletResponse response){
        if(rentHouse!=null) {
            try{
                int i=sysRentHouseService.updateRent(rentHouse);
                if(rentHouse.getFlag()==1){//添加索引
                    rentHouseSearchService.addRentHouseIndex(rentHouse.getId());
                }else{
                    rentHouseSearchService.deleteRentHouseIndex(rentHouse.getId());
                }
                return DataRes.success(i);
            }catch (Exception e){
                e.printStackTrace();
                return DataRes.success(0);
            }
        }else{
            return DataRes.success(0);
        }
    }

    /**
     * 审核房源
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/updateStatus")
    @ResponseBody
    public DataRes updateStatus(RentHouse rentHouse, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysRentHouseService.updateRent(rentHouse));
    }


    /**
     *跳转到整租审核页面
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/gotoCheckWhole")
    @RequiresPermissions("renthouse/checkWhole")
    public String gotoCheckWhole(RentHouse rentHouse,HttpServletRequest request, HttpServletResponse response){
        if(rentHouse.getId()!=null){
            try {
                request.setAttribute("renthouse",sysRentHouseService.selectByPrimaryKey(rentHouse));
                RentHouseImg rentHouseImg=new RentHouseImg();
                rentHouseImg.setRentHouse(rentHouse.getId());
                rentHouseImg.setIsDel(0);
                request.setAttribute("imgList", sysRentHouseImgService.selectAll(rentHouseImg));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("renthouse",rentHouse);
            request.setAttribute("imgList",null);
        }
        return "sys/sys_renthouse_whole_detail";
    }

    /**
     *跳转到整租审核页面
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/gotoCheck")
    @RequiresPermissions("renthouse/checkWhole")
    public String gotoCheck(RentHouse rentHouse,HttpServletRequest request, HttpServletResponse response){
        if(rentHouse.getId()!=null){
            try {
                request.setAttribute("renthouse",sysRentHouseService.selectByPrimaryKey(rentHouse));
                RentHouseImg rentHouseImg=new RentHouseImg();
                rentHouseImg.setRentHouse(rentHouse.getId());
                rentHouseImg.setIsDel(0);
                request.setAttribute("imgList", sysRentHouseImgService.selectAll(rentHouseImg));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("renthouse",rentHouse);
            request.setAttribute("imgList",null);
        }
        if(rentHouse.getRentMode()==1) {
            return "sys/sys_renthouse_whole_detail";
        }else{
            return "sys/sys_renthouse_joint_detail";
        }
    }

    /**
     *跳转到合租审核页面
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/gotoCheckJoint")
    @RequiresPermissions("renthouse/checkJoint")
    public String gotoCheckJoint(RentHouse rentHouse,HttpServletRequest request, HttpServletResponse response){
        if(rentHouse.getId()!=null){
            try {
                request.setAttribute("renthouse",sysRentHouseService.selectByPrimaryKey(rentHouse));
                RentHouseImg rentHouseImg=new RentHouseImg();
                rentHouseImg.setRentHouse(rentHouse.getId());
                rentHouseImg.setIsDel(0);
                request.setAttribute("imgList", sysRentHouseImgService.selectAll(rentHouseImg));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("renthouse",rentHouse);
            request.setAttribute("imgList",null);
        }
        return "sys/sys_renthouse_joint_detail";
    }
    /**
     * 更新房源
     * @param rentHouse
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/saveWhole")
    @ResponseBody
    public DataRes saveWhole(RentHouse rentHouse,HttpServletRequest request, HttpServletResponse response){
        RentHouse rentHouse1=sysRentHouseService.selectByPrimaryKey(rentHouse);
        if(rentHouse.getStatus()!=null) {
            if (rentHouse1.getStatus() == 0&&rentHouse.getStatus()!=rentHouse1.getStatus()) {
                //审核状态变化，短信通知
                sysRentHouseService.sendSms(rentHouse.getStatus(),rentHouse1.getTelephone(),rentHouse.getStatusContent());
            }
        }
        return DataRes.success(sysRentHouseService.updateRent(rentHouse));
    }

    /**
     * 删除房源图片
     * @param rentHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("renthouse/deleteImg")
    @ResponseBody
    public DataRes deleteImg(RentHouseImg rentHouseImg, HttpServletRequest request,HttpServletResponse response){
        if(rentHouseImg.getId()!=null){
            RentHouseImg rentHouseImg1=sysRentHouseImgService.selectByPrimaryKey(rentHouseImg);
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            ossClientUtil.deleteFile(rentHouseImg1.getImgUrl());
            rentHouseImg.setIsDel(1);
            return DataRes.success(sysRentHouseImgService.update(rentHouseImg));
        }else{
            return DataRes.success(0);
        }
    }

    /**
     * 整租导出
     * @param rentHouse
     * @param request
     * @param response
     */
    @RequestMapping("renthouse/wholeExport")
    public void wholeExport(RentHouse rentHouse,HttpServletRequest request,HttpServletResponse response){
        rentHouse.setRentMode(1);
        rentHouse.setOrderByString("order by createtime desc");
        List<RentHouse> list=sysRentHouseService.selectAll(rentHouse);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","房源编号");
        header.put("userId","用户id");
        header.put("contacts","联系人");
        header.put("telephone","手机号");
        header.put("sex_","性别");
        header.put("userType_","用户类型");
        header.put("coveredType","建筑类型");
        header.put("room","室");
        header.put("hall","厅");
        header.put("toilet","卫生间");
        header.put("coveredArea","建筑面积");
        header.put("floor","楼层");
        header.put("totalFloor","总楼层");
        header.put("villageName","小区名称");
        header.put("address","地址");
        header.put("support","配套");
        header.put("fixture","装修情况");
        header.put("orientations","朝向");
        header.put("title","标题");
        header.put("createtime_","添加时间");
        header.put("status_","审核状态");
        header.put("rentFee","租金");
        header.put("rentTime","入驻时间");
        header.put("rentWay_","出租方案");
        header.put("flag_","上架状态");
        ExcelUtils.exportExcel("整租列表",header,list,response,request);
    }
    /**
     * 合租导出
     * @param rentHouse
     * @param request
     * @param response
     */
    @RequestMapping("renthouse/jointExport")
    public void jointExport(RentHouse rentHouse,HttpServletRequest request,HttpServletResponse response){
        rentHouse.setRentMode(2);
        rentHouse.setOrderByString("order by createtime desc");
        List<RentHouse> list=sysRentHouseService.selectAll(rentHouse);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","房源编号");
        header.put("userId","用户id");
        header.put("contacts","联系人");
        header.put("telephone","手机号");
        header.put("sex_","性别");
        header.put("userType_","用户类型");
        header.put("coveredType","建筑类型");
        header.put("room","室");
        header.put("hall","厅");
        header.put("toilet","卫生间");
        header.put("coveredArea","建筑面积");
        header.put("floor","楼层");
        header.put("totalFloor","总楼层");
        header.put("villageName","小区名称");
        header.put("address","地址");
        header.put("support","配套");
        header.put("fixture","装修情况");
        header.put("orientations","朝向");
        header.put("title","标题");
        header.put("createtime_","添加时间");
        header.put("status_","审核状态");
        header.put("rentFee","租金");
        header.put("rentPeople","合租人数");
        header.put("rentRoom_","合租房间");
        header.put("rentSex_","性别要求");
        header.put("rentTime","入驻时间");
        header.put("rentWay_","出租方案");
        header.put("flag_","上架状态");
        ExcelUtils.exportExcel("合租列表",header,list,response,request);
    }
}
