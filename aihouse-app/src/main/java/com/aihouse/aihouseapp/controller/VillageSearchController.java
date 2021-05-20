package com.aihouse.aihouseapp.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Village;
import com.aihouse.aihouseservice.VillageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 小区搜索controller
 */
@CrossOrigin
@RestController
public class VillageSearchController {

    @Resource
    private VillageService villageService;

    /**
     * app搜索小区
     * @param cityId
     * @param keyword
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "app/searchVillage",method = RequestMethod.POST)
    public DataRes searchVillage(@RequestParam("cityId")Integer cityId, @RequestParam("keyword")String keyword, HttpServletRequest request, HttpServletResponse response){
        if(cityId!=null&&keyword!=null){
//            keyword="%"+keyword+"%";
            Village village=new Village();
            village.setCityid(cityId);
            village.setVillageName(keyword);
            List<Map<String,Object>> list=villageService.selectAllByName(village);
            return DataRes.success(list);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
