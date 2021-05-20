package com.aihouse.aihouseapp.utils;

public class AppRuntimeException extends  Exception {
    private int code;
    public AppRuntimeException(int code,String message){
        super(message);
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
