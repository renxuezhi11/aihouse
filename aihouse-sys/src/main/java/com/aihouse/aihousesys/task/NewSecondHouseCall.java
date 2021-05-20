package com.aihouse.aihousesys.task;

import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.SysEmailSettingService;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import com.aihouse.aihouseservice.SysUserService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import emailUtil.emailUtil;

@Component("NewSecondHouseCall")
public class NewSecondHouseCall {

    @Resource
    private SecondHandHouseService secondHandHouseService;

    @Resource
    private SysEmailSettingService sysEmailSettingService;

    @Resource
    private SysScheduleJobUserService sysScheduleJobUserService;

    @Resource
    private SysUserService sysUserService;

    public void execute(SysScheduleJob sysScheduleJob){
        System.out.println("-----执行中----");
        //查询未审核的二手房
        SecondHandHouse secondHandHouse=new SecondHandHouse();
        secondHandHouse.setStatus(0);
        List<SecondHandHouse> list=secondHandHouseService.selectAll(secondHandHouse);
        if(list.size()>0){
            SysEmailSetting sysEmailSetting=new SysEmailSetting();
            List<SysEmailSetting> emailSettings=sysEmailSettingService.selectAll(sysEmailSetting);
            if(emailSettings.size()>0){
                String content="你现在有待审核的二手房源"+list.size()+"套，请速去管理后台处理。";
                SysEmailSetting sysEmailSetting1=emailSettings.get(0);
                SysScheduleJobUser sysScheduleJobUser=new SysScheduleJobUser();
                sysScheduleJobUser.setJobId(sysScheduleJob.getId());
                List<SysScheduleJobUser> sysScheduleJobUserList =sysScheduleJobUserService.selectAll(sysScheduleJobUser);
                for(SysScheduleJobUser s:sysScheduleJobUserList) {
                    SysUser sysUser=new SysUser();
                    sysUser.setId(s.getUserId());
                    sysUser=sysUserService.selectByPrimaryKey(sysUser);
                    if(sysUser.getEmail()!=null&&!sysUser.getEmail().equals("")) {
                        emailUtil.SendMail(sysEmailSetting1.getSmtpHost(), sysEmailSetting1.getSmtpPort(), sysEmailSetting1.getEmailAccount(), sysEmailSetting1.getEmailPassword()
                                , sysUser.getEmail(),"二手房审核提醒",content);
                    }
                }
            }

        }
    }
}
