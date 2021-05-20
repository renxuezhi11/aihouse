package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.SysLoginLog;
import com.aihouse.aihousedao.bean.SysOperationLog;
import com.aihouse.aihouseservice.SysLoginLogService;
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
public class SysLoginLogController {
    @Resource
    private SysLoginLogService sysLoginLogService;

    /**
     * 删除
     * @param sysLoginLog
     * @return
     */
    @RequestMapping("sysLoginLog/deleteByPrimaryKey")
    @ResponseBody
    public DataRes deleteByPrimaryKey(SysLoginLog sysLoginLog, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysLoginLogService.deleteByPrimaryKey(sysLoginLog));
    }

    /**
     * 保存 如果id存在则修改否则新增
     * @param sysLoginLog
     * @return
     */
    @RequestMapping("sysLoginLog/save")
    @ResponseBody
    public DataRes save(SysLoginLog sysLoginLog, HttpServletRequest request, HttpServletResponse response){
        if(sysLoginLog.getId()==null){
            return DataRes.success(sysLoginLogService.insert(sysLoginLog));
        }
        return DataRes.success(sysLoginLogService.update(sysLoginLog));
    }

    /**
     * 根据主键查询
     * @param sysLoginLog
     * @return
     */
    @RequestMapping("sysLoginLog/selectByPrimaryKey")
    @ResponseBody
    public DataRes selectByPrimaryKey(SysLoginLog sysLoginLog, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysLoginLogService.selectByPrimaryKey(sysLoginLog));
    }

    /**
     * 根据条件查询
     */
    @RequestMapping("sysLoginLog/querySysLoginLogByCondition")
    @ResponseBody
    public DataRes queryByCondition(SysLoginLog sysLoginLog, HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysLoginLogService.queryByCondition(sysLoginLog));
    }

    /**
     * 分页查询
     * @param sysLoginLog 参数
     * @return
     */
    @RequestMapping("sysLoginLog/selectAll")
    @ResponseBody
    public DataRes selectAll(SysLoginLog sysLoginLog,HttpServletRequest request, HttpServletResponse response){
        return DataRes.success(sysLoginLogService.selectAllByPaging(sysLoginLog));
    }

    /**
     * 导出数据
     * @param sysLoginLog 参数
     * @return
     */
    @RequestMapping("sysLoginLog/export")
    public void export(SysLoginLog sysLoginLog,HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SysLoginLog> list= sysLoginLogService.selectAll(sysLoginLog);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id", "");
        header.put("sysUserId", "用户id");
        header.put("createTime_", "创建时间(也是登录时间)");
        header.put("ip", "ip");
        header.put("browser", "浏览器");
        ExcelUtils.exportExcel("",header,list,response,request);
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @RequestMapping("sysLoginLog/gotoList")
    public String gotoList(SysOperationLog sysOperationLog, HttpServletRequest request, HttpServletResponse response){
        return "sys/sys_login_log_list.html";
    }

}
