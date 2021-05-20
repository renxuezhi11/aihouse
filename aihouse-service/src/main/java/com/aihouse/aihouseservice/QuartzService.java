package com.aihouse.aihouseservice;

import com.aihouse.aihousedao.bean.SysScheduleJob;
import enums.JobOperateEnum;
import org.quartz.SchedulerException;

public interface QuartzService {

    /**
     * 服务器启动执行定时任务
     *
     * @author lanjerry
     * @date 2019/1/28 15:38
     */
    void timingTask();

    /**
     * 新增定时任务
     *
     * @author lanjerry
     * @date 2019/1/28 15:44
     * @param job 任务
     */
    void addJob(SysScheduleJob job);

    /**
     * 操作定时任务
     *
     * @author lanjerry
     * @date 2019/1/28 16:56
     * @param jobOperateEnum 操作枚举
     * @param job 任务
     */
    void operateJob(JobOperateEnum jobOperateEnum, SysScheduleJob job) throws SchedulerException;

    /**
     * 启动所有任务
     *
     * @author lanjerry
     * @date 2019/1/28 16:58
     */
    void startAllJob() throws SchedulerException;

    /**
     * 暂停所有任务
     *
     * @author lanjerry
     * @date 2019/1/28 16:58
     */
    void pauseAllJob() throws SchedulerException;
}
