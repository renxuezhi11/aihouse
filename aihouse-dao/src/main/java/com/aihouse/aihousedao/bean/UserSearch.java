package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


/**
 *用户搜索记录表 bean
 */
public class UserSearch   extends Page {

	/**
	 * 用户
	 */
	private Users users;



	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 搜索内容
	 */
	private String content;
	/**
	 * 时间
	 */
	private Date createTime;
	/**
	 * 搜索城市id
	 */
	private Integer cityId;

	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId(){
		return id;
	}


	public void setId(Long id){
		this.id=id;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public String getContent(){
		return content;
	}


	public void setContent(String content){
		this.content=content;
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


	public Integer getCityId(){
		return cityId;
	}


	public void setCityId(Integer cityId){
		this.cityId=cityId;
	}


	/**
	 * 用户
	 */
	public Users getUsers(){
		return users;
	}


	/**
	 * 用户
	 */
	public void setUsers(Users users){
		this.users = users;
	}



}
