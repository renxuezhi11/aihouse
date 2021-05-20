package com.aihouse.aihousedao.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * solr 查询结果bean 写字楼
 */
public class SolrOfficeHouse implements Serializable {
    private Integer id;

    private String loc;

    private String name;

    private Integer cityId;

    private Integer areaId;

    private Integer streesId;

    private String title;

    private Integer type;

    private Double coveredArea;

    private Double monthlyRent;

    private Date createtime;

    private String picture;

    private Integer floor;

    private Integer houseReal;

    private List<String> fixture;

    private String areaName;

    private String streetsName;

    private String cityName;

    private Integer PageView;

    private Integer countCellect;

    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getCoveredArea() {
        return coveredArea;
    }

    public void setCoveredArea(Double coveredArea) {
        this.coveredArea = coveredArea;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getHouseReal() {
        return houseReal;
    }

    public void setHouseReal(Integer houseReal) {
        this.houseReal = houseReal;
    }

    public List<String> getFixture() {
        return fixture;
    }

    public void setFixture(List<String> fixture) {
        this.fixture = fixture;
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
