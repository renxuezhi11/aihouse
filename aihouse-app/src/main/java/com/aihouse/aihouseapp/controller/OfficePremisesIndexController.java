package com.aihouse.aihouseapp.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihouseservice.AppConditionListService;
import com.aihouse.aihouseservice.AreaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class OfficePremisesIndexController {

    @Resource
    private AppConditionListService appConditionListService;
    /**
     * app写字楼首页条件筛选列表（根据经纬度查询附近的房源，按照距离排序）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/officepremises/conditionList")
    public DataRes conditionList(HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(appConditionListService.officepremises(441300));
    }

}
