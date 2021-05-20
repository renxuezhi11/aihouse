package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;

import java.util.Date;
import java.util.List;

/**
 * 写字楼出租bean
 */
public class OfficeHouse extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 名称
     */
    private String name;

    /**
     * 房源描述
     */
    private  String description;

    /**
     * 面积
     */
    private Double coveredArea;

    /**
     * 楼层
     */
    private Integer floorLevels;

    /**
     * 总楼层
     */
    private Integer totalFloor;

    /**
     * 工位数
     */
    private Integer locationNumber;

    /**
     * 物业费
     */
    private Double propertyFee;

    /**
     *门牌号
     */
    private String officeNumber;

    /**
     *地铁
     */
    private String subway;

    /**
     * 租金
     */
    private Double monthlyRent;

    /**
     * 类型：1纯写字楼2商住楼3商业综合体楼4酒店写字楼
     */
    private Integer type;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 区域id
     */
    private Integer districtId;

    /**
     * 街道id
     */
    private Integer areaId;

    /**
     * 地址
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
     * 是否实勘
     */
    private Integer houseReal;

    /**
     * 审核回复
     */
    private String statusContent;

    /**
     * 审核状态
     */
    private Integer checkStatus;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 出租方案(1.押一付三2押一付一3押一付二4押二付一5半年付6年付7面议)
     */
    private Integer rentWay;

    /**
     * 性别
     */
    private Integer sex;

    private String contacts;

    private String telephone;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 入驻时间
     */
    private String rentTime;

    /**
     * 装修情况
     */
    private String fixture;

    /**
     * 关联小气id
     */
    private Integer villageId;

    /**
     * 上架状态0未上架1上架2下架
     */
    private Integer flag;

    private Integer isDel;

    private Integer isLift;

    public Integer getIsLift() {
        return isLift;
    }

    public void setIsLift(Integer isLift) {
        this.isLift = isLift;
    }

    private List<String> officeHouseImgList;

    public List<String> getOfficeHouseImgList() {
        return officeHouseImgList;
    }

    public void setOfficeHouseImgList(List<String> officeHouseImgList) {
        this.officeHouseImgList = officeHouseImgList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
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

    public Integer getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(Integer locationNumber) {
        this.locationNumber = locationNumber;
    }

    public Double getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(Double propertyFee) {
        this.propertyFee = propertyFee;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
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

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public String getType_() {
        if(type!=null){
            if(type==1){
                return "纯写字楼";
            }else if(type==2){
                return "商住楼";
            }else if(type==3){
                return "商业综合体楼";
            }else  if(type==4){
                return "酒店写字楼";
            }
        }
        return "";
    }
    public String getCreateTime_() {
        return DateUtils.formatDateTime(createtime);
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
    public String getRentWay_(){
        if(rentWay!=null){
            if(rentWay==1){
                return "押一付三";
            }else if(rentWay==2){
                return "押一付一";
            }else if(rentWay==3){
                return "押一付二";
            }else if(rentWay==4){
                return "押二付一";
            }else if(rentWay==5){
                return "半年付";
            }else if(rentWay==6){
                return "年付";
            }else if(rentWay==7){
                return "面议";
            }
        }
        return "";
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getIsDel_(){
        if(this.isDel!=null){
            if(this.isDel==0){
                return "正常";
            }else{
                return "删除";
            }
        }
        return "";
    }
}
