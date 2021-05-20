package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.SysScheduleJob;
import com.aihouse.aihousedao.bean.SysScheduleJobUser;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihouseservice.QuartzService;
import com.aihouse.aihouseservice.SysScheduleJobService;
import com.aihouse.aihouseservice.SysScheduleJobUserService;
import com.aihouse.aihouseservice.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SysScheduleController {

    @Resource
    private SysScheduleJobService sysScheduleJobService;

    @Resource
    private SysScheduleJobUserService sysScheduleJobUserService;

    @Resource
    private SysUserService sysUserService;
    /**
     * 定时任务页面
     * @return
     */
    @RequestMapping("job/gotoList")
    @RequiresPermissions("job/gotoList")
    public String gotoJobList(){
        return "sys/sys_schedule_job_list";
    }


    /**
     * 分页查询
     * @param sysScheduleJob
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("job/selectAll")
    @ResponseBody
    public DataRes selectAll(SysScheduleJob sysScheduleJob, HttpServletRequest request, HttpServletResponse response){
        sysScheduleJob.setOrderByString(" order by createtime desc ");
        sysScheduleJob=sysScheduleJobService.selectAllByPaging(sysScheduleJob);
        return DataRes.success(sysScheduleJob);
    }


    /**
     * 启动
     * @param sysScheduleJob
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("job/start")
    @RequiresPermissions("job/start")
    @ResponseBody
    public DataRes start(SysScheduleJob sysScheduleJob,HttpServletRequest request,HttpServletResponse response){
        if(sysScheduleJob.getId()!=null){
            sysScheduleJobService.start(sysScheduleJob.getId());
        }
        return DataRes.success("");
    }


    /**
     * 跳转到详情页
     * @param sysScheduleJob
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("job/gotoDetail")
    @RequiresPermissions("job/save")
    public String gotoDetail(SysScheduleJob sysScheduleJob,HttpServletResponse response,HttpServletRequest request){
        if(sysScheduleJob.getId()!=null){
            sysScheduleJob=sysScheduleJobService.selectByPrimaryKey(sysScheduleJob);
            request.setAttribute("job",sysScheduleJob);
        }else{
            request.setAttribute("job",sysScheduleJob);
        }
        return "sys/sys_schedule_job_detail";
    }

    /**
     * 停止
     * @param sysScheduleJob
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("job/stop")
    @RequiresPermissions("job/stop")
    @ResponseBody
    public DataRes stop(SysScheduleJob sysScheduleJob,HttpServletRequest request,HttpServletResponse response){
        if(sysScheduleJob.getId()!=null){
            sysScheduleJobService.pause(sysScheduleJob.getId());
        }
        return DataRes.success("");
    }

    /**
     * 删除定时任务
     * @param sysScheduleJob
     * @return
     */
    @RequestMapping("job/delete")
    @RequiresPermissions("job/delete")
    @ResponseBody
    public DataRes delete(SysScheduleJob sysScheduleJob){
        if(sysScheduleJob.getId()!=null){
            sysScheduleJobService.delete(sysScheduleJob.getId());
        }
        return DataRes.success("");
    }

    /**
     *保存修改
     * @param sysScheduleJob
     * @return
     */
    @RequestMapping("job/save")
    @RequiresPermissions("job/save")
    @ResponseBody
    public DataRes save(SysScheduleJob sysScheduleJob){
        if(sysScheduleJob.getId()!=null){
            sysScheduleJobService.update(sysScheduleJob);
            //sysScheduleJobService.pause(sysScheduleJob.getId());
            sysScheduleJobService.deleteOnlyJob(sysScheduleJob.getId());
            if(sysScheduleJob.getJobStatus()==1) {
                sysScheduleJobService.addJob(sysScheduleJob);
            }else if(sysScheduleJob.getJobStatus()==2){
                sysScheduleJobService.addJob(sysScheduleJob);
                sysScheduleJobService.pause(sysScheduleJob.getId());
            }
        }else{
            if(sysScheduleJob.getJobStatus()==2){
                sysScheduleJobService.add(sysScheduleJob);
                sysScheduleJobService.pause(sysScheduleJob.getId());
            }else if(sysScheduleJob.getJobStatus()==1) {
                sysScheduleJobService.add(sysScheduleJob);
            }
        }
        return DataRes.success("");
    }


    /**
     * 跳转到提醒用户页面
     * @param jobId
     * @param request
     * @return
     */
    @RequestMapping("job/gotoJobUserList")
    @RequiresPermissions("job/save")
    public String gotoJobUserList(Integer jobId,HttpServletRequest request){
        request.setAttribute("jobId",jobId);
        return "sys/sys_schedule_job_user_list";
    }


    /**
     * 分页获取任务制定的用户
     * @param sysScheduleJobUser
     * @param request
     * @return
     */
    @RequestMapping("job/selectJobUserAll")
    @ResponseBody
    public DataRes selectJobUserAll(SysScheduleJobUser sysScheduleJobUser,HttpServletRequest request){
        return DataRes.success(sysScheduleJobUserService.selectAllByPaging(sysScheduleJobUser));
    }


    /**
     * 删除用户
     * @param sysScheduleJobUser
     * @return
     */
    @RequestMapping("job/deleteJobUser")
    @ResponseBody
    public DataRes deleteJobUser(SysScheduleJobUser sysScheduleJobUser){
        return DataRes.success(sysScheduleJobUserService.deleteByPrimaryKey(sysScheduleJobUser));
    }

    @RequestMapping("job/addJobUser")
    @ResponseBody
    public DataRes addJobUser(String userId,Integer jobId){
        if(userId!=null) {
            String[] id=userId.split(",");
            if(id.length>0) {
                for(String s:id) {
                    SysScheduleJobUser sysScheduleJobUser = new SysScheduleJobUser();
                    sysScheduleJobUser.setUserId(Integer.parseInt(s));
                    sysScheduleJobUser.setJobId(jobId);
                    List<SysScheduleJobUser> list = sysScheduleJobUserService.selectAll(sysScheduleJobUser);
                    if (list.size() > 0) {

                    } else {
                        sysScheduleJobUserService.insert(sysScheduleJobUser);
                    }
                }
            }
        }
        return DataRes.success("");
    }

    /**
     * 跳转到管理员列表
     * @return
     */
    @RequestMapping("job/gotoSelectUserList")
    public String gotoSelectUserList(){
        return "sys/sys_schedule_select_user_list";
    }

    /**
     * 分页查询管理员
     * @param sysUser
     * @return
     */
    @RequestMapping("job/selectUser")
    @ResponseBody
    public DataRes selectUser(SysUser sysUser){
        return DataRes.success(sysUserService.selectAllByPaging(sysUser));
    }
}
