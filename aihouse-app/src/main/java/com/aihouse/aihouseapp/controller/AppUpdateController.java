package com.aihouse.aihouseapp.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.AppUpdate;
import com.aihouse.aihouseservice.AppUpdateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AppUpdateController {

    @Resource
    private AppUpdateService appUpdateService;


    /**
     * 检查更新app
     * @param appid
     * @param version
     * @param request
     * @return
     */
    @RequestMapping(value = "app/checkUpdate",method = RequestMethod.POST)
    public DataRes checkUpdate(String appid, String version, HttpServletRequest request){
        if(appid!=null&&version!=null){
            AppUpdate appUpdate=new AppUpdate();
            appUpdate.setAppid(appid);
            appUpdate.setOrderByString(" order by createtime desc ");
            List<AppUpdate> list=appUpdateService.selectAll(appUpdate);
            if(list!=null&&list.size()>0){
                appUpdate=list.get(0);
                if(appUpdate.getVersion().equals(version)){
                    return DataRes.error(ResponseCode.UPDATE_ERROR);
                }else{
                    return DataRes.success(appUpdate);
                }
            }else{
                return DataRes.error(ResponseCode.UPDATE_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
