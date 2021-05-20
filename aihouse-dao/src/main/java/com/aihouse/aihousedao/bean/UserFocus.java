package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


/**
 *用户关注表 bean
 */
public class UserFocus   extends Page {

	/**
	 * 二手房源详细信息
	 */
	private Users users;

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 关联用户表id
	 */
	private Integer userId;
	/**
	 * 关注的用户表id
	 */
	private Integer focusUserId;
	/**
	 * 关注时间
	 */
	private Date createtime;

	/**
	 * 用户昵称
	 */
	private String focusUserName;

	private String nickname;

	private String userphoto;

	private String signname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getSignname() {
		return signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
	}

	public String getFocusUserName() {
		return focusUserName;
	}

	public void setFocusUserName(String focusUserName) {
		this.focusUserName = focusUserName;
	}

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


	public Integer getFocusUserId(){
		return focusUserId;
	}


	public void setFocusUserId(Integer focusUserId){
		this.focusUserId=focusUserId;
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


	/**
	 * 用户详细信息
	 */
	public Users getUsers(){
		return users;
	}


	/**
	 * 用户详细信息
	 */
	public void setUsers(Users users){
		this.users = users;
	}



}
