package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * 用户黑名单
 */
public class UserScreen extends Page {

    private Integer id;

    private Integer userId;

    private Integer screenUserId;

    private Date createtime;

    private String nickname;

    private String userPhoto;

    private String signname;

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

    public Integer getScreenUserId() {
        return screenUserId;
    }

    public void setScreenUserId(Integer screenUserId) {
        this.screenUserId = screenUserId;
    }

    @JsonIgnore
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getSignname() {
        return signname;
    }

    public void setSignname(String signname) {
        this.signname = signname;
    }
}
