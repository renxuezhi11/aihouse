package com.aihouse.aihousesys.controller.users;

import javax.annotation.Resource;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserCertificationService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import com.aihouse.aihousedao.bean.UserCertification;


/**
 *用户认证表 controller
 */
@Controller
public class SysUserCertificationController  {

	private final static Integer count = 1;

	/**
	 * APP用户
	 */
	@Resource
	private UsersService usersService;



	@Resource
	private UserCertificationService userCertificationService;

	/**
	 * 用户认证列表
	 */
	@RequestMapping("userCertification/gotoList")
	public String gotoList(UserCertification userCertification, HttpServletRequest request, HttpServletResponse response){
		return "users/user_certification_list";
	}

	/**
	 * 用户认证详情
	 */
	@RequestMapping("userCertification/gotoDetail")
	public String gotoDetail(UserCertification userCertification, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(null == userCertification.getId()){
			throw new  Exception("数据丢失用户认证");
		}else{
			userCertification = userCertificationService.selectByPrimaryKey(userCertification);
			request.setAttribute("user_certification",userCertification);
			if(userCertification.getImgUrl()!=null){
			    String[] urls=userCertification.getImgUrl().split(",");
			    request.setAttribute("imgUrl",urls);
            }
			Users users = new Users();
			users.setId(userCertification.getUserId());
			request.setAttribute("app_users",usersService.selectByPrimaryKey(users));
		}
		return "users/user_certification_detail";
	}

	/**
	 * 删除-用户认证表
	 */
	@ResponseBody
	@RequestMapping("userCertification/deleteByPrimaryKey")
	public DataRes deleteByPrimaryKey(UserCertification userCertification, HttpServletRequest request, HttpServletResponse response){
		return DataRes.success(userCertificationService.deleteByPrimaryKey(userCertification));
	}


	/**
	 *  保存 (主键为空则增加 否则 修改)-> 用户认证表
	 */
	@ResponseBody
	@RequestMapping("userCertification/save")
	public DataRes save(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		if(userCertification.getId()==null){
			return DataRes.success(userCertificationService.insert(userCertification));
		}else{
            Users users = new Users();
			UserCertification userCertification1=userCertificationService.selectByPrimaryKey(userCertification);
			users.setId(userCertification1.getUserId());
			Users users1=usersService.selectByPrimaryKey(users);
			if(userCertification1.getStatus()!=userCertification.getStatus()) {//审核状态变化

				if (userCertification.getStatus() == 1) {
					users.setIsCertification(Users.ISCERTIFICATION_YES);
					usersService.update(users);
				} else if (userCertification.getStatus() == 2) {
					users.setIsCertification(Users.ISCERTIFICATION_FAIL);
					usersService.update(users);
				}
				//短信通知，type，phone
				if(userCertification1.getStatus()==0) {
					userCertificationService.sendSms(userCertification.getStatus(), users1.getTelephone());
				}
			}
            return DataRes.success(userCertificationService.update(userCertification));
        }
	}


	/**
	 * 根据主键查询->用户认证表
	 */
	@ResponseBody
	@RequestMapping("userCertification/selectByPrimaryKey")
	public DataRes selectByPrimaryKey(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.selectByPrimaryKey(userCertification));
	}


	/**
	 * 根据条件查询(所有的实体属性都是条件,如果为空则忽略该字段)->用户认证表
	 */
	@ResponseBody
	@RequestMapping("userCertification/selectByCondition")
	public DataRes selectByCondition(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.selectByCondition(userCertification));
	}


	/**
	 * 分页查询 (所有的实体属性都是条件,如果为空则忽略该字段) (详见Page类.所以的实体都继承该类 默认 page=1 pageSize=10)->用户认证表
	 */
	@ResponseBody
	@RequestMapping("userCertification/selectAllByPaging")
	public DataRes selectAllByPaging(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.selectAllByPaging(userCertification));
	}


	/**
	 * 导出报表->用户认证表
	 */
	@RequestMapping("userCertification/export")
	public void export(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		List<UserCertification> list= userCertificationService.selectAll(userCertification);
		Map<String, String> header = new LinkedHashMap<>();
		header.put("id", "id");
		header.put("userId", "用户id");
		header.put("idCard", "身份证");
		header.put("imgUrl", "图片地址");
		header.put("truename", "真实姓名");
		header.put("createTime_", "认证时间");
		header.put("status", "认证审核状态0未审核1审核通过2审核不通过");
		header.put("statusContent", "审核回复");
		ExcelUtils.exportExcel("用户认证表",header,list,response,request);

	}


	/**
	 * 级联查询(带分页) APP用户--用户认证信息
	 */
	@RequestMapping("userCertification/selectUsersAndUserCertification")
	@ResponseBody
	public DataRes selectUsersAndUserCertification(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.selectUsersAndUserCertification(userCertification));
	}


	/**
	 * 级联条件查询 APP用户--用户认证信息
	 */
	@RequestMapping("userCertification/selectUsersAndUserCertificationByCondition")
	@ResponseBody
	public DataRes selectUsersAndUserCertificationByCondition(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.selectUsersAndUserCertificationByCondition(userCertification));
	}


	/**
	 * 级联删除(根据主键删除) APP用户--用户认证信息
	 */
	@RequestMapping("userCertification/deleteUsersAndUserCertification")
	@ResponseBody
	public DataRes deleteUsersAndUserCertification(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		return DataRes.success(userCertificationService.deleteUsersAndUserCertification(userCertification));
	}

	/**
	 * 审核通过--用户认证信息
	 */
	@RequestMapping("userCertification/pass")
	@ResponseBody
	@Transactional
	public DataRes passUserCertification(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		UserCertification userCertification1 = userCertificationService.selectByPrimaryKey(userCertification);
		if(userCertification1.getStatus()!=1) {
			userCertification.setStatus(UserCertification.STATUS_PASS);
			Users users = new Users();
			users.setId(userCertification1.getUserId());
			users.setIsCertification(Users.ISCERTIFICATION_YES);
			int i = usersService.update(users);
			if(userCertification1.getStatus()==0){
				Users users1=usersService.selectByPrimaryKey(users);
				userCertificationService.sendSms(userCertification.getStatus(), users1.getTelephone());
			}
			return DataRes.success(userCertificationService.update(userCertification));
		}else{
			return DataRes.success("已认证");
		}

	}

	/**
	 * 审核驳回--用户认证信息
	 */
	@RequestMapping("userCertification/reject")
	@ResponseBody
	public DataRes rejectUserCertification(UserCertification userCertification,HttpServletRequest request,HttpServletResponse response){
		UserCertification userCertification1 = userCertificationService.selectByPrimaryKey(userCertification);
		if(userCertification1.getStatus()!=2) {
			userCertification.setStatus(UserCertification.STATUS_REJECT);
			Users users = new Users();
			users.setId(userCertification1.getUserId());
			users.setIsCertification(Users.ISCERTIFICATION_NO);
			usersService.update(users);
			if(userCertification1.getStatus()==0){
				Users users1=usersService.selectByPrimaryKey(users);
				userCertificationService.sendSms(userCertification.getStatus(), users1.getTelephone());
			}
			return DataRes.success(userCertificationService.update(userCertification));
		}
		else{
			return DataRes.success("已拒绝");
		}
	}
}
