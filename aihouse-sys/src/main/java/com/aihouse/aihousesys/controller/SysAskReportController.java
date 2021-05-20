package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.AskReport;
import com.aihouse.aihousedao.bean.CommunityReport;
import com.aihouse.aihouseservice.AskReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SysAskReportController {

    @Resource
    private AskReportService askReportService;


    /**
     * 跳转到审核页面
     * @return
     */
    @RequestMapping("sysAskReport/gotoList")
    @RequiresPermissions("sysAskReport/gotoList")
    public String gotoList(){
        return "sys/sys_ask_report_list";
    }

    /**
     * 分页查询
     * @param askReport
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysAskReport/selectAll")
    @RequiresPermissions("sysAskReport/selectAll")
    @ResponseBody
    public DataRes selectAll(AskReport askReport, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(askReportService.selectAllByPaging(askReport));
    }

    /**
     * 删除-
     */
    @ResponseBody
    @RequestMapping("sysAskReport/delete")
    @RequiresPermissions("sysAskReport/delete")
    public DataRes deleteByPrimaryKey(AskReport askReport, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(askReportService.deleteByPrimaryKey(askReport));
    }

    /**
     * 审核-
     */
    @ResponseBody
    @RequestMapping("sysAskReport/check")
    @RequiresPermissions("sysAskReport/check")
    public DataRes checkByPrimaryKey(AskReport askReport, HttpServletRequest request, HttpServletResponse response){
        askReport.setStatus(1);
        return DataRes.success(askReportService.update(askReport));
    }

}
