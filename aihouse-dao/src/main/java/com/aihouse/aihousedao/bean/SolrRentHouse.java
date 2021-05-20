package com.aihouse.aihousedao.bean;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * solr查询结果bean 租房
 */
public class SolrRentHouse {

    private Integer id;

    private String loc;

    private String villageName;

    private Integer cityId;

    private Integer areaId;

    private Integer streesId;

    private Integer room;

    private Integer hall;

    private Integer toilet;

    private Double coveredArea;

    private Integer floor;

    private Date createtime;

    private String picture;

    private Double rentFee;

    private Integer houseReal;

    private String title;

    private String orientations;

    private Integer rentMode;

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

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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

    public Double getRentFee() {
        return rentFee;
    }

    public void setRentFee(Double rentFee) {
        this.rentFee = rentFee;
    }

    public Integer getHouseReal() {
        return houseReal;
    }

    public void setHouseReal(Integer houseReal) {
        this.houseReal = houseReal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public Integer getRentMode() {
        return rentMode;
    }

    public void setRentMode(Integer rentMode) {
        this.rentMode = rentMode;
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
