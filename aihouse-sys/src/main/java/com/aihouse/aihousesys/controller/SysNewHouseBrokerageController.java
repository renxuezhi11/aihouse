package com.aihouse.aihousesys.controller;
import javax.annotation.Resource;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.SysNewHouseBrokerage;
import com.aihouse.aihouseservice.SysNewHouseBrokerageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;

import static com.aihouse.aihousecore.utils.ResponseCode.FAIL;


/**
 *楼盘佣金表 controller
 */
@Controller
public class SysNewHouseBrokerageController  {


	@Resource
	private SysNewHouseBrokerageService sysNewHouseBrokerageService;

	/*
	* 跳入佣金列表
	* */
	@RequestMapping("sysNewHouseBrokerage/gotoList")
	public String gotoList(SysNewHouseBrokerage sysNewHouseBrokerage, HttpServletRequest request, HttpServletResponse response){
		if(null==sysNewHouseBrokerage){
			try {
				throw new Exception("没有楼盘");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			sysNewHouseBrokerage.setNewHouseId(sysNewHouseBrokerage.getId());
			sysNewHouseBrokerage.setId(null);
			request.setAttribute("sys_newhouse_brokerage",sysNewHouseBrokerage);
		}
		return "sys/sys_newhouse_brokerage_list";
	}

	@RequestMapping("sysNewHouseBrokerage/gotoDetail")
	@RequiresPermissions("sysNewHouseBrokerage/save")
	public String gotoDetail(SysNewHouseBrokerage sysNewHouseBrokerage, HttpServletRequest request, HttpServletResponse response) {
		if (null == sysNewHouseBrokerage.getId()) {
			request.setAttribute("sys_newhouse_brokerage", sysNewHouseBrokerage);
		}else{
			request.setAttribute("sys_newhouse_brokerage", sysNewHouseBrokerageService.selectByPrimaryKey(sysNewHouseBrokerage));
		}
		return "sys/sys_newhouse_brokerage_detail";
	}

	/**
	 * 删除-楼盘佣金表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseBrokerage/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(SysNewHouseBrokerage sysNewHouseBrokerage, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(sysNewHouseBrokerageService.deleteByPrimaryKey(sysNewHouseBrokerage));
	}


	/**
	 *  保存 (主键为空则增加 否则 修改)-> 楼盘佣金表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseBrokerage/save")
	public DataRes save(SysNewHouseBrokerage sysNewHouseBrokerage,HttpServletRequest request,HttpServletResponse response){
		if(sysNewHouseBrokerage.getId()==null){
			return DataRes.success(sysNewHouseBrokerageService.insert(sysNewHouseBrokerage));
		}
		return DataRes.success(sysNewHouseBrokerageService.update(sysNewHouseBrokerage));

	}


	/**
	 * 根据主键查询->楼盘佣金表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseBrokerage/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(SysNewHouseBrokerage sysNewHouseBrokerage,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(sysNewHouseBrokerageService.selectByPrimaryKey(sysNewHouseBrokerage));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->楼盘佣金表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseBrokerage/selectByCondition")
	public DataRes selectByCondition(SysNewHouseBrokerage sysNewHouseBrokerage,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(sysNewHouseBrokerageService.selectByCondition(sysNewHouseBrokerage));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->楼盘佣金表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseBrokerage/selectAllByPaging")
	public DataRes selectAllByPaging(SysNewHouseBrokerage sysNewHouseBrokerage,HttpServletRequest request,HttpServletResponse response,@RequestParam("newHouseId")Integer newHouseId){
		if(newHouseId==null){
			return DataRes.error(FAIL);
		}else{
			sysNewHouseBrokerage.setNewHouseId(newHouseId);
		}
		return DataRes.success(sysNewHouseBrokerageService.selectAllByPaging(sysNewHouseBrokerage));
	}


	/**
	 * 导出报表->楼盘佣金表
	 */
	@RequestMapping("sysNewHouseBrokerage/export")
	public void export(SysNewHouseBrokerage sysNewHouseBrokerage,HttpServletRequest request,HttpServletResponse response){
		List<SysNewHouseBrokerage> list= sysNewHouseBrokerageService.selectAll(sysNewHouseBrokerage);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "主键");
		header.put("newHouseId", "关联楼盘表id");
		header.put("type", "业务类型（1.代理佣金2.）");
		header.put("brokerageType", "佣金类型（1.结佣2现佣）");
		header.put("brokerageModel", "佣金模式1.固定佣金2.固定金额");
		header.put("brokerageMoney", "佣金金额");
		header.put("brokerageScale", "佣金点数");
		header.put("remark", "备注");
		header.put("isDel", "删除状态0正常1删除");
		ExcelUtils.exportExcel("楼盘佣金表",header,list,response,request);

	}


}
