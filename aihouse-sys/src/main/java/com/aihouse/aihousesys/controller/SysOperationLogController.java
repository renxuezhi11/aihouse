package com.aihouse.aihousesys.controller;


import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.SysOperationLog;
import com.aihouse.aihouseservice.SysOperationLogService;
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

@Controller
public class SysOperationLogController {

    @Resource
    private SysOperationLogService sysOperationLogService;


    /**
     * 根据主键查询
     * @param sysOperationLog
     * @return
     */
    @RequestMapping("sysOperationLog/selectByPrimaryKey")
    @ResponseBody
    public DataRes selectByPrimaryKey(SysOperationLog sysOperationLog, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysOperationLogService.selectByPrimaryKey(sysOperationLog));
    }

    /**
     * 根据条件查询
     */
    @RequestMapping("sysOperationLog/querySysOperationLogByCondition")
    @ResponseBody
    public DataRes queryByCondition(SysOperationLog sysOperationLog, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysOperationLogService.queryByCondition(sysOperationLog));
    }

    /**
     * 分页查询
     * @param sysOperationLog 参数
     * @return
     */
    @RequestMapping("sysOperationLog/selectAll")
    @ResponseBody
    public DataRes selectAll(SysOperationLog sysOperationLog,HttpServletRequest request, HttpServletResponse response){
        sysOperationLog.setOrderByString("order by id desc");
        return DataRes.success(sysOperationLogService.selectAllByPaging(sysOperationLog));
    }

    /**
     * 导出数据
     * @param sysOperationLog 参数
     * @return
     */
    @RequestMapping("sysOperationLog/export")
    public void export(SysOperationLog sysOperationLog,HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SysOperationLog> list= sysOperationLogService.selectAll(sysOperationLog);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "操作日志");
        header.put("sysUserId", "用户id");
        header.put("sysAuthId", "权限id");
        header.put("authName", "权限名");
        header.put("authHref", "权限链接");
        header.put("ip", "IP地址");
        header.put("createTime_", "创建时间");
        header.put("requestParam", "请求参数");
        header.put("exceptions", "异常");
        ExcelUtils.exportExcel("操作日志",header,list,response,request);
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @RequestMapping("sysOperationLog/gotoList")
    public String gotoList(SysOperationLog sysOperationLog, HttpServletRequest request, HttpServletResponse response){
        return "sys/sys_operation_log_list";
    }

    /**
     * 跳转到详情页面
     * @return
     */
    @RequestMapping("sysOperationLog/gotoDetail")
    @RequiresPermissions("sysOperationLog/save")
    public String gotoDetail(SysOperationLog sysOperationLog, HttpServletRequest request, HttpServletResponse response){
        if(sysOperationLog.getId()!=null){
            request.setAttribute("sys_operation_log",sysOperationLogService.selectByPrimaryKey(sysOperationLog));
        }else {
            request.setAttribute("sys_operation_log",sysOperationLog);
        }
        return "sys/sys_operation_log_detail";
    }
}
