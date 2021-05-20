package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

/**
 *ahs_ask_comment_reply bean
 */
public class AskCommentReply   extends Page {


	//未浏览已浏览
	public final  static  String   CHECKED = "1";

	//未浏览
	public final  static  String   UNCHECKED = "0";

	//类型:1----评论
	public static final   Integer  TYPE_COMMENT = 1;
    //类型:2----回复
	public static final   Integer  TYPE_REVERT  = 2;

	/**
	 * 回复表
	 */
	private AppAskComent appAskComent;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 评论主键
	 */
	private Integer commentId;
	/**
	 * 用户主键
	 */
	private Integer userId;
	/**
	 * 评论内容
	 */
	private String commentText;
	/**
	 * 是否被题主浏览过 0-未浏览 1-已浏览
	 */
	private String isChecked;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 类型：1----评论    2-----回复
	 */
	private Integer type;
	/**
	 * 目标用户Id
	 */
	private Integer ToUserId;

	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getCommentId(){
		return commentId;
	}


	public void setCommentId(Integer commentId){
		this.commentId=commentId;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public String getCommentText(){
		return commentText;
	}


	public void setCommentText(String commentText){
		this.commentText=commentText;
	}


	public String getIsChecked(){
		return isChecked;
	}


	public void setIsChecked(String isChecked){
		this.isChecked=isChecked;
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


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getToUserId() {
		return ToUserId;
	}

	public void setToUserId(Integer toUserId) {
		ToUserId = toUserId;
	}
}
