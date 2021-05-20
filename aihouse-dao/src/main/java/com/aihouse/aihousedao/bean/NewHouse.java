package com.aihouse.aihousedao.bean;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 *x新房
 */
public class NewHouse extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     * 楼盘名称
     */
    private String name;

    /**
     *别名
     */
    private String alias;



    /**
     * 省份
     */

    private Integer provinceId;
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
    private Integer streesId;

    /**
     * 地址
     */
    private String address;

    /**
     * 均价
     */
    private BigDecimal averagePrice;

    /**
     * 均价描述
     */
    private String averagePriceDesc;

    /**
     * 首付比例
     */
    private String downPaymentScale;

    /**
     * 产品类型
     */
    private String houseType;

    /**
     * 建筑类型
     */

    private String coveredType;

    /**
     * 项目特色
     */
    private String feature;

    /**
     * 项目简介
     */
    private String profile;

    /**
     * 装修情况(毛坯，精装修，简装修，中装修，豪华装修)
     */
    private String fixture;

    /**
     * 产权年限
     */
    private Integer propertyYear;

    /**
     * 开发商
     */
    private String developer;

    /**
     * 销售进度(0在售1待售2售完)
     */
    private Integer sellStage;

    /**
     * 售楼地址
     */
    private String salesOfficeAddress;

    /**
     * 咨询电话
     */
    private String telephone;

    /**
     * 周边设施
     */
    private String around;

    /**
     * 占地面积单位m2
     */
    private Double floorSpace;
    /**
     * 建筑面积单位m2
     */
    private Double coveredArea;

    /**
     * 容积率
     */
    private Double plotRatio;

    /**
     * 绿化率
     */
    private Double greeningRate;

    /**
     * 停车位地上
     */
    private Integer carPlaceUp;

    /**
     * 停车位地下
     */
    private Integer carPlaceDown;

    /**
     * 楼栋数
     */
    private Integer building;

    /**
     * 总户数
     */
    private Integer total_house;

    /**
     * 物业公司
     */
    private String propertyCompany;

    /**
     * 物业费
     */
    private Double propertyFee;

    /**
     * 添加时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 经度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 上架状态0未上架1上架2下架
     */
    private Integer status;

    /**
     * 浏览量
     */
    private Integer pageView;

    /**
     * 评分
     */
    private Double score;

    /**
     * 评论数
     */
    private Integer comment_count;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public Integer getStreesId() {
        return streesId;
    }

    public void setStreesId(Integer streesId) {
        this.streesId = streesId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getAveragePriceDesc() {
        return averagePriceDesc;
    }

    public void setAveragePriceDesc(String averagePriceDesc) {
        this.averagePriceDesc = averagePriceDesc;
    }

    public String getDownPaymentScale() {
        return downPaymentScale;
    }

    public void setDownPaymentScale(String downPaymentScale) {
        this.downPaymentScale = downPaymentScale;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getCoveredType() {
        return coveredType;
    }

    public void setCoveredType(String coveredType) {
        this.coveredType = coveredType;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public Integer getPropertyYear() {
        return propertyYear;
    }

    public void setPropertyYear(Integer propertyYear) {
        this.propertyYear = propertyYear;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public Integer getSellStage() {
        return sellStage;
    }

    public void setSellStage(Integer sellStage) {
        this.sellStage = sellStage;
    }

    public String getSalesOfficeAddress() {
        return salesOfficeAddress;
    }

    public void setSalesOfficeAddress(String salesOfficeAddress) {
        this.salesOfficeAddress = salesOfficeAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAround() {
        return around;
    }

    public void setAround(String around) {
        this.around = around;
    }

    public Double getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(Double floorSpace) {
        this.floorSpace = floorSpace;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Double getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(Double plotRatio) {
        this.plotRatio = plotRatio;
    }

    public Double getGreeningRate() {
        return greeningRate;
    }

    public void setGreeningRate(Double greeningRate) {
        this.greeningRate = greeningRate;
    }

    public Integer getCarPlaceUp() {
        return carPlaceUp;
    }

    public void setCarPlaceUp(Integer carPlaceUp) {
        this.carPlaceUp = carPlaceUp;
    }

    public Integer getCarPlaceDown() {
        return carPlaceDown;
    }

    public void setCarPlaceDown(Integer carPlaceDown) {
        this.carPlaceDown = carPlaceDown;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public Integer getTotal_house() {
        return total_house;
    }

    public void setTotal_house(Integer total_house) {
        this.total_house = total_house;
    }

    public String getPropertyCompany() {
        return propertyCompany;
    }

    public void setPropertyCompany(String propertyCompany) {
        this.propertyCompany = propertyCompany;
    }

    public Double getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(Double propertyFee) {
        this.propertyFee = propertyFee;
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

    public String getUpdatetime_() {
        return DateUtils.formatDateTime(updatetime);
    }
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public Integer getStatus() {
        return status;
    }
    public String getStatus_() {
        if(status!=null) {
            if (status==0){
                return "未上架";
            }else if (status == 1) {
                return "上架";
            } else if (status == 2) {
                return "已下架";
            }
        }
        return "";
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getSellStage_(){
        if(sellStage!=null){
            if(sellStage==0){
                return "在售";
            }else if(sellStage==1){
                return "待售";
            }else if(sellStage==2){
                return "售完";
            }
        }
        return "";
    }
}
