package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;


/**
 *圈子信息评论表 bean
 */
public class CommunityComment   extends Page {

	public  static  final  Integer TYPE_COMMENT   = 1;

	public  static  final  Integer TYPE_REPLAY    = 2;

	/**
	 * 房圈子
	 */
	private Community community;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 关联圈子信息表id
	 */
	private Integer communityId;
	/**
	 * 关联用户表id,评论人
	 */
	private Integer userId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 评论目标用户id
	 */
	private Integer toUid;
	/**
	 * 评论时间
	 */
	private Date createtime;

	/**
	 *类型：1---评论  2-----回复
	 * */
	private Integer type;

	private String nickname;

	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getCommunityId(){
		return communityId;
	}


	public void setCommunityId(Integer communityId){
		this.communityId=communityId;
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


	public Integer getToUid(){
		return toUid;
	}


	public void setToUid(Integer toUid){
		this.toUid=toUid;
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


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
