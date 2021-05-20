package com.aihouse.aihouseapp.utils;

import IM.IMUtils;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ImTokenUtils {
    public static String getToken(RedisUtil redisUtil){
        String token=null;
        if(redisUtil.get("imtoken")!=null){
            token=(String)redisUtil.get("imtoken");
        }else{
            String result= IMUtils.getToken();
            Map map1= JSON.parseObject(result,HashMap.class);
            token="Bearer "+map1.get("access_token").toString();
            Integer expirt=Integer.parseInt(map1.get("expires_in").toString());
            redisUtil.set("imtoken",token,expirt);
        }
        return token;
    }
}
