package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;


/**
 * 楼盘限制人数，合伙人入驻楼盘数量
 */
public class HouseLimit extends Page {

    private Integer id;

    private Integer limitNewHouse;

    private Integer limitBroker;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimitNewHouse() {
        return limitNewHouse;
    }

    public void setLimitNewHouse(Integer limitNewHouse) {
        this.limitNewHouse = limitNewHouse;
    }

    public Integer getLimitBroker() {
        return limitBroker;
    }

    public void setLimitBroker(Integer limitBroker) {
        this.limitBroker = limitBroker;
    }
}
