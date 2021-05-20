package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihouseservice.SysIndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private SysIndexService sysIndexService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/index/gotoWelcome")
    public String gotoWelcome(HttpServletRequest request){
        request.setAttribute("map",sysIndexService.getTongjiInfo());
        return "welcome";
    }

    @RequestMapping("/403")
    public String goto403(){
        return "403";
    }

    @RequestMapping("/index/getNewUserOfWeek")
    @ResponseBody
    public DataRes  getNewUserOfWeek(){
        List<Map<String,Object>> list=sysIndexService.getNewUserOfWeek();
        Map<String,Object> result=new HashMap<>();
        List<String> listX=new ArrayList<>();
        List<String> listY=new ArrayList<>();
        if(list!=null){
            for(Map map:list){
                listX.add(map.get("time").toString());
                listY.add(map.get("cnt").toString());
            }
        }
        result.put("x",listX);
        result.put("y",listY);
        return DataRes.success(result);
    }

    @RequestMapping("/index/getActiveUserOfWeek")
    @ResponseBody
    public DataRes  getActiveUserOfWeek(){
        List<Map<String,Object>> list=sysIndexService.getActiveUserOfWeek();
        Map<String,Object> result=new HashMap<>();
        List<String> listX=new ArrayList<>();
        List<String> listY=new ArrayList<>();
        if(list!=null){
            for(Map map:list){
                listX.add(map.get("time").toString());
                listY.add(map.get("cnt").toString());
            }
        }
        result.put("x",listX);
        result.put("y",listY);
        return DataRes.success(result);
    }
}
