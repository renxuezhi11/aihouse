package com.aihouse.aihouseservice.impl;

import com.aihouse.aihousedao.bean.SysScheduleJob;
import com.aihouse.aihousedao.dao.SysScheduleJobDao;
import com.aihouse.aihouseservice.QuartzService;
import com.aihouse.aihouseservice.SysScheduleJobService;
import enums.JobOperateEnum;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysScheduleJobServiceImpl implements SysScheduleJobService {


    @Resource
    private SysScheduleJobDao sysScheduleJobDao;

    @Resource
    private QuartzService quartzService;

    @Override
    public SysScheduleJobDao initDao() {
        return this.sysScheduleJobDao;
    }

    @Override
    public void add(SysScheduleJob job) {

        this.insert(job);
        //加入job
        try {
            quartzService.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addJob(SysScheduleJob job) {
        //加入job
        try {
            quartzService.addJob(job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(int id) {
        //此处省去数据验证
        SysScheduleJob job=new SysScheduleJob();
        job.setId(id);
        job = this.selectByPrimaryKey(job);
        job.setJobStatus(1);
        this.update(job);
        //执行job
        try {
            quartzService.operateJob(JobOperateEnum.START, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause(int id) {
        //此处省去数据验证
        SysScheduleJob job=new SysScheduleJob();
        job.setId(id);
        job = this.selectByPrimaryKey(job);
        job.setJobStatus(2);
        this.update(job);
        //执行job
        try {
            quartzService.operateJob(JobOperateEnum.PAUSE, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        //此处省去数据验证
        SysScheduleJob job=new SysScheduleJob();
        job.setId(id);
        job = this.selectByPrimaryKey(job);
        this.deleteByPrimaryKey(job);
        //执行job
        try {
            quartzService.operateJob(JobOperateEnum.DELETE, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOnlyJob(int id) {
        SysScheduleJob job=new SysScheduleJob();
        job.setId(id);
        job = this.selectByPrimaryKey(job);
        try {
            quartzService.operateJob(JobOperateEnum.DELETE, job);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
