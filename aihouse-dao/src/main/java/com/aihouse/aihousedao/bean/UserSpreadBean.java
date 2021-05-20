package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

public class UserSpreadBean extends Page {

    private Integer id;

    private String nickname;

    private Integer total;

    private Integer today;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Integer getTotal() {
        return total;
    }

    @Override
    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }
}
