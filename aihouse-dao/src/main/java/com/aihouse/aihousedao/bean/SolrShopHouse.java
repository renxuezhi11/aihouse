package com.aihouse.aihousedao.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * solr 查询结果bean 商铺
 */
public class SolrShopHouse implements Serializable {

    private Integer id;

    private String shopName;

    private String loc;

    private String title;

    private Integer cityId;

    private Integer areaId;

    private Integer streesId;

    private Double coveredArea;

    private Integer type;

    private Integer floorLevels;

    private Integer houseReal;

    private Double monthlyRent;

    private List<String> feature;

    private String fixture;

    private Integer status;

    private List<String> operation;

    private String areaName;

    private String streetsName;

    private String cityName;

    private Integer PageView;

    private Integer countCellect;

    private String userName;

    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFloorLevels() {
        return floorLevels;
    }

    public void setFloorLevels(Integer floorLevels) {
        this.floorLevels = floorLevels;
    }

    public Integer getHouseReal() {
        return houseReal;
    }

    public void setHouseReal(Integer houseReal) {
        this.houseReal = houseReal;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public List<String> getFeature() {
        return feature;
    }

    public void setFeature(List<String> feature) {
        this.feature = feature;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
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
}
