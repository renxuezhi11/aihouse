package com.aihouse.aihousedao.bean ;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.math.BigDecimal;
/**
 *楼盘户型表 bean
 */
public class SysNewHouseType   extends Page {

    //在售
    private final static  Integer ON_SALE = 0;
    //待售
    private final  static  Integer FOR_SALE = 1;
    //售罄
    private final  static  Integer SELL_OUT = 2;
    //正常
    private final  static  Integer NORMAL_STATE = 0;
    //删除
    private final  static  Integer DELETE_STATE = 1;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 关联楼盘表id
	 */
	private Integer newHouseId;
	/**
	 * 户型名称
	 */
	private String typeName;
	/**
	 * 建筑面积（m2）
	 */
	private BigDecimal coveredArea;
	/**
	 * 户型均价(元)
	 */
	private BigDecimal averagePrice;
	/**
	 * 户型总价(万元)
	 */
	private BigDecimal totalPrice;
	/**
	 * 户型分布
	 */
	private String spread;

	/**
	 * 房
	 */
	private Integer room;
	/**
	 * 厅
	 */
	private Integer hall;
	/**
	 * 厨房
	 */
	private Integer cookroom;
	/**
	 * 卫生间
	 */
	private Integer toilet;
	/**
	 * 阳台
	 */
	private Integer gallery;
	/**
	 * 销售进度(0在售，1待售，2售罄)
	 */
	private Integer sellStage;
	/**
	 * 户型特色
	 */
	private String feature;
	/**
	 * 户型图路径
	 */
	private String typeImg;
	/**
	 * 户型
	 */
	private String houseType;
	/**
	 * 上传时间
	 */
	private Date createtime;
	/**
	 * 状态0正常1删除
	 */
	private Integer isDel;

	public Integer getRoom() {
		return room;
	}

	public void setRoom(Integer room) {
		this.room = room;
	}

	public Integer getHall() {
		return hall;
	}

	public void setHall(Integer hall) {
		this.hall = hall;
	}

	public Integer getCookroom() {
		return cookroom;
	}

	public void setCookroom(Integer cookroom) {
		this.cookroom = cookroom;
	}

	public Integer getToilet() {
		return toilet;
	}

	public void setToilet(Integer toilet) {
		this.toilet = toilet;
	}

	public Integer getGallery() {
		return gallery;
	}

	public void setGallery(Integer gallery) {
		this.gallery = gallery;
	}

	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public Integer getNewHouseId(){
		return newHouseId;
	}


	public void setNewHouseId(Integer newHouseId){
		this.newHouseId=newHouseId;
	}


	public String getTypeName(){
		return typeName;
	}


	public void setTypeName(String typeName){
		this.typeName=typeName;
	}


	public BigDecimal getCoveredArea(){
		return coveredArea;
	}


	public void setCoveredArea(BigDecimal coveredArea){
		this.coveredArea=coveredArea;
	}


	public BigDecimal getAveragePrice(){
		return averagePrice;
	}


	public void setAveragePrice(BigDecimal averagePrice){
		this.averagePrice=averagePrice;
	}


	public BigDecimal getTotalPrice(){
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice){
		this.totalPrice=totalPrice;
	}


	public String getSpread(){
		return spread;
	}


	public void setSpread(String spread){
		this.spread=spread;
	}


	public Integer getSellStage(){
		return sellStage;
	}


	public void setSellStage(Integer sellStage){
		this.sellStage=sellStage;
	}


	public String getFeature(){
		return feature;
	}


	public void setFeature(String feature){
		this.feature=feature;
	}


	public String getTypeImg(){
		return typeImg;
	}


	public void setTypeImg(String typeImg){
		this.typeImg=typeImg;
	}


	public String getHouseType(){
		return houseType;
	}


	public void setHouseType(String houseType){
		this.houseType=houseType;
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

	public  String getsellStage_() {
		if(StringUtils.isEmpty(sellStage)){
			return "";
		}else if(sellStage.equals(ON_SALE)){
			return "在售";
		}else if(sellStage.equals(FOR_SALE)){
			return "待售";
		}else if(sellStage.equals(SELL_OUT)){
			return "售罄";
		}
		return "";
	}

	public String getisDel_() {
		if(StringUtils.isEmpty(isDel)){
			return"";
		}else if(isDel.equals(NORMAL_STATE)){
			return "正常";
		}else if(isDel.equals(DELETE_STATE)){
			return "删除";
		}
		return "";
	}

	public String getcreattime_(){ return DateUtils.formatDateTime(createtime); }

}
