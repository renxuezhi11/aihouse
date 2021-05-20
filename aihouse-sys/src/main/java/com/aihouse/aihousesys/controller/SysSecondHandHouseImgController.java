package com.aihouse.aihousesys.controller;


import javax.annotation.Resource;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;


/**
 *二手房图片表 controller
 */
//@Controller
public class SysSecondHandHouseImgController  {

	/**
	 * 二手房源详细信息
	 */
	@Resource
	private SecondHandHouseService secondHandHouseService;



	@Resource
	private SecondHandHouseImgService secondHandHouseImgService;
	/**
	 * 删除-二手房图片表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouseImg/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(SecondHandHouseImg secondHandHouseImg, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.deleteByPrimaryKey(secondHandHouseImg));
	}


	/**
	 *  保存 (主键为空则增加 否则 修改)-> 二手房图片表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouseImg/save")
	public DataRes save(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		if(secondHandHouseImg.getId()==null){
			return DataRes.success(secondHandHouseImgService.insert(secondHandHouseImg));
		}
		return DataRes.success(secondHandHouseImgService.update(secondHandHouseImg));

	}


	/**
	 * 根据主键查询->二手房图片表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouseImg/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.selectByPrimaryKey(secondHandHouseImg));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空��忽略该字段)->二手房图片表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouseImg/selectByCondition")
	public DataRes selectByCondition(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.selectByCondition(secondHandHouseImg));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->二手房图片表
	 */
	@ResponseBody
	@RequestMapping("secondHandHouseImg/selectAllByPaging")
	public DataRes selectAllByPaging(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.selectAllByPaging(secondHandHouseImg));
	}


	/**
	 * 导出报表->二手房图片表
	 */
	@RequestMapping("secondHandHouseImg/export")
	public void export(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		List<SecondHandHouseImg> list= secondHandHouseImgService.selectAll(secondHandHouseImg);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("imgType", "图片类型（1室内图，2交通图，3实景图，4户型图）");
		header.put("imgUrl", "图片路径");
		header.put("imgDesc", "图片描述");
		header.put("secondHouse", "关联二手房表id");
		header.put("createtime_", "上传时间");
		header.put("isDel", "删除状态0正常1删除");
		ExcelUtils.exportExcel("二手房图片表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouseImg/selectSecondHandHouseAndSecondHandHouseImg")
	@ResponseBody
	public DataRes selectSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.selectSecondHandHouseAndSecondHandHouseImg(secondHandHouseImg));
	}


	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouseImg/selectSecondHandHouseAndSecondHandHouseImgByCondition")
	@ResponseBody
	public DataRes selectSecondHandHouseAndSecondHandHouseImgByCondition(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.selectSecondHandHouseAndSecondHandHouseImgByCondition(secondHandHouseImg));
	}


	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("secondHandHouseImg/deleteSecondHandHouseAndSecondHandHouseImg")
	@ResponseBody
	public DataRes deleteSecondHandHouseAndSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(secondHandHouseImgService.deleteSecondHandHouseAndSecondHandHouseImg(secondHandHouseImg));
	}

	/**
	 * 删除图片
	 * @param secondHandHouseImg
	 * @return
	 */
	@RequestMapping("secondHandHouseImg/delete")
	@ResponseBody
	public DataRes deleteSecondHandHouseImg(SecondHandHouseImg secondHandHouseImg){
		SecondHandHouseImg secondHandHouseImg1=secondHandHouseImgService.selectByPrimaryKey(secondHandHouseImg);
		OSSClientUtil ossClientUtil=new OSSClientUtil();
		ossClientUtil.deleteFile(secondHandHouseImg1.getImgUrl());
		return DataRes.success(secondHandHouseImgService.deleteByPrimaryKey(secondHandHouseImg));
	}


}
