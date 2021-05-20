package com.aihouse.aihousesys.task;

import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.SysEmailSettingService;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import com.aihouse.aihouseservice.SysUserService;
import com.aihouse.aihouseservice.users.UserCertificationService;
import emailUtil.emailUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实名认证提醒
 */
@Component("UserCertificationCall")
public class UserCertificationCall {

    @Resource
    private UserCertificationService userCertificationService;

    @Resource
    private SysEmailSettingService sysEmailSettingService;

    @Resource
    private SysScheduleJobUserService sysScheduleJobUserService;

    @Resource
    private SysUserService sysUserService;

    public void execute(SysScheduleJob sysScheduleJob){
        UserCertification userCertification=new UserCertification();
        userCertification.setStatus(0);
        List<UserCertification> userCertificationList=userCertificationService.selectAll(userCertification);
        if(userCertificationList.size()>0){
            SysEmailSetting sysEmailSetting=new SysEmailSetting();
            List<SysEmailSetting> emailSettings=sysEmailSettingService.selectAll(sysEmailSetting);
            if(emailSettings.size()>0){
                String content="你现在有待审核的实名认证"+userCertificationList.size()+"条，请速去管理后台处理。";
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
                                , sysUser.getEmail(),"实名认证提醒",content);
                    }
                }
            }
        }
    }
}
