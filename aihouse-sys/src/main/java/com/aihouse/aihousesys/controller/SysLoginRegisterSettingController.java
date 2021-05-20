package com.aihouse.aihousesys.controller;


import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.SysEmailSetting;
import com.aihouse.aihousedao.bean.SysLoginRegisterSetting;
import com.aihouse.aihouseservice.SysEmailSettingService;
import com.aihouse.aihouseservice.SysLoginRegisterSettingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SysLoginRegisterSettingController {

    @Resource
    private SysLoginRegisterSettingService loginRegisterSettingService;


    /**
     * 跳转到登录注册设置页面
     * @param request
     * @return
     */
    @RequestMapping("loginRegisterSetting/gotoList")
    @RequiresPermissions("loginRegisterSetting/gotoList")
    public String gotoAppList(HttpServletRequest request){
        SysLoginRegisterSetting sysLoginRegisterSetting=new SysLoginRegisterSetting();
        List<SysLoginRegisterSetting> list=loginRegisterSettingService.selectAll(sysLoginRegisterSetting);
        if(list.size()>0){
            request.setAttribute("loginRegister",list.get(0));
        }else{
            request.setAttribute("loginRegister",sysLoginRegisterSetting);
        }
        return "sys/sys_login_register_setting";
    }

    /**
     * 保存登录注册设置
     * @param sysLoginRegisterSetting
     * @return
     */
    @ResponseBody
    @RequestMapping("loginRegisterSetting/save")
    @RequiresPermissions("loginRegisterSetting/save")
    public DataRes save(SysLoginRegisterSetting sysLoginRegisterSetting){
        if(sysLoginRegisterSetting.getId()==null){
            return DataRes.success(loginRegisterSettingService.insert(sysLoginRegisterSetting));
        }
        return DataRes.success(loginRegisterSettingService.update(sysLoginRegisterSetting));
    }

}
