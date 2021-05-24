package com.aihouse.aihousedao.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * solr查询结果bean 二手房
 */
public class SolrSecondHouse implements Serializable {

    private Integer id;

    private String villageName;

    private String feature;

    private Integer cityId;

    private Integer areaId;

    private Integer streesId;

    private Double price;

    private Integer room;

    private Integer hall;

    private Integer toilet;

    private Double coveredArea;

    private String orientations;

    private String houseType;

    private String floor;

    private String fixture;

    private String title;

    private Integer houseReal;

    private String createtime;

    private String picture;

    private String areaName;

    private String streetsName;

    private String cityName;

    private Integer PageView;

    private Integer countCellect;

    private String userName;

    private Integer unitPrice;

    private Integer isTop;

    private Integer isSale;

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getToilet() {
        return toilet;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getHouseReal() {
        return houseReal;
    }

    public void setHouseReal(Integer houseReal) {
        this.houseReal = houseReal;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreetsName() {
        return streetsName;
    }

    public void setStreetsName(String streetsName) {
        this.streetsName = streetsName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getPageView() {
        return PageView;
    }

    public void setPageView(Integer pageView) {
        PageView = pageView;
    }

    public Integer getCountCellect() {
        return countCellect;
    }

    public void setCountCellect(Integer countCellect) {
        this.countCellect = countCellect;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsSale() {
        return isSale;
    }

    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }
}
