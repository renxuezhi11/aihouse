package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.Map;


/**
 *用户收藏表 bean
 */
public class UserCollect   extends Page {

	/**
	 * 用户详细信息
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
	 * 收藏类型（1新房，2二手房，3租房，4商铺，5写字楼，6圈子）
	 */
	private Integer collectType;
	/**
	 * 房源id
	 */
	private Integer houseId;
	/**
	 * 收藏时间
	 */
	private Date createtime;
	/**
	 * 是否删除0正常1删除
	 */
	private Integer isDel;

	private Map<String,Object> map;

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
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


	public Integer getCollectType(){
		return collectType;
	}


	public void setCollectType(Integer collectType){
		this.collectType=collectType;
	}


	public Integer getHouseId(){
		return houseId;
	}


	public void setHouseId(Integer houseId){
		this.houseId=houseId;
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


	public Integer getIsDel(){
		return isDel;
	}


	public void setIsDel(Integer isDel){
		this.isDel=isDel;
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
