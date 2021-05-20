package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * 租房
 */
public class RentHouse extends Page {

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 区域id
     */
    private Integer areaId;
    /**
     *街道id
     */
    private Integer streetsId;

    /**
     *联系人
     */
    private String contacts;

    /**
     *手机号
     */
    private String telephone;

    /**
     *建筑类型
     */
    private String coveredType;

    /**
     * 房屋类型
     */
    private String houseType;

    /**
     *室
     */
    private Integer room;

    /**
     *厅
     */
    private Integer hall;

    /**
     *厨房
     */
    private Integer cookRoom;

    /**
     *卫生间
     */
    private Integer toilet;

    /**
     *阳台
     */
    private Integer gallery;

    /**
     *建筑面积
     */
    private Double coveredArea;

    /**
     *电梯
     */
    private Integer isLift;

    /**
     *楼层
     */
    private Integer floor;

    /**
     *总楼层
     */
    private Integer totalFloor;

    /**
     *小区id
     */
    private Integer villageId;

    /**
     *小区名称
     */
    private String villageName;

    /**
     *房源描述
     */
    private String houseDesc;

    /**
     *经度
     */
    private String lng;

    /**
     *纬度
     */
    private String lat;
    /**
     *地址
     */
    private String address;
    /**
     *配套
     */
    private String support;
    /**
     *装修情况
     */
    private String fixture;

    /**
     *朝向
     */
    private String orientations;
    /**
     *标题
     */
    private String title;
    /**
     *房间号
     */
    private String houseNumber;

    /**
     *出租方式1整租2合租
     */
    private Integer rentMode;

    /**
     *创建时间
     */
    private Date createtime;

    /**
     *跟新时间
     */
    private Date updatetime;

    /**
     *审核状态0未审核，1审核通过，2审核不通过
     */
    private Integer status;
    /**
     *合租人数
     */
    private Integer rentPeople;
    /**
     *合租房间
     */
    private Integer rentRoom;

    /**
     *性别要求
     */
    private Integer rentSex;

    /**
     *租金
     */
    private Integer rentFee;

    /**
     *入驻时间
     */
    private String rentTime;

    /**
     *出租方案
     */
    private Integer rentWay;

    /**
     *经纪人id
     */
    private Integer broker;

    /**
     *是否实勘0无1已
     */
    private Integer rentReal;
    /**
     *审核回复
     */
    private String statusContent;

    /**
     *上架状态0未上架1上架2下架
     */
    private Integer flag;

    /**
     * 删除状态0正常1删除
     */
    private Integer isDel;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 0业主1经纪人
     */
    private Integer userType;

    private List<String> rentHouseImgList;

    public List<String> getRentHouseImgList() {
        return rentHouseImgList;
    }

    public void setRentHouseImgList(List<String> rentHouseImgList) {
        this.rentHouseImgList = rentHouseImgList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStreetsId() {
        return streetsId;
    }

    public void setStreetsId(Integer streetsId) {
        this.streetsId = streetsId;
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

    public String getCoveredType() {
        return coveredType;
    }

    public void setCoveredType(String coveredType) {
        this.coveredType = coveredType;
    }

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

    public Integer getCookRoom() {
        return cookRoom;
    }

    public void setCookRoom(Integer cookRoom) {
        this.cookRoom = cookRoom;
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

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Integer getIsLift() {
        return isLift;
    }

    public void setIsLift(Integer isLift) {
        this.isLift = isLift;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getHouseDesc() {
        return houseDesc;
    }

    public void setHouseDesc(String houseDesc) {
        this.houseDesc = houseDesc;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getRentMode() {
        return rentMode;
    }
    public String getRentMode_() {
        if(rentMode!=null) {
            if (rentMode==1){
                return "整租";
            } else if (rentMode == 2) {
                return "合租";
            }
        }
        return "";
    }
    public String getHouseType_(){
        return room+"室"+hall+"厅"+toilet+"卫";
    }
    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setRentMode(Integer rentMode) {
        this.rentMode = rentMode;
    }
    @JsonIgnore
    public Date getCreatetime() {
        return createtime;
    }
    public String getCreatetime_() {
        return DateUtils.formatDateTime(createtime);
    }
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    @JsonIgnore
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRentPeople() {
        return rentPeople;
    }

    public void setRentPeople(Integer rentPeople) {
        this.rentPeople = rentPeople;
    }

    public Integer getRentRoom() {
        return rentRoom;
    }

    public void setRentRoom(Integer rentRoom) {
        this.rentRoom = rentRoom;
    }

    public Integer getRentSex() {
        return rentSex;
    }
    public String getRentSex_() {
        if(rentSex!=null){
            if(rentSex==0){
                return "男女不限";
            }else if(rentSex==1){
                return "男";
            }else if(rentSex==2){
                return "女";
            }
        }
        return "";
    }

    public String getRentRoom_(){
        if(rentRoom!=null){
            if(rentRoom==1){
                return "主卧";
            }else if(rentRoom==2){
                return "次卧";
            }
        }
        return "";
    }
    public void setRentSex(Integer rentSex) {
        this.rentSex = rentSex;
    }

    public Integer getRentFee() {
        return rentFee;
    }

    public void setRentFee(Integer rentFee) {
        this.rentFee = rentFee;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public Integer getBroker() {
        return broker;
    }

    public void setBroker(Integer broker) {
        this.broker = broker;
    }

    public Integer getRentReal() {
        return rentReal;
    }

    public void setRentReal(Integer rentReal) {
        this.rentReal = rentReal;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
    public String getStatus_() {
        if(status!=null) {
            if (status==0){
                return "未审核";
            }else if (status == 1) {
                return "审核通过";
            } else if (status == 2) {
                return "审核不通过";
            }
        }
        return "";
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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }


    public String getIsDel_() {
        if(this.isDel!=null){
            if(isDel==0){
                return "正常";
            }else{
                return "删除";
            }
        }else{
            return "";
        }

    }
}

