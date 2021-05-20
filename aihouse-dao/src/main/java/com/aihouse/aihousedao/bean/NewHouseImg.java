package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 楼盘图片
 */
public class NewHouseImg extends Page{

    /**
     *id
     */
    private Integer id;

    /**
     *图片类型（1效果图，2交通图，3实景图，4户型图，5样板间，6周边配套）
     */
    private Integer imgType;

    /**
     *图片路径
     */
    private String imgUrl;

    /**
     *图片描述
     */
    private String imgDesc;

    /**
     *关联楼盘表id
     */
    private Integer newHouseId;

    /**
     *上传时间
     */
    private Date createtime;

    /**
     *删除状态0正常1删除
     */
    private Integer IsDel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getNewHouseId() {
        return newHouseId;
    }

    public void setNewHouseId(Integer newHouseId) {
        this.newHouseId = newHouseId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsDel() {
        return IsDel;
    }

    public void setIsDel(Integer isDel) {
        IsDel = isDel;
    }
}
