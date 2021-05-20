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
public class EntireTenancyIndexController {

    @Resource
    private AppConditionListService appConditionListService;

    /**
     * 获取整租查询条件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/entiretenancy/conditionList")
    public DataRes conditionList(HttpServletRequest request, HttpServletResponse response) {
        return DataRes.success(appConditionListService.entireTenancyIndex(441300));
    }

}
