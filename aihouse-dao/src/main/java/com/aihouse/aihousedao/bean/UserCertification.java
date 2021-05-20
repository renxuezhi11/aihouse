package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 *用户认证表 bean
 */
public class UserCertification   extends Page {

	/**
	 * 认证审核状态---未审核
	 * ***/
	public static final Integer STATUS_UNREVIEWED = 0;
	/**
	 * 用户状态---审核通过
	 * ***/
	public static final Integer STATUS_PASS = 1;
	/**
	 * 用户状态---审核驳回
	 * ***/
	public static final Integer STATUS_REJECT = 2;

	/**
	 * APP用户
	 */
	private Users users;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 图片地址
	 */
	private String imgUrl;
	/**
	 * 真实姓名
	 */
	private String truename;
	/**
	 * 认证时间
	 */
	private Date createTime;
	/**
	 * 认证审核状态0未审核1审核通过2审核不通过
	 */
	private Integer status;
	/**
	 * 审核回复
	 */
	private String statusContent;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public String getIdCard(){
		return idCard;
	}


	public void setIdCard(String idCard){
		this.idCard=idCard;
	}


	public String getImgUrl(){
		return imgUrl;
	}


	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}


	public String getTruename(){
		return truename;
	}


	public void setTruename(String truename){
		this.truename=truename;
	}


	public String getCreateTime_(){
		return DateUtils.formatDateTime(createTime);
	}


	@JsonIgnore
	public Date getCreateTime(){
		return createTime;
	}


	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}


	public Integer getStatus(){
		return status;
	}


	public void setStatus(Integer status){
		this.status=status;
	}


	public String getStatusContent(){
		return statusContent;
	}


	public void setStatusContent(String statusContent){
		this.statusContent=statusContent;
	}


	/**
	 * APP用户
	 */
	public Users getUsers(){
		return users;
	}


	/**
	 * APP用户
	 */
	public void setUsers(Users users){
		this.users = users;
	}

	public String getstatus_(){
		if(StringUtils.isEmpty(status)){
			return "";
		}else if(status.equals(STATUS_UNREVIEWED)){
			return "未审核";
		}else if(status.equals(STATUS_PASS)){
			return "通过";
		}else if(status.equals(STATUS_REJECT)){
			return "驳回";
		}
		return "";
	}

}
