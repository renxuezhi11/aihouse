package com.aihouse.aihousedao.bean;

import com.aihouse.aihousedao.Page;

/**
 * 系统注册登录
 */
public class SysLoginRegisterSetting extends Page{

    private Integer id;

    private Integer isNeedSpread;

    private Integer isNeedCheck;

    private Integer isNeedLogin;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsNeedSpread() {
        return isNeedSpread;
    }

    public void setIsNeedSpread(Integer isNeedSpread) {
        this.isNeedSpread = isNeedSpread;
    }

    public Integer getIsNeedCheck() {
        return isNeedCheck;
    }

    public void setIsNeedCheck(Integer isNeedCheck) {
        this.isNeedCheck = isNeedCheck;
    }

    public Integer getIsNeedLogin() {
        return isNeedLogin;
    }

    public void setIsNeedLogin(Integer isNeedLogin) {
        this.isNeedLogin = isNeedLogin;
    }
}
