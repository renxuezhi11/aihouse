package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.Community;
import com.aihouse.aihousedao.bean.CommunityReport;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.CommunityReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SysCommunityReportController {

    @Resource
    private CommunityReportService communityReportService;

    /**
     * 跳转到圈子审核页面
     * @return
     */
    @RequestMapping("communityReport/gotoList")
    @RequiresPermissions("communityReport/gotoList")
    public String gotoList(){
        return "sys/sys_community_report_list";
    }


    /**
     * 分页查询
     * @param communityReport
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("communityReport/selectAll")
    @RequiresPermissions("communityReport/selectAll")
    @ResponseBody
    public DataRes selectAll(CommunityReport communityReport, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(communityReportService.selectAllByPaging(communityReport));
    }


    /**
     * 删除-
     */
    @ResponseBody
    @RequestMapping("communityReport/delete")
    @RequiresPermissions("communityReport/delete")
    public DataRes deleteByPrimaryKey(CommunityReport communityReport, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(communityReportService.deleteByPrimaryKey(communityReport));
    }

    /**
     * 审核-
     */
    @ResponseBody
    @RequestMapping("communityReport/check")
    @RequiresPermissions("communityReport/check")
    public DataRes checkByPrimaryKey(CommunityReport communityReport, HttpServletRequest request, HttpServletResponse response){
        communityReport.setStatus(1);
        return DataRes.success(communityReportService.update(communityReport));
    }
}
