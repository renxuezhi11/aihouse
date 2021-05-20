package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 楼盘点评bean
 */
public class NewHouseComment extends Page {

    /**
     * id
     */
    private Integer id;
    /**
     * 关联楼盘id
     */
    private Integer newHouseId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 价格评分
     */
    private Integer priceScore;
    /**
     * 地段评分
     */
    private Integer placeScore;

    /**
     * 配套评分
     */
    private Integer matingScore;

    /**
     * 交通评分
     */
    private Integer trafficScore;

    /**
     * 环境评分
     */
    private Integer envScore;

    /**
     * 评分
     */
    private Double score;


    /**
     * 点评内容
     */
    private String content;

    /**
     * 图片路径组合
     */
    private String imgUrl;

    /**
     * 发布时间
     */
    private Date createtime;

    /**
     * 状态（0未审核，1审核通过，2审核不通过）
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer thumbsUp;

    /**
     * 审核回复
     */
    private String statusContent;

    private String userPhoto;

    private String username;


    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNewHouseId() {
        return newHouseId;
    }

    public void setNewHouseId(Integer newHouseId) {
        this.newHouseId = newHouseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPriceScore() {
        return priceScore;
    }

    public void setPriceScore(Integer priceScore) {
        this.priceScore = priceScore;
    }

    public Integer getPlaceScore() {
        return placeScore;
    }

    public void setPlaceScore(Integer placeScore) {
        this.placeScore = placeScore;
    }

    public Integer getMatingScore() {
        return matingScore;
    }

    public void setMatingScore(Integer matingScore) {
        this.matingScore = matingScore;
    }

    public Integer getTrafficScore() {
        return trafficScore;
    }

    public void setTrafficScore(Integer trafficScore) {
        this.trafficScore = trafficScore;
    }

    public Integer getEnvScore() {
        return envScore;
    }

    public void setEnvScore(Integer envScore) {
        this.envScore = envScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public void setStatusContent(String statusContent) {
        this.statusContent = statusContent;
    }
}
