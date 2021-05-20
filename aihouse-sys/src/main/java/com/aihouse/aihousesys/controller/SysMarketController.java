package com.aihouse.aihousesys.controller;
import javax.annotation.Resource;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihouseservice.SysMarketService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.SysMarket;

/**
 *行情表 controller
 */
@Controller
public class SysMarketController  {


	@Resource
	private SysMarketService sysMarketService;

	/**
	 * 设置页
	 */
	@RequestMapping("sysMarket/gotoPage")
	public  String gotoPage(SysMarket sysMarket, HttpServletRequest request, HttpServletResponse response){
		List<SysMarket> list = sysMarketService.selectAll(sysMarket);
		if(list.size()>0){
			request.setAttribute("sys_market",list.get(0));
		}else{
			request.setAttribute("sys_market",sysMarket);
		}
		return "sys/sys_marker_detail";
	}

	/**
	 * 删除-行情表
	 */
	@ResponseBody
	@RequestMapping("sysMarket/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(SysMarket sysMarket, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysMarketService.deleteByPrimaryKey(sysMarket));
	}


	/**
	 *  保存 (主键为空则增加 否则 修改)-> 行情表
	 */
	@ResponseBody
	@RequestMapping("sysMarket/save")
	public DataRes save(SysMarket sysMarket,HttpServletRequest request,HttpServletResponse response){
		if(sysMarket.getId()==null){
			return DataRes.success(sysMarketService.insert(sysMarket));
		}
		return DataRes.success(sysMarketService.update(sysMarket));

	}


	/**
	 * 根据主键查询->行情表
	 */
	@ResponseBody
	@RequestMapping("sysMarket/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(SysMarket sysMarket,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(sysMarketService.selectByPrimaryKey(sysMarket));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->行情表
	 */
	@ResponseBody
	@RequestMapping("sysMarket/selectByCondition")
	public DataRes selectByCondition(SysMarket sysMarket,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(sysMarketService.selectByCondition(sysMarket));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->行情表
	 */
	@ResponseBody
	@RequestMapping("sysMarket/selectAllByPaging")
	public DataRes selectAllByPaging(SysMarket sysMarket,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(sysMarketService.selectAllByPaging(sysMarket));
	}


	/**
	 * 导出报表->行情表
	 */
	@RequestMapping("sysMarket/export")
	public void export(SysMarket sysMarket,HttpServletRequest request,HttpServletResponse response){
		List<SysMarket> list= sysMarketService.selectAll(sysMarket);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("averagePrice", "均价");
		header.put("priceJump", "上涨");
		header.put("cityID", "城市id");
		header.put("createTime_", "添加时间");
		ExcelUtils.exportExcel("行情表",header,list,response,request);
	}
}
