package com.aihouse.aihousedao.bean;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.math.BigDecimal;


/**
 *ahs_user_login_log bean
 */
public class UserLoginLog   extends Page {


	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户Id
	 */
	private Integer userId;
	/**
	 * 用户登录IP
	 */
	private String ip;
	/**
	 * 用户登录设备号
	 */
	private String deviceNumber;
	/**
	 * 创建时间
	 */
	private Date createtime;
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


	public String getIp(){
		return ip;
	}


	public void setIp(String ip){
		this.ip=ip;
	}


	public String getDeviceNumber(){
		return deviceNumber;
	}


	public void setDeviceNumber(String deviceNumber){
		this.deviceNumber=deviceNumber;
	}


	public String getCreatetime_(){
		return DateUtils.formatDateTime(createtime);
	}


	@JsonIgnore
	public Date getCreatetime(){
		return createtime;
	}


	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}


}
