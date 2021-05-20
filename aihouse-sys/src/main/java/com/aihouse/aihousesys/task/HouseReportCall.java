package com.aihouse.aihousesys.task;

import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.HouseReportService;
import com.aihouse.aihouseservice.SysEmailSettingService;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import com.aihouse.aihouseservice.SysUserService;
import emailUtil.emailUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 房源举报提醒
 */
@Component("HouseReportCall")
public class HouseReportCall {

    @Resource
    private HouseReportService houseReportService;

    @Resource
    private SysEmailSettingService sysEmailSettingService;

    @Resource
    private SysScheduleJobUserService sysScheduleJobUserService;

    @Resource
    private SysUserService sysUserService;

    public void execute(SysScheduleJob sysScheduleJob){
        HouseReport houseReport=new HouseReport();
        houseReport.setStatus(0);
        List<HouseReport> houseReportList=houseReportService.selectAll(houseReport);
        if(houseReportList.size()>0){
            SysEmailSetting sysEmailSetting=new SysEmailSetting();
            List<SysEmailSetting> emailSettings=sysEmailSettingService.selectAll(sysEmailSetting);
            if(emailSettings.size()>0){
                String content="你现在有待审核的房源举报"+houseReportList.size()+"条，请速去管理后台处理。";
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
                                , sysUser.getEmail(),"房源举报审核提醒",content);
                    }
                }
            }
        }
    }
}
