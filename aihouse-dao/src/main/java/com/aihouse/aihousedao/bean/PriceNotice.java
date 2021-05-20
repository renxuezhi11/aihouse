package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

import java.util.Date;

/**
 * 价格通知bean
 */
public class PriceNotice extends Page {

    /**
     * id
     */
    private Integer id;

    /**
     * 房源id
     */
    private Integer houseId;

    /**
     * 房源类型1新房2二手房3租房4商铺出租5写字楼出租
     */
    private Integer houseType;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 通知手机号
     */
    private String telephone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
