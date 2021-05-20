package com.aihouse.aihouseapp.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.SysMarket;
import com.aihouse.aihouseservice.SysMarketService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class MarketController {
    @Resource
    private SysMarketService sysMarketService;

    /**
     * 获取市场行情
     * @return
     */
    @RequestMapping(value = "app/getMarket",method = RequestMethod.POST)
    public DataRes getmarket(){
        SysMarket sysMarket=new SysMarket();
        return DataRes.success(sysMarketService.selectAll(sysMarket).get(0));
    }
}
