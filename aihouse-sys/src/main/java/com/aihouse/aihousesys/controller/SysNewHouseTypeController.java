package com.aihouse.aihousesys.controller;
import javax.annotation.Resource;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.SysNewHouseType;
import com.aihouse.aihouseservice.SysNewHouseTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import static com.aihouse.aihousecore.utils.ResponseCode.FAIL;


/**
 *楼盘户型表 controller
 */
@Controller
public class SysNewHouseTypeController {

	@Resource
	private SysNewHouseTypeService sysNewHouseTypeService;

	@Value("${auto.code.filePath}")
	private String filePath;


	@RequestMapping("sysNewHouseType/gotoList")
	public String gotoList(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		if(null==sysNewHouseType){
			try {
				throw new Exception("没有楼盘");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			sysNewHouseType.setNewHouseId(sysNewHouseType.getId());
			sysNewHouseType.setId(null);
			request.setAttribute("sys_newhouse_type",sysNewHouseType);
		}

		return "sys/sys_newhouse_type_list";
	}

	@RequestMapping("sysNewHouseType/gotoDetail")
	@RequiresPermissions("sysNewHouseType/save")
	public String gotoDetail(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		if (null == sysNewHouseType.getId()) {
			request.setAttribute("sys_newhouse_type", sysNewHouseType);
		}else{
			request.setAttribute("sys_newhouse_type", sysNewHouseTypeService.selectByPrimaryKey(sysNewHouseType));
		}
		return "sys/sys_newhouse_type_detail";
	}

	/**
	 * 删除-楼盘户型表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseType/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		return DataRes.success(sysNewHouseTypeService.deleteByPrimaryKey(sysNewHouseType));
	}


	/**
	 * 保存 (主键为空则增加 否则 修改)-> 楼盘户型表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseType/save")
	public DataRes save(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		if (sysNewHouseType.getId() == null) {
			return DataRes.success(sysNewHouseTypeService.insert(sysNewHouseType));
		}
		return DataRes.success(sysNewHouseTypeService.update(sysNewHouseType));

	}


	/**
	 * 根据主键查询->楼盘户型表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseType/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		return DataRes.success(sysNewHouseTypeService.selectByPrimaryKey(sysNewHouseType));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->楼盘户型表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseType/selectByCondition")
	public DataRes selectByCondition(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		return DataRes.success(sysNewHouseTypeService.selectByCondition(sysNewHouseType));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->楼盘户型表
	 */
	@ResponseBody
	@RequestMapping("sysNewHouseType/selectAllByPaging")
	public DataRes selectAllByPaging(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response,@RequestParam("newHouseId")Integer newHouseId) {
	    if(newHouseId==null){
	        return DataRes.error(FAIL);
        }else{
            sysNewHouseType.setNewHouseId(newHouseId);
        }
		return DataRes.success(sysNewHouseTypeService.selectAllByPaging(sysNewHouseType));
	}


	/**
	 * 导出报表->楼盘户型表
	 */
	@RequestMapping("sysNewHouseType/export")
	public void export(SysNewHouseType sysNewHouseType, HttpServletRequest request, HttpServletResponse response) {
		List<SysNewHouseType> list = sysNewHouseTypeService.selectAll(sysNewHouseType);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "主键");
		header.put("newHouseId", "关联楼盘表id");
		header.put("typeName", "户型名称");
		header.put("coveredArea", "建筑面积（m2）");
		header.put("averagePrice", "户型均价(元)");
		header.put("totalPrice", "户型总价(万元)");
		header.put("spread", "户型分布");
		header.put("sellStage", "销售进度(0在售，1待售，2售罄)");
		header.put("feature", "户型特色");
		header.put("typeImg", "户型图路径");
		header.put("houseType", "户型");
		header.put("createtime_", "上传时间");
		header.put("isDel", "状态0正常1删除");
		ExcelUtils.exportExcel("楼盘户型表", header, list, response, request);

	}

	/**
	 * 上传图片
	 *
	 * @param uploadfile
	 * @return
	 */
	@RequestMapping("sysNewHouseType/upload")
	@ResponseBody
	@RequiresPermissions("sysNewHouseType/save")
	public DataRes upload(@RequestParam("file") MultipartFile uploadfile) throws IOException {
		/*// 获得文件：
		// 获得文件名：
		String filename = uploadfile.getOriginalFilename();
		// 获得输入流：
		InputStream input = uploadfile.getInputStream();
		File p = new File(filePath + DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/"));
		if (!p.exists()) {
			p.mkdirs();
		}
		File file = new File(p, DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
		uploadfile.transferTo(file);*/

		//		return DataRes.success(DateUtils.formatDateByPattern(new Date(), "yyyy/MM/dd/") + DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
		OSSClientUtil ossClientUtil=new OSSClientUtil();
		try {
			String name=ossClientUtil.uploadImg2Oss(uploadfile);
			return DataRes.success(name);
		}catch (Exception e){
			System.out.println("上传失败");
			return DataRes.error();
		}
	}

	/**
	 * 查看图片
	 *
	 * @return
	 */
	@RequestMapping("fileImages/watch")
	public void watch(String path, HttpServletResponse response) throws IOException {
		File file = new File(filePath + path);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			int len;
			byte[] buff = new byte[1204];
			while ((len = fileInputStream.read(buff)) != -1) {
				response.getOutputStream().write(buff, 0, len);
			}
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

}