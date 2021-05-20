package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 租房图片表
 */
public class RentHouseImg extends Page {

    /**
     *id
     */
    private  Integer id;

    /**
     *图片路径
     */
    private String imgUrl;

    /**
     *图片描述
     */
    private String imgDesc;

    /**
     *房源id
     */
    private Integer rentHouse;

    /**
     *上传时间
     */
    private Date createtime;

    /**
     *是否删除
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

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getRentHouse() {
        return rentHouse;
    }

    public void setRentHouse(Integer rentHouse) {
        this.rentHouse = rentHouse;
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

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
