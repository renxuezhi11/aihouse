package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;
import java.util.List;

/**
 * 商铺出租bean
 */
public class ShopHouse  extends Page {

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 面积
     */
    private Double coveredArea;

    /**
     * 是否包含物业费
     */
    private Integer isFee;

    /**
     * 物业费
     */
    private Double propertyFee;

    /**
     * 面宽:m
     */
    private Double faceWidth;

    /**
     * 层高:m
     */
    private Double standardTall;

    /**
     * 进深:m
     */
    private Double floorLongth;

    /**
     * 楼层
     */
    private Integer floorLevels;

    /**
     * 总楼层
     */
    private Integer totalFloor;

    /**
     * 状态（1营业中，2闲置中，3新浦）
     */
    private Integer status;

    /**
     * 所属市Id
     */
    private Integer cityId;

    /**
     * 所属区Id
     */
    private Integer districtId;

    /**
     * 所属街道Id
     */
    private Integer areaId;

    /**
     * 详细地址
     */
    private String adress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 是否装让：是-1，否-0
     */
    private Integer ifTransfer;

    /**
     * 房源描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人ID
     */
    private Integer createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人Id
     */
    private Integer updateBy;

    /**
     * 月租金，若无则为0
     */
    private Double monthlyRent;

    /**
     * 转让费，若无则为0
     */
    private Double transferFee;

    /**
     * 起租期，若无则为0: 个月
     */
    private Integer leaseMinimum;

    /**
     * 出租方案(1.押一付三2押一付一3押一付二4押二付一5半年付6年付7面议)
     */
    private Integer mortgagePayment;

    /**
     * 商铺类型：1住宅底商2商业街商铺，3临街门面，4写字楼配套底商，5购物中心，6其他
     */
    private Integer type;

    /**
     * 是否实勘，0未1已实勘
     */
    private Integer houseReal;

    /**
     * 审核回复
     */
    private String statusContent;

    /**
     * 状态（0未审核，1审核通过，2审核不通过）
     */
    private Integer checkStatus;

    /**
     * 装修情况(毛坯，精装修，简装修，中装修，豪华装修)
     */
    private String fixture;

    /**
     * 房屋配套
     */
    private String support;

    /**
     * 0男1女
     */
    private Integer sex;

    /**
     * 0业主1经纪人
     */
    private Integer userType;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 适合经营(百货超市，酒店宾馆，家具建材，服饰鞋包，生活服务，美容美发，餐饮美食，休闲娱乐，其他)
     */
    private String operation;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 关联小区id
     */
    private Integer villageId;

    /**
     * 上架状态0未上架1上架2下架
     */
    private Integer flag;

    private String feature;

    private Integer isDel;

    private List<String> shopHouseImgList;

    public List<String> getShopHouseImgList() {
        return shopHouseImgList;
    }

    public void setShopHouseImgList(List<String> shopHouseImgList) {
        this.shopHouseImgList = shopHouseImgList;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Integer getIsFee() {
        return isFee;
    }

    public void setIsFee(Integer isFee) {
        this.isFee = isFee;
    }

    public Double getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(Double propertyFee) {
        this.propertyFee = propertyFee;
    }

    public Double getFaceWidth() {
        return faceWidth;
    }

    public void setFaceWidth(Double faceWidth) {
        this.faceWidth = faceWidth;
    }

    public Double getStandardTall() {
        return standardTall;
    }

    public void setStandardTall(Double standardTall) {
        this.standardTall = standardTall;
    }

    public Double getFloorLongth() {
        return floorLongth;
    }

    public void setFloorLongth(Double floorLongth) {
        this.floorLongth = floorLongth;
    }

    public Integer getFloorLevels() {
        return floorLevels;
    }

    public void setFloorLevels(Integer floorLevels) {
        this.floorLevels = floorLevels;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getIfTransfer() {
        return ifTransfer;
    }

    public void setIfTransfer(Integer ifTransfer) {
        this.ifTransfer = ifTransfer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Double getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }

    public Integer getLeaseMinimum() {
        return leaseMinimum;
    }

    public void setLeaseMinimum(Integer leaseMinimum) {
        this.leaseMinimum = leaseMinimum;
    }

    public Integer getMortgagePayment() {
        return mortgagePayment;
    }

    public void setMortgagePayment(Integer mortgagePayment) {
        this.mortgagePayment = mortgagePayment;
    }

    public Integer getType() {
        return type;
    }

    public String getType_() {
        if(type!=null){
            if(type==1){
                return "住宅底商";
            }else if(type==2){
                return "商业街商铺";
            }else if(type==3){
                return "临街门面";
            }else  if(type==4){
                return "写字楼配套底商";
            }else if(type==5){
                return "购物中心";
            }else{
                return "其他";
            }
        }
        return null;
    }

    public String getFlag_() {
        if(flag!=null) {
            if (flag==0){
                return "未上架";
            }else if (flag == 1) {
                return "上架";
            } else if (flag == 2) {
                return "已下架";
            }
        }
        return "";
    }
    public String getCheckStatus_() {
        if(checkStatus!=null) {
            if (checkStatus==0){
                return "未审核";
            }else if (checkStatus == 1) {
                return "审核通过";
            } else if (checkStatus == 2) {
                return "审核不通过";
            }
        }
        return "";
    }

    public String getCreateTime_() {
        return DateUtils.formatDateTime(createTime);
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHouseReal() {
        return houseReal;
    }

    public void setHouseReal(Integer houseReal) {
        this.houseReal = houseReal;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }
    public String getUserType_(){
        if(userType!=null){
            if(userType==0){
                return "业主";
            }else if(userType==1){
                return "经纪人";
            }
        }
        return "";
    }

    public String getSex_(){
        if(sex!=null){
            if(sex==0){
                return "男";
            }else  if(sex==1){
                return "女";
            }
        }
        return "";
    }
    public String getMortgagePayment_(){
        if(mortgagePayment!=null){
            if(mortgagePayment==1){
                return "押一付三";
            }else if(mortgagePayment==2){
                return "押一付一";
            }else if(mortgagePayment==3){
                return "押一付二";
            }else if(mortgagePayment==4){
                return "押二付一";
            }else if(mortgagePayment==5){
                return "半年付";
            }else if(mortgagePayment==6){
                return "年付";
            }else if(mortgagePayment==7){
                return "面议";
            }
        }
        return "";
    }

    public String getIsFee_(){
        if(isFee!=null){
            if(isFee==0){
                return "否";
            }else if(isFee==1){
                return "是";
            }
        }
        return "";
    }

    public  String getIfTransfer_(){
        if(ifTransfer!=null){
            if(ifTransfer==0){
                return "否";
            }else if(ifTransfer==1){
                return "是";
            }
        }
        return "";
    }

    public String getStatus_(){
        if(status!=null){
            if(status==1){
                return "营业中";
            }else if(status==2){
                return "闲置中";
            }else if(status==3){
                return "新铺";
            }
        }
        return "";
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getIsDel_(){
        if(this.isDel!=null){
            if(isDel==0){
                return "正常";
            }else{
                return "删除";
            }
        }
        return "";
    }
}
