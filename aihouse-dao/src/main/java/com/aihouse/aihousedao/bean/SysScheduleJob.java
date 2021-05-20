package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 定时任务实体类
 */
public class SysScheduleJob extends Page {

    private Integer id;

    private String jobName;

    private Integer jobStatus;

    private String cronExpression;

    private String quartzClass;

    private String description;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getQuartzClass() {
        return quartzClass;
    }

    public void setQuartzClass(String quartzClass) {
        this.quartzClass = quartzClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getJobStatus_(){
        if(this.jobStatus!=null){
            if(this.jobStatus==1){
                return "已启动";
            }else if(this.jobStatus==2){
                return "已禁用";
            }
        }
        return "";
    }
}
