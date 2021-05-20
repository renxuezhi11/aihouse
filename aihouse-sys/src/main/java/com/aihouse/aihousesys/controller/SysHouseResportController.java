package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.HouseReport;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.HouseReportService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseSearchService;
import com.aihouse.aihouseservice.renthouse.RentHouseSearchService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.shophouse.ShopHouseSearchService;
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
 * 系统房源举报controller
 */
@Controller
public class SysHouseResportController {

    @Resource
    private HouseReportService houseReportService;

    @Resource
    private UsersService usersService;

    /**
     * 跳转到房源举报页面
     * @return
     */
    @RequestMapping("housereport/gotoList")
    @RequiresPermissions("housereport/gotoList")
    public String gotoReport(){
        return "sys/sys_house_report_list";
    }

    /**
     * 分页查询房源举报
     * @param houseReport
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("housereport/selectAll")
    @ResponseBody
    public DataRes selectAll(HouseReport houseReport, HttpServletRequest request, HttpServletResponse response){
        if(houseReport.getUsername()!=null&&!houseReport.getUsername().equals("")){
            Users users=new Users();
            users.setUsername(houseReport.getUsername());
            List<Users> list=usersService.selectAll(users);
            if(list.size()>0){
                houseReport.setUserId(list.get(0).getId());
            }
        }
        return DataRes.success(houseReportService.selectAllByPaging(houseReport));
    }


    /**
     * 导出
     * @param houseReport
     * @param response
     * @param request
     */
    @RequestMapping("housereport/export")
    public void export(HouseReport houseReport,HttpServletResponse response,HttpServletRequest request){
        List<HouseReport> list=houseReportService.selectAll(houseReport);
        Map<String,String> header=new LinkedHashMap<>();
        header.put("id","id");
        header.put("houseId","房源id");
        header.put("userId","用户id");
        header.put("houseType_","房源类型");
        header.put("content","举报内容");
        header.put("reason","举报原因");
        header.put("createtime_","举报时间");
        header.put("status_","举报状态");
        ExcelUtils.exportExcel("房源举报",header,list,response,request);
    }

    /**
     * 跳转到编辑页面
     * @param houseReport
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("housereport/gotoDetail")
    @RequiresPermissions("housereport/save")
    public String gotoDetail(HouseReport houseReport,HttpServletRequest request,HttpServletResponse response ){
        if(houseReport.getId()!=null){
            request.setAttribute("report",houseReportService.selectByPrimaryKey(houseReport));
        }else {
            request.setAttribute("report",houseReport);
        }
        return "sys/sys_house_report_detail";
    }


    /**
     * 保存
     * @param houseReport
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("housereport/save")
    @ResponseBody
    public DataRes save(HouseReport houseReport,HttpServletResponse response,HttpServletRequest request){
        return DataRes.success(houseReportService.update(houseReport));
    }
}
