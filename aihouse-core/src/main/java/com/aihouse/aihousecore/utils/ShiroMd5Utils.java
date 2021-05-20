package com.aihouse.aihousecore.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroMd5Utils {
    private final static int hashIterations=1;
    public static String ShiroMd5(String passwrod){
        String result = new Md5Hash(passwrod,null,hashIterations).toString();
        return result;
    }
}
