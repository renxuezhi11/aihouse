package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 写字楼出租图片表
 */
public class OfficeHouseImg extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     * 图片路径
     */
    private String imgUrl;

    /**
     * 图片类型
     */
    private Integer imgType;

    /**
     * 图片描述
     */
    private String imgDesc;

    /**
     * 商铺id
     */
    private Integer officeID;

    /**
     * 上传时间
     */
    private Date createtime;

    /**
     * 是否删除
     */
    private Integer isDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer idDel) {
        this.isDel = idDel;
    }

    public Integer getOfficeID() {
        return officeID;
    }

    public void setOfficeID(Integer officeID) {
        this.officeID = officeID;
    }
}
