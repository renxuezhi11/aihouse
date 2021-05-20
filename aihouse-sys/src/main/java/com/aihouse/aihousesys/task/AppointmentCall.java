package com.aihouse.aihousesys.task;


import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.AppointmentService;
import com.aihouse.aihouseservice.SysEmailSettingService;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import com.aihouse.aihouseservice.SysUserService;
import emailUtil.emailUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("AppointmentCall")
public class AppointmentCall {

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private SysEmailSettingService sysEmailSettingService;

    @Resource
    private SysScheduleJobUserService sysScheduleJobUserService;

    @Resource
    private SysUserService sysUserService;

    public void execute(SysScheduleJob sysScheduleJob){
        Appointment appointment=new Appointment();
        appointment.setStatus(0);
        List<Appointment> list=appointmentService.selectAll(appointment);
        if(list.size()>0){
            SysEmailSetting sysEmailSetting=new SysEmailSetting();
            List<SysEmailSetting> emailSettings=sysEmailSettingService.selectAll(sysEmailSetting);
            if(emailSettings.size()>0){
                String content="你现在有待处理的预约"+list.size()+"条，请速去管理后台处理。";
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
                                , sysUser.getEmail(),"预约处理提醒",content);
                    }
                }
            }
        }
    }
}
