package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.Quartz.QuartzFactory;
import com.aihouse.aihousedao.bean.SysScheduleJob;
import com.aihouse.aihouseservice.QuartzService;
import com.aihouse.aihouseservice.SysScheduleJobService;
import enums.JobOperateEnum;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartzServiceImpl implements QuartzService {

    /**
     * 调度器
     */
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysScheduleJobService sysScheduleJobService;

    @Override
    public void timingTask() {
        //查询数据库是否存在需要定时的任务
        SysScheduleJob sysScheduleJob=new SysScheduleJob();
//        sysScheduleJob.setJobStatus(1);全部
        List<SysScheduleJob> scheduleJobs = sysScheduleJobService.selectAll(sysScheduleJob);
        if (scheduleJobs != null) {
            scheduleJobs.forEach(this::addJob);
        }
        sysScheduleJob.setJobStatus(2);
        scheduleJobs = sysScheduleJobService.selectAll(sysScheduleJob);
        for(SysScheduleJob s:scheduleJobs){
            try {
                operateJob(JobOperateEnum.PAUSE, s);
            }catch (SchedulerException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addJob(SysScheduleJob job) {
        try {
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                    .startNow()
                    .build();
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(QuartzFactory.class)
                    .withIdentity(job.getJobName())
                    .build();
            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put("scheduleJob", job);
            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void operateJob(JobOperateEnum jobOperateEnum, SysScheduleJob job) throws SchedulerException {
        JobKey jobKey = new JobKey(job.getJobName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            //抛异常
        }
        switch (jobOperateEnum) {
            case START:
                scheduler.resumeJob(jobKey);
                break;
            case PAUSE:
                scheduler.pauseJob(jobKey);
                break;
            case DELETE:
                scheduler.deleteJob(jobKey);
                break;
        }
    }

    @Override
    public void startAllJob() throws SchedulerException {
        scheduler.start();
    }

    @Override
    public void pauseAllJob() throws SchedulerException {
        scheduler.standby();
    }
}
