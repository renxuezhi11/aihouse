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
public class JointRentIndexController {

    @Resource
    private AppConditionListService appConditionListService;
    /**
     * app合租首页合租筛选条件返回集接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/jointrent/conditionList")
    public DataRes conditionList(HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(appConditionListService.joinRent(441300));
    }

}
