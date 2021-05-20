package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;


/**
 *用户浏览记录表 bean
 */
public class UserHistory   extends Page {

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
	 * 浏览类型（1新房，2二手房，3租房，4商铺，5写字楼）
	 */
	private Integer historyType;
	/**
	 * 房源id
	 */
	private Integer houseId;
	/**
	 * 浏览时间
	 */
	private Date createtime;
	/**
	 * 是否删除0正常1删除
	 */
	private Integer isDel;

	private String housePicture;

	private double housePrice;

	private String houseUnit;

	private String houseArea;

	private String houseStreet;

	private String houseTitle;

	private String houseContent;

	private Integer houseFlag;

	public Integer getHouseFlag() {
		return houseFlag;
	}

	public void setHouseFlag(Integer houseFlag) {
		this.houseFlag = houseFlag;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseStreet() {
		return houseStreet;
	}

	public void setHouseStreet(String houseStreet) {
		this.houseStreet = houseStreet;
	}

	public String getHouseTitle() {
		return houseTitle;
	}

	public void setHouseTitle(String houseTitle) {
		this.houseTitle = houseTitle;
	}

	public String getHouseContent() {
		return houseContent;
	}

	public void setHouseContent(String houseContent) {
		this.houseContent = houseContent;
	}

	public String getHousePicture() {
		return housePicture;
	}

	public void setHousePicture(String housePicture) {
		this.housePicture = housePicture;
	}

	public double getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(double housePrice) {
		this.housePrice = housePrice;
	}

	public String getHouseUnit() {
		return houseUnit;
	}

	public void setHouseUnit(String houseUnit) {
		this.houseUnit = houseUnit;
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


	public Integer getHistoryType(){
		return historyType;
	}


	public void setHistoryType(Integer historyType){
		this.historyType=historyType;
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
