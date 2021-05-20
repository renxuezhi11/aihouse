package com.aihouse.aihousedao.Quartz;

import com.aihouse.aihousecore.utils.SpringUtils;
import com.aihouse.aihousedao.bean.SysScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

public class QuartzFactory implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SysScheduleJob scheduleJob = (SysScheduleJob) jobExecutionContext.getMergedJobDataMap().get("scheduleJob");
        //获取对应的Bean
        Object object = SpringUtils.getBean(scheduleJob.getQuartzClass());
        try {
            //利用反射执行对应方法
            Method method = object.getClass().getMethod("execute",SysScheduleJob.class);
            method.invoke(object,scheduleJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
