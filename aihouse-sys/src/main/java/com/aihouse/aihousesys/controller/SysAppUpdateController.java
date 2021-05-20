package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.AppUpdate;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihouseservice.AppUpdateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SysAppUpdateController {
    @Resource
    private AppUpdateService appUpdateService;


    /**
     * 跳转到app更新列表
     * @return
     */
    @RequestMapping("app/gotoList")
    @RequiresPermissions("app/gotoList")
    public String gotoAppList(){
        return "sys/sys_app_list";
    }

    /**
     * 查询分页
     * @param appUpdate
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/selectAllByPaging")
    @RequiresPermissions("app/selectAll")
    @ResponseBody
    public DataRes selectAll(AppUpdate appUpdate, HttpServletRequest request, HttpServletResponse response)throws Exception{
        appUpdate.setOrderByString(" order by createtime desc ");
        return DataRes.success(appUpdateService.selectAllByPaging(appUpdate));
    }

    /**
     * 跳转到更细详情页
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("app/gotoDetail")
    @RequiresPermissions("app/save")
    public String gotoDetail(HttpServletRequest request, HttpServletResponse response,Integer id){
        if(id!=null){
            try {
                AppUpdate appUpdate1=new AppUpdate();
                appUpdate1.setId(id);
                appUpdate1 = appUpdateService.selectByPrimaryKey(appUpdate1);
                request.setAttribute("appUpdate",appUpdate1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            AppUpdate appUpdate1=new AppUpdate();
            request.setAttribute("appUpdate",appUpdate1);
        }
        return "sys/sys_app_detail";
    }

    /**
     * 保存 如果id存在则修改否则新增
     * @param appUpdate
     * @return
     */
    @RequestMapping("app/save")
    @RequiresPermissions("app/save")
    @ResponseBody
    public DataRes save(AppUpdate appUpdate, HttpServletRequest request, HttpServletResponse response) {
        if(appUpdate.getId()==null){
            try {
                return DataRes.success(appUpdateService.insert(appUpdate));
            }catch (Exception e){
                e.printStackTrace();
                return  DataRes.error();
            }
        }
        try {
            return DataRes.success(appUpdateService.update(appUpdate));
        }catch (Exception e){
            e.printStackTrace();
            return DataRes.error();
        }
    }
}
