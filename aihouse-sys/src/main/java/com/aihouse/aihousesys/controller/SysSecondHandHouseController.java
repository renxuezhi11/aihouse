package com.aihouse.aihousesys.controller;

import javax.annotation.Resource;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.VillageService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.SecondHandHouse;


/**
 *二手房表 controller
 */
@Controller
public class SysSecondHandHouseController  {

	/**
	 * 二手房图片
	 */
	@Resource
	private SecondHandHouseImgService secondHandHouseImgService;

	@Resource
	private SecondHandHouseService secondHandHouseService;

	@Resource
	private SecondHouseSearchService secondHouseSearchService;


	/*
	* 跳入二手房列表页
	* */
	@RequestMapping("secondHandHouse/gotoList")
	public String gotoList(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return"sys/sys_secondhand_house_list";
	}

	/*
	 * 跳入二手房详情页
	 * */
	@RequestMapping("secondHandHouse/gotoDetail")
	public String gotoDetail(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		if(null==secondHandHouse.getId()){
			request.setAttribute("sys_secondhand_house",secondHandHouse);
		}else{
			request.setAttribute("sys_secondhand_house",secondHandHouseService.selectByPrimaryKey(secondHandHouse));
			SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
			secondHandHouseImg.setSecondHouse(secondHandHouse.getId());
			request.setAttribute("imgList",secondHandHouseImgService.selectAll(secondHandHouseImg));
		}
		return "sys/sys_secondhand_house_Detail";
	}


	/**
	 * 删除-二手房表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouse/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.deleteByPrimaryKey(secondHandHouse));
	}


	/**
	 *  保存 (主键为空则增加 否则 修改)-> 二手房表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouse/save")
	public DataRes save(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		if(secondHandHouse.getId()==null){
			return DataRes.success(secondHandHouseService.insert(secondHandHouse));
		}
		//查询旧数据
		SecondHandHouse secondHandHouse1=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
		int i=secondHandHouseService.updateSecondHouse(secondHandHouse);
		if(secondHandHouse.getFlag()!=null){
			if(secondHandHouse.getFlag()==1){
				secondHouseSearchService.addSecondHouseIndex(secondHandHouse.getId());
			}else{
				secondHouseSearchService.deleteSecondHouseIndex(secondHandHouse.getId());
			}
		}
		if(secondHandHouse.getStatus()!=null) {
			if (secondHandHouse1.getStatus() == 0&&secondHandHouse.getStatus()!=secondHandHouse1.getStatus()) {
				//更改了审核状态，已审核，异步短信通知，1：通过2不通过
				secondHandHouseService.sendSms(secondHandHouse.getStatus(),secondHandHouse1.getTelephone(),secondHandHouse.getStatusContent());
			}
		}
		if(secondHandHouse.getIsTop()!=null){
			secondHandHouse1=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
			if(secondHandHouse1.getFlag()==1) {
				secondHouseSearchService.addSecondHouseIndex(secondHandHouse.getId());
			}
		}
		if(secondHandHouse1.getIsSale()!=secondHandHouse.getIsSale() && secondHandHouse1.getFlag()==1){
			secondHouseSearchService.deleteSecondHouseIndex(secondHandHouse.getId());
			secondHouseSearchService.addSecondHouseIndex(secondHandHouse.getId());
		}
		return DataRes.success(i);
	}


	/**
	 * 根据主键查询->二手房表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouse/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectByPrimaryKey(secondHandHouse));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->二手房表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouse/selectByCondition")
	public DataRes selectByCondition(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectByCondition(secondHandHouse));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->二手房表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouse/selectAllByPaging")
	public DataRes selectAllByPaging(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectAllByPaging(secondHandHouse));
	}


	/**
	 * 导出报表->二手房表
	 */
	@RequestMapping("secondHandHouse/export")
	public void export(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		List<SecondHandHouse> list= secondHandHouseService.selectAll(secondHandHouse);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("feature", "房源特色");
		header.put("title", "标题");
		header.put("villageId", "小区id");
		header.put("villageName", "小区名称");
		header.put("address", "地址");
		header.put("lng", "经度");
		header.put("lat", "纬度");
		header.put("floor", "楼层");
		header.put("totalFloor", "总楼层");
		header.put("coveredType", "建筑类型");
		header.put("coveredArea", "建筑面积(m2)");
		header.put("price", "售价(万元)");
		header.put("userId", "用户id");
		header.put("orientations", "朝向(东，南，西，北，东北，东南，西南，西北，东西，南北)");
		header.put("isLift", "是否有电梯0有1无");
		header.put("room", "室");
		header.put("hall", "厅");
		header.put("cookroom", "厨房");
		header.put("toilet", "卫生间");
		header.put("gallery", "阳台");
		header.put("cityid", "城市id，关联区域表");
		header.put("areaid", "区域id，关联区域表");
		header.put("streesid", "街道片区，关联区域表");
		header.put("fixture", "装修情况(毛坯，精装修，简装修，中装修，豪华装修)");
		header.put("age", "房龄");
		header.put("houseDesc", "房源描述");
		header.put("ownerMentality", "业主心态");
		header.put("sellPoint", "卖点");
		header.put("houseNumber", "房号");
		header.put("createtime_", "发布时间");
		header.put("updatetime_", "更新时间");
		header.put("status", "状态（0未审核，1审核通过，2审核不通过）");
		header.put("houseType", "产品类型");
		header.put("contacts", "联系人");
		header.put("sex", "0男1女");
		header.put("userType", "0业主1经纪人");
		header.put("telephone", "手机号");
		header.put("signName", "签名");
		header.put("broker", "经纪人（关联用户表id）");
		header.put("houseReal", "是否实勘(0未1已)");
		header.put("statusContent", "审核回复");
		header.put("flag", "上架状态0未上架1上架2下架");
		header.put("isDel", "删除状态0正常1删除");
		ExcelUtils.exportExcel("二手房表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) 小区--二手房
	 */
	@RequestMapping("secondHandHouse/selectVillageAndSecondHandHouse")
	@ResponseBody
	public DataRes selectVillageAndSecondHandHouse(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectVillageAndSecondHandHouse(secondHandHouse));
	}


	/**
	 * 级联条件查询 小区--二手房
	 */
	@RequestMapping("secondHandHouse/selectVillageAndSecondHandHouseByCondition")
	@ResponseBody
	public DataRes selectVillageAndSecondHandHouseByCondition(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectVillageAndSecondHandHouseByCondition(secondHandHouse));
	}


	/**
	 * 级联删除(根据主键删除) 小区--二手房
	 */
	@RequestMapping("secondHandHouse/deleteVillageAndSecondHandHouse")
	@ResponseBody
	public DataRes deleteVillageAndSecondHandHouse(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.deleteVillageAndSecondHandHouse(secondHandHouse));
	}



	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouse/selectSecondHandHouseAndSecondHandHouseImg")
	@ResponseBody
	public DataRes selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectSecondHandHouseAndSecondHandHouseImg(secondHandHouse));
	}


	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouse/selectSecondHandHouseAndSecondHandHouseImgByCondition")
	@ResponseBody
	public DataRes selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.selectSecondHandHouseAndSecondHandHouseImgByCondition(secondHandHouse));
	}


	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouse/deleteSecondHandHouseAndSecondHandHouseImg")
	@ResponseBody
	public DataRes deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouse secondHandHouse,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseService.deleteSecondHandHouseAndSecondHandHouseImg(secondHandHouse));
	}



}
