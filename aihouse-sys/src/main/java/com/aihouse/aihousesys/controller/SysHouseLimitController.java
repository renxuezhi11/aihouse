package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.HouseLimit;
import com.aihouse.aihousedao.bean.SysMarket;
import com.aihouse.aihouseservice.HouseLimitService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SysHouseLimitController {

    @Resource
    private HouseLimitService houseLimitService;

    /**
     * 跳转页面
     * @param request
     * @return
     */
    @RequestMapping("houseLimit/gotoList")
    @RequiresPermissions("houseLimit/gotoList")
    public String gotoAppList(HttpServletRequest request){
        HouseLimit houseLimit=new HouseLimit();
        List<HouseLimit> limitList=houseLimitService.selectAll(houseLimit);
        if(limitList.size()>0){
            request.setAttribute("houseLimit",limitList.get(0));
        }else{
            request.setAttribute("houseLimit",houseLimit);
        }
        return "sys/sys_house_limit";
    }

    /**
     * save
     * @param houseLimit
     * @return
     */
    @ResponseBody
    @RequestMapping("houseLimit/save")
    public DataRes save(HouseLimit houseLimit){
        if(houseLimit.getId()==null){
            return DataRes.success(houseLimitService.insert(houseLimit));
        }
        return DataRes.success(houseLimitService.update(houseLimit));

    }
}
