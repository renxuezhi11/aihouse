package com.aihouse.aihousesys.controller.users;


import javax.annotation.Resource;

import IM.IMUtils;
import com.aihouse.aihousecore.utils.*;
import com.aihouse.aihouseservice.users.*;
import com.aihouse.aihousesys.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
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
import java.util.*;

import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.Users;
import org.springframework.web.multipart.MultipartFile;


/**
 *用户表 controller
 */
@Controller
public class SysUsersController  {

	@Value("${auto.code.filePath}")
	private String filePath;

	@Value("${userphoto}")
	private String userPhoto;

	/**
	 * 用户搜索记录
	 */
	@Resource
	private UserSearchService userSearchService;


	/**
	 * 用户浏览记录表
	 */
	@Resource
	private UserHistoryService userHistoryService;


	/**
	 * 二手房图片
	 */
	@Resource
	private UserFocusService userFocusService;


	/**
	 * 二手房图片
	 */
	@Resource
	private UserCollectService userCollectService;


	/**
	 * 用户认��信息
	 */
	@Resource
	private UserCertificationService userCertificationService;



	@Resource
	private UsersService usersService;


	@Resource
	private RedisUtil redisUtil;
	/**
	 *跳入用户列表
	 * */
	@RequestMapping("users/gotoList")
	public String gotoList(Users users, HttpServletRequest request, HttpServletResponse response){
		return "users/users_list";
	}

	/**
	 *跳入用户详情
	 * */
	@RequestMapping("users/gotoDetail")
	public  String gotoDetail(Users users, HttpServletRequest request, HttpServletResponse response){
		if(null==users.getId()){
			request.setAttribute("app_users",users);
		}else{
			request.setAttribute("app_users",usersService.selectByPrimaryKey(users));
		}
		return "users/users_detail";
	}

	/**
	 * 删除-用户表
	 */
	@ResponseBody
	@RequestMapping("users/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(Users users, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(usersService.deleteByPrimaryKey(users));
	}


	/**
	 *  保存 (主���为空则增加 否则 修改)-> 用户表
	 */
	@ResponseBody
	@RequestMapping("users/save")
	public DataRes save(Users users,HttpServletRequest request,HttpServletResponse response){
//	    System.out.println("photo======="+users.getUserphoto());
		if(users.getId()==null){
//			String username="ai"+ randomUtils.createData(10);
			String username=randomUtils.createData(10);
			users.setUsername(username);
			users.setImAccount(username);
			users.setImPassword("123456789");
			Map<String,Object> map=new HashMap<>();
			String token="";
			if(redisUtil.get("imtoken")!=null){
				token=(String)redisUtil.get("imtoken");
			}else{
				String result=IMUtils.getToken();
				Map map1= JSON.parseObject(result,HashMap.class);
				token="Bearer "+map1.get("access_token").toString();
				Integer expirt=Integer.parseInt(map1.get("expires_in").toString());
				redisUtil.set("imtoken",token,expirt);
			}
			IMUtils.registerUser(username,"123456789",token);
			if(users.getUserphoto()==null){
				users.setUserphoto(userPhoto);
			}
			return DataRes.success(usersService.insert(users));
		}else{
			if(users.getStatus()==1){
				redisUtil.del(RedisConstants.USER_TOKEN+users.getId());
			}
			if(users.getUserphoto()==null){
				users.setUserphoto(userPhoto);
			}
			return DataRes.success(usersService.update(users));
		}

	}
	/**
	 * 根据主键查询->用户表
	 */
	@ResponseBody
	@RequestMapping("users/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectByPrimaryKey(users));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->用户表
	 */
	@ResponseBody
	@RequestMapping("users/selectByCondition")
	public DataRes selectByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectByCondition(users));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为��则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->用户表
	 */
	@ResponseBody
	@RequestMapping("users/selectAllByPaging")
	public DataRes selectAllByPaging(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectAllByPaging(users));
	}


	/**
	 * 导出报表->用户表
	 */
	@RequestMapping("users/export")
	public void export(Users users,HttpServletRequest request,HttpServletResponse response){
		List<Users> list= usersService.selectAll(users);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("telephone", "手机号");
		header.put("nickname", "昵称");
		header.put("truename", "真实姓名");
		header.put("sex", "性别(1男，2女)");
		header.put("password", "密码");
		header.put("userphoto", "头像");
		header.put("email", "绑定邮箱");
		header.put("wxaccount", "绑定微信账号");
		header.put("aliaccount", "绑定支付宝账号");
		header.put("isCertification", "是否实名认证0未认证1认证");
		header.put("imAccount", "及时通讯账号");
		header.put("imPassword", "及时通讯账号密码");
		header.put("idCard", "身份证");
		header.put("role", "角色（1业主，2租客，3购房者4.职业房东5，置业顾问6，合伙人）");
		header.put("registerTime_", "注册时间");
		header.put("status", "用户状态0正常1禁用");
		ExcelUtils.exportExcel("用户表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	@RequestMapping("users/selectUsersAndUserCertification")
	@ResponseBody
	public DataRes selectUsersAndUserCertification(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserCertification(users));
	}


	/**
	 * 级联条件查询 APP用户--用户认证信息
	 */
	@RequestMapping("users/selectUsersAndUserCertificationByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserCertificationByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserCertificationByCondition(users));
	}


	/**
	 * 级联删除(根据主键删除) APP用户--用户认证信息
	 */
	@RequestMapping("users/deleteUsersAndUserCertification")
	@ResponseBody
	public DataRes deleteUsersAndUserCertification(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.deleteUsersAndUserCertification(users));
	}



	/**
	 * 级联查询(带分页) 二手房源详细信��--二手房图片
	 */
	@RequestMapping("users/selectUsersAndUserCollect")
	@ResponseBody
	public DataRes selectUsersAndUserCollect(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserCollect(users));
	}


	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	@RequestMapping("users/selectUsersAndUserCollectByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserCollectByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserCollectByCondition(users));
	}


	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("users/deleteUsersAndUserCollect")
	@ResponseBody
	public DataRes deleteUsersAndUserCollect(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.deleteUsersAndUserCollect(users));
	}



	/**
	 * 级联查询(带分页) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("users/selectUsersAndUserFocus")
	@ResponseBody
	public DataRes selectUsersAndUserFocus(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserFocus(users));
	}


	/**
	 * 级联条件查询 二手房源详细信息--二手房图片
	 */
	@RequestMapping("users/selectUsersAndUserFocusByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserFocusByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserFocusByCondition(users));
	}


	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--二手房图片
	 */
	@RequestMapping("users/deleteUsersAndUserFocus")
	@ResponseBody
	public DataRes deleteUsersAndUserFocus(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.deleteUsersAndUserFocus(users));
	}



	/**
	 * 级联查询(带分页) 二手房源详细信息--用户浏览记录表
	 */
	@RequestMapping("users/selectUsersAndUserHistory")
	@ResponseBody
	public DataRes selectUsersAndUserHistory(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserHistory(users));
	}


	/**
	 * 级联条件查询 二手房源详细信息--用户浏览记录表
	 */
	@RequestMapping("users/selectUsersAndUserHistoryByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserHistoryByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserHistoryByCondition(users));
	}


	/**
	 * 级联删除(根据主键删除) 二手房源详细信息--用户浏览记录表
	 */
	@RequestMapping("users/deleteUsersAndUserHistory")
	@ResponseBody
	public DataRes deleteUsersAndUserHistory(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.deleteUsersAndUserHistory(users));
	}



	/**
	 * 级联查询(带分页) 用户--用户搜索记录
	 */
	@RequestMapping("users/selectUsersAndUserSearch")
	@ResponseBody
	public DataRes selectUsersAndUserSearch(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserSearch(users));
	}


	/**
	 * 级联条件查询 用户--用户搜索记录
	 */
	@RequestMapping("users/selectUsersAndUserSearchByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserSearchByCondition(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.selectUsersAndUserSearchByCondition(users));
	}


	/**
	 * 级联删除(根据主键删除) 用户--用户搜索记录
	 */
	@RequestMapping("users/deleteUsersAndUserSearch")
	@ResponseBody
	public DataRes deleteUsersAndUserSearch(Users users,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(usersService.deleteUsersAndUserSearch(users));
	}

	/**
	 * 头像上传
	 */
	@RequestMapping("users/upload")
	@ResponseBody
	@RequiresPermissions("users/save")
	public DataRes upload(@RequestParam("file") MultipartFile uploadfile) throws IOException {
		// 获得文件：
		// 获得文件名：
		String filename = uploadfile.getOriginalFilename();
		// 获得输入流：
		InputStream input = uploadfile.getInputStream();
		File p = new File(filePath + DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/"));
		if (!p.exists()) {
			p.mkdirs();
		}
		File file = new File(p, DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
		uploadfile.transferTo(file);
		return DataRes.success(DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/") + DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename);
	}

	/**
	 * 查看图片
	 *
	 * @return
	 */
	@RequestMapping("users/watch")
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

	/**
	 * 判断手机号是否存在
	 * @param telephone
	 * @return
	 */
	@RequestMapping("users/isTelephoneExit")
	@ResponseBody
	public DataRes isTelephoneExit(String telephone){
		Users users=new Users();
		users.setTelephone(telephone);
		List<Users> list=usersService.selectAll(users);
		if(list.size()>0){
			return DataRes.success(false);
		}else{
			return DataRes.success(true);
		}
	}

	@RequestMapping("users/updateSpread")
	@ResponseBody
	public DataRes updateSpread(Users users){

		if(users.getIsSpread()==0){
			users.setSpreadCode(" ");
			usersService.update(users);
		}else{
			users.setSpreadCode(randomUtils.getRandomString(6));
			usersService.update(users);
		}
		return DataRes.success(true);
	}
}
