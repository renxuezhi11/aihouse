package com.aihouse.aihouseapp.utils;

import com.aihouse.aihousedao.bean.Users;

import java.io.Serializable;

public class SessionUser implements Serializable {

    public static final String FLAG="token";

    private Users users;

    private String token;

    private String deviceId;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}
