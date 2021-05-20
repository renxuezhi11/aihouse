package com.aihouse.aihousesys.controller;


import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.SysEmailSetting;
import com.aihouse.aihouseservice.SysEmailSettingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SysEmailSettingController {

    @Resource
    private SysEmailSettingService emailSettingService;


    /**
     * 跳转到邮件设置页面
     * @param request
     * @return
     */
    @RequestMapping("emailSetting/gotoList")
    @RequiresPermissions("emailSetting/gotoList")
    public String gotoAppList(HttpServletRequest request){
        SysEmailSetting sysEmailSetting=new SysEmailSetting();
        List<SysEmailSetting> list=emailSettingService.selectAll(sysEmailSetting);
        if(list.size()>0){
            request.setAttribute("email",list.get(0));
        }else{
            request.setAttribute("email",sysEmailSetting);
        }
        return "sys/sys_email_setting";
    }

    /**
     * 保存邮箱设置
     * @param sysEmailSetting
     * @return
     */
    @ResponseBody
    @RequestMapping("emailSetting/save")
    @RequiresPermissions("emailSetting/save")
    public DataRes save(SysEmailSetting sysEmailSetting){
        if(sysEmailSetting.getId()==null){
            return DataRes.success(emailSettingService.insert(sysEmailSetting));
        }
        return DataRes.success(emailSettingService.update(sysEmailSetting));
    }

}
