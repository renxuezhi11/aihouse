package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;

import com.aihouse.aihousedao.bean.Village;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.math.BigDecimal;


/**
 *二手房表 bean
 */
public class SecondHandHouse   extends Page {

	//正常
	private final  static  Integer NORMAL_STATE = 0;
	//删除
	private final  static  Integer DELETE_STATE = 1;

	/**
	 * 是否有电梯
	 * **/
	//是
	private final static  Integer IS_LIFT_TRUE = 0;
    //否
	private final static  Integer IS_LIFT_FALSE = 1;

	/*
	* 审核状态
	* */
	//未审核
	private final static Integer AUDIT_STATUS_FORMAL = 0;
	//审核通过
	private final static Integer AUDIT_STATUS_PASS = 1;
	//审核失败
	private final static Integer AUDIT_STATUS_FAIL = 2;

	/**
	 * 发布人类型
	 * */
	//业主
     private final static Integer USERTYPE_OWNER  = 0;
     //经纪人
	 private final static Integer USERTYPE_BROKER = 1;

	 /**
	  *是否实勘
	  * */
	 //未
	 private final static Integer SOLID_NO = 0 ;
	 //已
	 private final static Integer SOLID_YES  = 1 ;

	 /**
	  * 上架状态
	  *
	  * */
	 //未上架
	 private final static Integer FLAG_NOT_ON   = 0 ;
	 //上架
	 private final static Integer FLAG_PUTAWAY  = 1 ;
	 //下架
	 private final static Integer FLAG_SOLD_OUT = 2 ;

	/**
	 * 二手房图片
	 */
	private List<String> secondHandHouseImgList;

	private List<SecondHandHouseImg> secondHandHouseImgListO;

	private List<String> secondHandHouseAgreementList;

	/**
	 * 小区
	 */
	private Village village;



	/**
	 * id
	 */
	private Integer id;
	/**
	 * 房源特色
	 */
	private String feature;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 小区id
	 */
	private Integer villageId;
	/**
	 * 小区名称
	 */
	private String villageName;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private String lng;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 楼层
	 */
	private Integer floor;
	/**
	 * 总楼层
	 */
	private Integer totalFloor;
	/**
	 * 建筑类型
	 */
	private String coveredType;
	/**
	 * 建筑面积(m2)
	 */
	private BigDecimal coveredArea;
	/**
	 * 售价(万元)
	 */
	private BigDecimal price;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 朝向(东，南，西，北，东北，东南，西南，西北，东西，南北)
	 */
	private String orientations;
	/**
	 * 是否有电梯0有1无
	 */
	private Integer isLift;
	/**
	 * 室
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
	 * 装修情况(毛坯，精装修，简装修，中装修，豪华装修)
	 */
	private String fixture;
	/**
	 * 房龄
	 */
	private Integer age;
	/**
	 * 房源描述
	 */
	private String houseDesc;
	/**
	 * 业主心态
	 */
	private String ownerMentality;
	/**
	 * 卖点
	 */
	private String sellPoint;
	/**
	 * 房号
	 */
	private String houseNumber;
	/**
	 * 发布时间
	 */
	private Date createtime;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 状态（0未审核，1审核通过，2审核不通过）
	 */
	private Integer status;
	/**
	 * 产品类型
	 */
	private String houseType;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 0男1女
	 */
	private Integer sex;
	/**
	 * 0业主1经纪人
	 */
	private Integer userType;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 签名
	 */
	private String signName;
	/**
	 * 经纪人（关联用户表id）
	 */
	private Integer broker;
	/**
	 * 是否实勘(0未1已)
	 */
	private Integer houseReal;
	/**
	 * 审核回复
	 */
	private String statusContent;
	/**
	 * 上架状态0未上架1上架2下架
	 */
	private Integer flag;
	/**
	 * 删除状态0正常1删除
	 */
	private Integer isDel;

	private Integer isTop;

	private Integer isSale;

	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public String getFeature(){
		return feature;
	}


	public void setFeature(String feature){
		this.feature=feature;
	}


	public String getTitle(){
		return title;
	}


	public void setTitle(String title){
		this.title=title;
	}


	public Integer getVillageId(){
		return villageId;
	}


	public void setVillageId(Integer villageId){
		this.villageId=villageId;
	}


	public String getVillageName(){
		return villageName;
	}


	public void setVillageName(String villageName){
		this.villageName=villageName;
	}


	public String getAddress(){
		return address;
	}


	public void setAddress(String address){
		this.address=address;
	}


	public String getLng(){
		return lng;
	}


	public void setLng(String lng){
		this.lng=lng;
	}


	public String getLat(){
		return lat;
	}


	public void setLat(String lat){
		this.lat=lat;
	}


	public Integer getFloor(){
		return floor;
	}


	public void setFloor(Integer floor){
		this.floor=floor;
	}


	public Integer getTotalFloor(){
		return totalFloor;
	}


	public void setTotalFloor(Integer totalFloor){
		this.totalFloor=totalFloor;
	}


	public String getCoveredType(){
		return coveredType;
	}


	public void setCoveredType(String coveredType){
		this.coveredType=coveredType;
	}


	public BigDecimal getCoveredArea(){
		return coveredArea;
	}


	public void setCoveredArea(BigDecimal coveredArea){
		this.coveredArea=coveredArea;
	}


	public BigDecimal getPrice(){
		return price;
	}


	public void setPrice(BigDecimal price){
		this.price=price;
	}


	public Integer getUserId(){
		return userId;
	}


	public void setUserId(Integer userId){
		this.userId=userId;
	}


	public String getOrientations(){
		return orientations;
	}


	public void setOrientations(String orientations){
		this.orientations=orientations;
	}


	public Integer getIsLift(){
		return isLift;
	}


	public void setIsLift(Integer isLift){
		this.isLift=isLift;
	}


	public Integer getRoom(){
		return room;
	}


	public void setRoom(Integer room){
		this.room=room;
	}


	public Integer getHall(){
		return hall;
	}


	public void setHall(Integer hall){
		this.hall=hall;
	}


	public Integer getCookroom(){
		return cookroom;
	}


	public void setCookroom(Integer cookroom){
		this.cookroom=cookroom;
	}


	public Integer getToilet(){
		return toilet;
	}


	public void setToilet(Integer toilet){
		this.toilet=toilet;
	}


	public Integer getGallery(){
		return gallery;
	}


	public void setGallery(Integer gallery){
		this.gallery=gallery;
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


	public String getFixture(){
		return fixture;
	}


	public void setFixture(String fixture){
		this.fixture=fixture;
	}


	public Integer getAge(){
		return age;
	}


	public void setAge(Integer age){
		this.age=age;
	}


	public String getHouseDesc(){
		return houseDesc;
	}


	public void setHouseDesc(String houseDesc){
		this.houseDesc=houseDesc;
	}


	public String getOwnerMentality(){
		return ownerMentality;
	}


	public void setOwnerMentality(String ownerMentality){
		this.ownerMentality=ownerMentality;
	}


	public String getSellPoint(){
		return sellPoint;
	}


	public void setSellPoint(String sellPoint){
		this.sellPoint=sellPoint;
	}


	public String getHouseNumber(){
		return houseNumber;
	}


	public void setHouseNumber(String houseNumber){
		this.houseNumber=houseNumber;
	}


	@JsonIgnore
	public Date getCreatetime(){
		return createtime;
	}


	public void setCreatetime(Date createtime){
		this.createtime=createtime;
	}

	@JsonIgnore
	public Date getUpdatetime(){
		return updatetime;
	}


	public void setUpdatetime(Date updatetime){
		this.updatetime=updatetime;
	}


	public Integer getStatus(){
		return status;
	}


	public void setStatus(Integer status){
		this.status=status;
	}


	public String getHouseType(){
		return houseType;
	}


	public void setHouseType(String houseType){
		this.houseType=houseType;
	}


	public String getContacts(){
		return contacts;
	}


	public void setContacts(String contacts){
		this.contacts=contacts;
	}


	public Integer getSex(){
		return sex;
	}


	public void setSex(Integer sex){
		this.sex=sex;
	}


	public Integer getUserType(){
		return userType;
	}


	public void setUserType(Integer userType){
		this.userType=userType;
	}


	public String getTelephone(){
		return telephone;
	}


	public void setTelephone(String telephone){
		this.telephone=telephone;
	}


	public String getSignName(){
		return signName;
	}


	public void setSignName(String signName){
		this.signName=signName;
	}


	public Integer getBroker(){
		return broker;
	}


	public void setBroker(Integer broker){
		this.broker=broker;
	}


	public Integer getHouseReal(){
		return houseReal;
	}


	public void setHouseReal(Integer houseReal){
		this.houseReal=houseReal;
	}


	public String getStatusContent(){
		return statusContent;
	}


	public void setStatusContent(String statusContent){
		this.statusContent=statusContent;
	}


	public Integer getFlag(){
		return flag;
	}


	public void setFlag(Integer flag){
		this.flag=flag;
	}


	public Integer getIsDel(){
		return isDel;
	}


	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}


	/**
	 * 小区
	 */
	public Village getVillage(){
		return village;
	}


	/**
	 * 小区
	 */
	public void setVillage(Village village){
		this.village = village;
	}



	/**
	 * 二手房图片
	 */
	public List<String> getSecondHandHouseImgList(){
		return secondHandHouseImgList;
	}


	/**
	 * 二手房图片
	 */
	public void setSecondHandHouseImgList(List<String> secondHandHouseImgList){
		this.secondHandHouseImgList = secondHandHouseImgList;
	}

	public String getisLift_(){
		if(StringUtils.isEmpty(isLift)){
			return "";
		}else if(isLift.equals(IS_LIFT_TRUE)){
			return "有";
		}else if(isLift.equals(IS_LIFT_FALSE)){
			return "无";
		}
		return "";
	}

	public  String getstatus_(){
		if(StringUtils.isEmpty(status)){
			return "";
		}else if(status.equals(AUDIT_STATUS_FORMAL)){
			return "未审核";
		}else if(status.equals(AUDIT_STATUS_PASS)){
			return "审核通过";
		}else if(status.equals(AUDIT_STATUS_FAIL)){
			return "审核不通过";
		}
		return "";
	}

	public String getuserType_(){
		if(StringUtils.isEmpty(userType)){
			return "";
		}else if(userType.equals(USERTYPE_OWNER)){
			return "业主";
		}else if(userType.equals(USERTYPE_BROKER)){
			return "经纪人";
		}
		return "";
	}

	public String gethouseReal_(){
		if(StringUtils.isEmpty(houseReal)){
			return "";
		}else if(houseReal.equals(SOLID_NO)){
			return "否";
		}else if(houseReal.equals(SOLID_YES)){
			return "是";
		}
		return "";
	}

	public  String getflag_(){
		if(StringUtils.isEmpty(flag)){
			return "";
		}else if(flag.equals(FLAG_NOT_ON)){
			return "未上架";
		}else if(flag.equals(FLAG_PUTAWAY)){
			return "上架";
		}else if(flag.equals(FLAG_SOLD_OUT)){
			return "下架";
		}
		return "";
	}

	public String getisDel_(){
		if(StringUtils.isEmpty(isDel)){
			return "";
		}else if(isDel.equals(NORMAL_STATE)){
			return "正常";
		}else if(isDel.equals(DELETE_STATE)){
			return "删除";
		}
		return "";
	}


	public String getcreateTime_(){
		return DateUtils.formatDateTime(createtime);
	}

	public String getupdateTime_(){
		return DateUtils.formatDateTime(updatetime);
	}


	public List<String> getSecondHandHouseAgreementList() {
		return secondHandHouseAgreementList;
	}

	public void setSecondHandHouseAgreementList(List<String> secondHandHouseAgreementList) {
		this.secondHandHouseAgreementList = secondHandHouseAgreementList;
	}

	public List<SecondHandHouseImg> getSecondHandHouseImgListO() {
		return secondHandHouseImgListO;
	}

	public void setSecondHandHouseImgListO(List<SecondHandHouseImg> secondHandHouseImgListO) {
		this.secondHandHouseImgListO = secondHandHouseImgListO;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getIsTop_(){
		if(this.isTop!=null){
			if(isTop==0){
				return "未置顶";
			}else{
				return "已置顶";
			}
		}
		return "";
	}

	public String getIsSale_(){
		if(this.isSale!=null){
			if(isSale==0){
				return "在售";
			}else{
				return "已售";
			}
		}
		return "";
	}

	public Integer getIsSale() {
		return isSale;
	}

	public void setIsSale(Integer isSale) {
		this.isSale = isSale;
	}
}
