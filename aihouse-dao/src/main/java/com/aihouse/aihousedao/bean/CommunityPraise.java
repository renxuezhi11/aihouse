package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


/**
 *房圈子点赞表 bean
 */
public class CommunityPraise   extends Page {

	/**
	 * 房圈子
	 */
	private Community community;



	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 圈子id
	 */
	private Integer communityId;
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


	public Integer getCommunityId(){
		return communityId;
	}


	public void setCommunityId(Integer communityId){
		this.communityId=communityId;
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
	 * 房圈子
	 */
	public Community getCommunity(){
		return community;
	}


	/**
	 * 房圈子
	 */
	public void setCommunity(Community community){
		this.community = community;
	}



}
