package IM;

import com.aihouse.aihousecore.utils.HttpClient;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 环信im工具类
 */
public class IMUtils {

    /**
     * 注册用户方法
     * @param username
     * @param password
     * @return
     */
    public static String registerUser(String username,String password,String token){
        String url=IMConstant.IMUrl+"/"+IMConstant.orgname+"/"+IMConstant.appname+"/users";
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        String result=HttpClient.doPostToken(url, JSON.toJSONString(map),token);
        System.out.println(result);
        return result;
    }

    /**
     * 获取环信 token
     * @return
     */
    public static String getToken(){
        String url=IMConstant.IMUrl+"/"+IMConstant.orgname+"/"+IMConstant.appname+"/token";
        Map<String,String> map=new HashMap<>();
        map.put("grant_type","client_credentials");
        map.put("client_id",IMConstant.client_id);
        map.put("client_secret",IMConstant.client_secret);
        String result=HttpClient.doPost(url, JSON.toJSONString(map));
        System.out.println(result);
        return result;
    }


    /**
     * 获取用户的好友列表
     * @param owner_username
     * @param token
     * @return
     */
    public static String getUsertFriends(String owner_username,String token){
        String url=IMConstant.IMUrl+"/"+IMConstant.orgname+"/"+IMConstant.appname+"/users/"+owner_username+"/contacts/users";
        String result=HttpClient.doGetToken(url, token,"GET");
        return result;
    }


    /**
     * 删除用户好友
     * @param owner_username
     * @param friend_username
     * @param token
     * @return
     */
    public static String deleteUserFriends(String owner_username,String friend_username,String token){
        String url=IMConstant.IMUrl+"/"+IMConstant.orgname+"/"+IMConstant.appname+"/users/"+owner_username+"/contacts/users/"+friend_username;
        System.out.println(url);
        String result=HttpClient.doGetToken(url, token,"DELETE");
        return result;
    }
}
