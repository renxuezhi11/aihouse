package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import javax.annotation.Resource;

import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.VillageService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.Village;


/**
 *小区表 controller
 */
@Controller
public class SysVillageController  {

	/**
	 * 二手房
	 */
	@Resource
	private SecondHandHouseService secondHandHouseService;



	@Resource
	private VillageService villageService;

	@Resource
	private AreaService areaService;
	/**
	 * 删除-小区表
	 */
	@ResponseBody
	@RequestMapping("village/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(Village village, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(villageService.deleteByPrimaryKey(village));
	}


	/**
	 * 跳转到小区管理界面
	 * @return
	 */
	@RequestMapping("village/gotoList")
	@RequiresPermissions("village/gotoList")
	public String gotoList(){
		return "sys/sys_village_list";
	}

	/**
	 * 跳转到编辑页面
	 * @param village
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("village/gotoEdit")
	@RequiresPermissions("village/save")
	public String gotoEdit(Village village,HttpServletRequest request,HttpServletResponse response){
		if(village.getId()!=null){
			village=villageService.selectByPrimaryKey(village);
			request.setAttribute("vill",village);
			Area area=new Area();
			area.setParentId(0);
			area.setOrderByString("order by sort asc");
			request.setAttribute("provice", areaService.queryByCondition(area));
			area=new Area();
			area.setParentId(village.getProvinceid());
			request.setAttribute("city", areaService.queryByCondition(area));
			area.setParentId(village.getCityid());
			request.setAttribute("area", areaService.queryByCondition(area));
			area.setParentId(village.getAreaid());
			request.setAttribute("strees", areaService.queryByCondition(area));
		}else{
			request.setAttribute("vill",village);
			Area area=new Area();
			area.setParentId(0);
			area.setOrderByString("order by sort asc");
			request.setAttribute("provice",areaService.queryByCondition(area));
		}
		return "sys/sys_village_detail";
	}

	/**
	 *  保存 (主键为空则增加 否则 修改)-> 小区表
	 */
	@ResponseBody
	@RequestMapping("village/save")
	@RequiresPermissions("village/save")
	public DataRes save(Village village,HttpServletRequest request,HttpServletResponse response){
		if(village.getId()==null){
			return DataRes.success(villageService.insert(village));
		}
		return DataRes.success(villageService.update(village));

	}

	/**
	 * 获取区域列表
	 * @param area
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("village/getAreaList")
	@ResponseBody
	public DataRes getAreaList(Area area,HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(areaService.queryByCondition(area));
	}

	/**
	 * 根据主键查询->小区表
	 */
	@ResponseBody
	@RequestMapping("village/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.selectByPrimaryKey(village));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->小区表
	 */
	@ResponseBody
	@RequestMapping("village/selectByCondition")
	public DataRes selectByCondition(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.selectByCondition(village));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->小区表
	 */
	@ResponseBody
	@RequestMapping("village/selectAllByPaging")
	public DataRes selectAllByPaging(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.selectAllByPaging(village));
	}


	/**
	 * 导出报表->小区表
	 */
	@RequestMapping("village/export")
	public void export(Village village,HttpServletRequest request,HttpServletResponse response){
		List<Village> list= villageService.selectAll(village);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("villageName","小区名称");
		header.put("cityid", "城市id，关联区域表");
		header.put("areaid", "区域id，关联区域表");
		header.put("streesid", "街道片区，关联区域表");
		header.put("address", "项目地址");
		header.put("provinceid", "省份");
		header.put("floorSpace", "占地面积单位m2");
		header.put("coveredArea", "建筑面积单位m2");
		header.put("plotRatio", "容积率");
		header.put("greeningRate", "绿化率");
		header.put("carPlaceUp", "停车位地上");
		header.put("carPlaceDown", "停车位地下");
		header.put("building", "楼栋数");
		header.put("totalHouse", "总户数");
		header.put("propertyCompany", "物业公司");
		header.put("propertyFee", "物业费");
		header.put("propertyYear", "产权年限");
		header.put("developer", "开发商");
		header.put("createtime_", "添加时间");
		header.put("coveredType", "建筑类型");
		ExcelUtils.exportExcel("小区表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	@RequestMapping("village/selectVillageAndSecondHandHouse")
	@ResponseBody
	public DataRes selectVillageAndSecondHandHouse(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.selectVillageAndSecondHandHouse(village));
	}


	/**
	 * 级联条件查询 小区--二手房
	 */
	@RequestMapping("village/selectVillageAndSecondHandHouseByCondition")
	@ResponseBody
	public DataRes selectVillageAndSecondHandHouseByCondition(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.selectVillageAndSecondHandHouseByCondition(village));
	}


	/**
	 * 级联删除(根据主键删除) 小区--二手房
	 */
	@RequestMapping("village/deleteVillageAndSecondHandHouse")
	@ResponseBody
	public DataRes deleteVillageAndSecondHandHouse(Village village,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(villageService.deleteVillageAndSecondHandHouse(village));
	}



}
