package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

/**
 *问题回复点赞表 bean
 */
public class AskCommentPraise   extends Page {

	//未浏览已浏览
	public final  static  String   CHECKED = "1";

	//未浏览
	public final  static  String   UNCHECKED = "0";

	/**
	 * 回复表
	 */
	private AppAskComent appAskComent;

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 点赞用户id
	 */
	private Integer userId;
	/**
	 * 问题回复主键id
	 */
	private Integer askCommentId;
	/**
	 * 是否被问题所属人观看
	 */
	private String isChecked;
	/**
	 * 创建时间
	 */
	private Date createTime;
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


	public Integer getAskCommentId(){
		return askCommentId;
	}


	public void setAskCommentId(Integer askCommentId){
		this.askCommentId=askCommentId;
	}


	public String getIsChecked(){
		return isChecked;
	}


	public void setIsChecked(String isChecked){
		this.isChecked=isChecked;
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


	/**
	 * 回复表
	 */
	public AppAskComent getAppAskComent(){
		return appAskComent;
	}

	/**
	 * 回复表
	 */
	public void setAppAskComent(AppAskComent appAskComent){
		this.appAskComent = appAskComent;
	}

}
