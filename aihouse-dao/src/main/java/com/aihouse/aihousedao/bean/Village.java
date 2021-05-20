package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.math.BigDecimal;


/**
 *小区表 bean
 */
public class Village   extends Page {

	/**
	 * 二手房
	 */
	private SecondHandHouse secondHandHouse;



	/**
	 * id
	 */
	private Integer id;

	/**
	 * 小区名称
	 */
	private String villageName;
	/**
	 * 城市id，关联区域表
	 */
	private Integer cityid;
	/**
	 * 区域id，关联区域表
	 */
	private Integer areaid;
	/**
	 * 街道片区，关联区域表
	 */
	private Integer streesid;
	/**
	 * 项目地址
	 */
	private String address;
	/**
	 * 省份
	 */
	private Integer provinceid;
	/**
	 * 占地面积单位m2
	 */
	private BigDecimal floorSpace;
	/**
	 * 建筑面积单位m2
	 */
	private BigDecimal coveredArea;
	/**
	 * 容积率
	 */
	private BigDecimal plotRatio;
	/**
	 * 绿化率
	 */
	private BigDecimal greeningRate;
	/**
	 * 停车位地上
	 */
	private Integer carPlaceUp;
	/**
	 * 停车位��下
	 */
	private Integer carPlaceDown;
	/**
	 * 楼栋数
	 */
	private Integer building;
	/**
	 * 总户数
	 */
	private Integer totalHouse;
	/**
	 * 物业公司
	 */
	private String propertyCompany;
	/**
	 * 物业费
	 */
	private BigDecimal propertyFee;
	/**
	 * 产权年限
	 */
	private Integer propertyYear;
	/**
	 * 开发商
	 */
	private String developer;
	/**
	 * 添加时间
	 */
	private Date createtime;
	/**
	 * 建筑类型
	 */
	private String coveredType;

	/**
	 * 经度
	 */
	private String lng;

	/**
	 * 纬度
	 */
	private String lat;
	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getCityid(){
		return cityid;
	}


	public void setCityid(Integer cityid){
		this.cityid=cityid;
	}


	public Integer getAreaid(){
		return areaid;
	}


	public void setAreaid(Integer areaid){
		this.areaid=areaid;
	}


	public Integer getStreesid(){
		return streesid;
	}


	public void setStreesid(Integer streesid){
		this.streesid=streesid;
	}


	public String getAddress(){
		return address;
	}


	public void setAddress(String address){
		this.address=address;
	}


	public Integer getProvinceid(){
		return provinceid;
	}


	public void setProvinceid(Integer provinceid){
		this.provinceid=provinceid;
	}


	public BigDecimal getFloorSpace(){
		return floorSpace;
	}


	public void setFloorSpace(BigDecimal floorSpace){
		this.floorSpace=floorSpace;
	}


	public BigDecimal getCoveredArea(){
		return coveredArea;
	}


	public void setCoveredArea(BigDecimal coveredArea){
		this.coveredArea=coveredArea;
	}


	public BigDecimal getPlotRatio(){
		return plotRatio;
	}


	public void setPlotRatio(BigDecimal plotRatio){
		this.plotRatio=plotRatio;
	}


	public BigDecimal getGreeningRate(){
		return greeningRate;
	}


	public void setGreeningRate(BigDecimal greeningRate){
		this.greeningRate=greeningRate;
	}


	public Integer getCarPlaceUp(){
		return carPlaceUp;
	}


	public void setCarPlaceUp(Integer carPlaceUp){
		this.carPlaceUp=carPlaceUp;
	}


	public Integer getCarPlaceDown(){
		return carPlaceDown;
	}


	public void setCarPlaceDown(Integer carPlaceDown){
		this.carPlaceDown=carPlaceDown;
	}


	public Integer getBuilding(){
		return building;
	}


	public void setBuilding(Integer building){
		this.building=building;
	}


	public Integer getTotalHouse(){
		return totalHouse;
	}


	public void setTotalHouse(Integer totalHouse){
		this.totalHouse=totalHouse;
	}


	public String getPropertyCompany(){
		return propertyCompany;
	}


	public void setPropertyCompany(String propertyCompany){
		this.propertyCompany=propertyCompany;
	}


	public BigDecimal getPropertyFee(){
		return propertyFee;
	}


	public void setPropertyFee(BigDecimal propertyFee){
		this.propertyFee=propertyFee;
	}


	public Integer getPropertyYear(){
		return propertyYear;
	}


	public void setPropertyYear(Integer propertyYear){
		this.propertyYear=propertyYear;
	}


	public String getDeveloper(){
		return developer;
	}


	public void setDeveloper(String developer){
		this.developer=developer;
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


	public String getCoveredType(){
		return coveredType;
	}


	public void setCoveredType(String coveredType){
		this.coveredType=coveredType;
	}


	/**
	 * 二手房
	 */
	public SecondHandHouse getSecondHandHouse(){
		return secondHandHouse;
	}


	/**
	 * 二手房
	 */
	public void setSecondHandHouse(SecondHandHouse secondHandHouse){
		this.secondHandHouse = secondHandHouse;
	}


	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
}
