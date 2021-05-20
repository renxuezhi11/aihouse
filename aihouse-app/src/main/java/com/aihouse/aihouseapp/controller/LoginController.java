package com.aihouse.aihouseapp.controller;

import IM.IMUtils;
import com.aihouse.aihouseapp.utils.ImTokenUtils;
import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.*;
import com.aihouse.aihousedao.bean.SysLoginRegisterSetting;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.SysLoginRegisterSettingService;
import com.aihouse.aihouseservice.UserLoginLogService;
import com.aihouse.aihouseservice.users.UsersService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * app登录注册接口
 */
@CrossOrigin
@RestController
public class LoginController {

    @Resource
    private UsersService usersService;

    @Resource
    private RedisUtil redisUtil;

    @Value("${userphoto}")
    private String userPhoto;

    @Resource
    private UserLoginLogService userLoginLogService;

    @Autowired
    private SysLoginRegisterSettingService loginRegisterSettingService;

    @RequestMapping("app/getSettingInfo")
    public DataRes getLoginRegisterSet(){
        SysLoginRegisterSetting sysLoginRegisterSetting =new SysLoginRegisterSetting();
        List<SysLoginRegisterSetting> list=loginRegisterSettingService.selectAll(sysLoginRegisterSetting);
        return DataRes.success(list.get(0));
    }
    /**
     * 登录接口，手机号，验证码，密码登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("app/login")
    public DataRes login(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("type")!=null){
            if(request.getParameter("type").toString().equals("1")){//手机快捷登录
                 if(redisUtil.get(request.getParameter("telephone"))!=null){
                        String code=(String)redisUtil.get(request.getParameter("telephone"));
                        if(code.equals(request.getParameter("code"))){
                            Users users=new Users();
                            users.setTelephone(request.getParameter("telephone"));
                            List<Users> list=usersService.selectAll(users);
                            int role=0;
                            if(list.size()==0){//用户不存在,保存user
//                                String username="ai"+randomUtils.createData(10);
                                String username=randomUtils.createData(10);
                                users.setUsername(username);
                                users.setImAccount(username);
                                users.setNickname(username);
                                users.setUserphoto(userPhoto);
                                users.setImPassword("123456789");
                                //this.register(username,users.getImPassword());
                                users.setIsCertification(0);
                                users.setStatus(0);
                                users.setRole(0);
                                usersService.insert(users);
                            }else {
                                users=list.get(0);
                                if(users.getStatus()==1){
                                    return DataRes.error(ResponseCode.USER_LOGIN_ERROR);
                                }
                                if(users.getRole()!=null){
                                    role=users.getRole();
                                }
                            }
                            String token="";
                            if(redisUtil.get(RedisConstants.USER_TOKEN+users.getId())!=null){
                                SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + users.getId());
                                if (request.getParameter("deviceId") != null&&!request.getParameter("deviceId").equals("1111111111111111111111111111111")) {
                                    sessionUser.setDeviceId(request.getParameter("deviceId"));
                                }
                                sessionUser.setUsers(users);
                                token=sessionUser.getToken();
                                redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                            }else{
                                token= UUID.randomUUID().toString()+ SessionUser.FLAG+users.getId();
                                SessionUser sessionUser=new SessionUser();
                                sessionUser.setToken(token);
                                sessionUser.setUsers(users);
                                if(request.getParameter("deviceId")!=null&&!request.getParameter("deviceId").equals("1111111111111111111111111111111")){
                                    sessionUser.setDeviceId(request.getParameter("deviceId"));
                                }
                                redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                            }

                            Map<String,Object> map=new HashMap<>();
                            map.put("token",token);
                            map.put("role",role);
                            //map.put("imcount",users.getImAccount());
                            //map.put("impassword",users.getImPassword());
                            //userLoginLogService.insertLog(users.getId(),IpUtils.getRealIp(request),request.getParameter("deviceId")!=null?request.getParameter("deviceId"):"");
                            return DataRes.success(map);
                        }else{
                            return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_ERROR);
                        }
                 }else{
                     return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_EXPIRED);
                 }

            }else if(request.getParameter("type").toString().equals("2")){//手机密码登录
                if(request.getParameter("telephone")!=null&&request.getParameter("password")!=null){
                    Users users=new Users();
                    users.setTelephone(request.getParameter("telephone"));
                    users.setPassword(request.getParameter("password"));
                    List<Users> list=usersService.selectByCondition(users);
                    if(list.size()==0){
                       return DataRes.error(ResponseCode.NO_ACCOUNT);
                    }else{
                        users=list.get(0);
                        if(users.getStatus()==1){
                            return DataRes.error(ResponseCode.USER_LOGIN_ERROR);
                        }
                        String token="";
                        SessionUser sessionUser=null;
                        if(redisUtil.get(RedisConstants.USER_TOKEN+users.getId())!=null){
                            sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + users.getId());
                            if (request.getParameter("deviceId") != null&&!request.getParameter("deviceId").equals("1111111111111111111111111111111")) {
                                sessionUser.setDeviceId(request.getParameter("deviceId"));
                            }
                            token=sessionUser.getToken();
                            sessionUser.setUsers(users);
                            redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                        }else {
                            token = UUID.randomUUID().toString() + SessionUser.FLAG + users.getId();
                            sessionUser = new SessionUser();
                            sessionUser.setToken(token);
                            sessionUser.setUsers(users);
                            if (request.getParameter("deviceId") != null&&!request.getParameter("deviceId").equals("1111111111111111111111111111111")) {
                                sessionUser.setDeviceId(request.getParameter("deviceId"));
                            }
                            redisUtil.set(RedisConstants.USER_TOKEN + sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                        }
                        Map<String,Object> map=new HashMap<>();
                        map.put("token",token);
                        map.put("role",sessionUser.getUsers().getRole());
                        //map.put("imcount",users.getImAccount());
                        //map.put("impassword",users.getImPassword());
                        //userLoginLogService.insertLog(users.getId(),IpUtils.getRealIp(request),request.getParameter("deviceId")!=null?request.getParameter("deviceId"):"");
                        return DataRes.success(map);
                    }
                }
            }else if (request.getParameter("type").toString().equals("3")){//token登录，验证token有效性
                if(request.getParameter("token")!=null){
                    System.out.println(request.getParameter("token"));
                    String userId=request.getParameter("token").split(SessionUser.FLAG)[1];
                    if(redisUtil.get(RedisConstants.USER_TOKEN+userId)!=null){
                        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
//                        System.out.println(sessionUser.getToken());
                        if(request.getParameter("deviceId")!=null){
                            //System.out.println(sessionUser.getDeviceId());
                            //System.out.println(request.getParameter("deviceId"));
                            if(sessionUser.getDeviceId().equals(request.getParameter("deviceId"))||request.getParameter("deviceId").equals("1111111111111111111111111111111")){
                                Users users=new Users();
                                users.setId(Integer.parseInt(userId));
                                users=usersService.selectByPrimaryKey(users);
                                if(users.getStatus()==1){
                                    return DataRes.error(ResponseCode.USER_LOGIN_ERROR);
                                }
                                Map<String,Object> map=new HashMap<>();
                                map.put("token",sessionUser.getToken());
                                map.put("role",sessionUser.getUsers().getRole());
                                //map.put("imcount",sessionUser.getUsers().getImAccount());
                                //map.put("impassword",sessionUser.getUsers().getImPassword());
                                userLoginLogService.insertLog(users.getId(),IpUtils.getRealIp(request),request.getParameter("deviceId")!=null?request.getParameter("deviceId"):"");
                                return DataRes.success(map);
                            }else{
                                return DataRes.error(ResponseCode.LOGIN_TOKEN_DEVICEID_ERRROR);
                            }
                        }
                    }else{
                        return DataRes.error(ResponseCode.LOGIN_TOKEN_ERRROR);
                    }
                }else{
                    return DataRes.error(ResponseCode.DATA_ERROR);
                }

            }
        }
        return DataRes.error(ResponseCode.DATA_ERROR);
    }

    /**
     * 绑定用户的身份
     * @param role
     * @param request
     * @return
     */
    @RequestMapping("app/login/bangUserRole")
    public DataRes bangUserRole(Integer role,HttpServletRequest request){
        if(role!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            Users users=new Users();
            users.setId(Integer.parseInt(userId));
            users.setRole(role);
            usersService.update(users);
            SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
            sessionUser.getUsers().setRole(role);
            redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(),sessionUser, RedisConstants.EXPIRE_30_DAY);
            return DataRes.success("绑定成功");

        }else {
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     *第三方登录
     * @param request
     * @param response
     * @param openId
     * @param type
     * @return
     */
    @RequestMapping("app/thirdPartyLogin")
    public DataRes thirdPartyLogin(HttpServletRequest request,HttpServletResponse response,String openId,Integer type){
        if(openId!=null){
            Users users=new Users();
            if(type==1){
                users.setWxaccount(openId);
            }else {
                users.setAliaccount(openId);
            }
            List<Users> list=usersService.selectByCondition(users);
            if(list!=null&&list.size()==1){//已绑定
                users=list.get(0);
                if(users.getStatus()==1){
                    return DataRes.error(ResponseCode.USER_LOGIN_ERROR);
                }
                String token="";
                SessionUser sessionUser=null;
                if(redisUtil.get(RedisConstants.USER_TOKEN+users.getId())!=null){
                     sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + users.getId());
                    token=sessionUser.getToken();
                    if (request.getParameter("deviceId") != null) {
                        System.out.println("th=="+request.getParameter("deviceId"));
                        sessionUser.setDeviceId(request.getParameter("deviceId"));
                    }
                    redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                }else {
                    token = UUID.randomUUID().toString() + SessionUser.FLAG + users.getId();
                    sessionUser = new SessionUser();
                    sessionUser.setToken(token);
                    sessionUser.setUsers(users);
                    if (request.getParameter("deviceId") != null) {
                        sessionUser.setDeviceId(request.getParameter("deviceId"));
                    }
                    redisUtil.set(RedisConstants.USER_TOKEN + sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                }
                Map<String,Object> map=new HashMap<>();
                map.put("token",sessionUser.getToken());
                map.put("role",sessionUser.getUsers().getRole());
                map.put("imcount",sessionUser.getUsers().getImAccount());
                map.put("impassword",sessionUser.getUsers().getImPassword());
//                userLoginLogService.insertLog(users.getId(),IpUtils.getRealIp(request),request.getParameter("deviceId")!=null?request.getParameter("deviceId"):"");
                return DataRes.success(map);
            }else{//未绑定
                return DataRes.error(ResponseCode.NO_BANG_PHONE);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 第三方登录绑定手机号
     * @param request
     * @param response
     * @param telephone
     * @param code
     * @param type
     * @param openId
     * @param deviceId
     * @return
     */
    @RequestMapping("app/bangPhone")
        public DataRes bangPhone(HttpServletRequest request,HttpServletResponse response,String telephone,String code,Integer type,String openId,String deviceId){
        if(telephone!=null &&!telephone.equals("")){
            if(redisUtil.get(telephone)!=null) {
                String vcode = (String) redisUtil.get(request.getParameter("telephone"));
                if (vcode.equals(code)) {
                    Users users = new Users();
                    users.setTelephone(telephone);
                    List<Users> list=usersService.selectByCondition(users);
                    if(list!=null&&list.size()==1) {//手机号已注册
                        users=list.get(0);
                        if(users.getStatus()==1){
                            return DataRes.error(ResponseCode.USER_LOGIN_ERROR);
                        }
                        Users users1=new Users();
                        users1.setId(users.getId());
                        if (type == 1) {
                            users1.setWxaccount(openId);
                            users.setWxaccount(openId);
                        } else {
                            users1.setAliaccount(openId);
                            users.setAliaccount(openId);
                        }
                        usersService.update(users1);
                    }else{
                        if (type == 1) {
                            users.setWxaccount(openId);
                        } else {
                            users.setAliaccount(openId);
                        }
                        //String username="ai"+randomUtils.createData(10);
                        String username=randomUtils.createData(10);
                        users.setUsername(username);
                        users.setImAccount(username);
                        users.setNickname(username);
                        users.setUserphoto(userPhoto);
                        users.setImPassword("123456789");
                        this.register(username,users.getImPassword());
                        users.setStatus(0);
                        users.setIsCertification(0);
                        users.setRole(0);
                        usersService.insert(users);
                    }
                    String token="";
                    SessionUser sessionUser=null;
                    if(redisUtil.get(RedisConstants.USER_TOKEN+users.getId())!=null){
                        sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + users.getId());
                        token=sessionUser.getToken();
                        if (request.getParameter("deviceId") != null) {
                            sessionUser.setDeviceId(request.getParameter("deviceId"));
                        }
                        sessionUser.setUsers(users);
                        redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                    }else {
                        token = UUID.randomUUID().toString() + SessionUser.FLAG + users.getId();
                        sessionUser = new SessionUser();
                        sessionUser.setToken(token);
                        sessionUser.setUsers(users);
                        if (request.getParameter("deviceId") != null) {
                            sessionUser.setDeviceId(request.getParameter("deviceId"));
                        }
                        redisUtil.set(RedisConstants.USER_TOKEN + sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                    }
                    Map<String,Object> map=new HashMap<>();
                    map.put("token",sessionUser.getToken());
                    map.put("role",sessionUser.getUsers().getRole());
                    map.put("imcount",sessionUser.getUsers().getImAccount());
                    map.put("impassword",sessionUser.getUsers().getImPassword());
//                    userLoginLogService.insertLog(users.getId(),IpUtils.getRealIp(request),request.getParameter("deviceId")!=null?request.getParameter("deviceId"):"");
                    return DataRes.success(map);
                } else {
                    return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_EXPIRED);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
    public void register(String username,String password){
        Map<String,Object> map=new HashMap<>();
        String token= ImTokenUtils.getToken(redisUtil);
        IMUtils.registerUser(username,password,token);
    }

    @RequestMapping("app/register")
    public DataRes register(HttpServletRequest request,String telephone,String code,String openId,String spreadCode){
        if(telephone!=null &&!telephone.equals("")){
            if(redisUtil.get(telephone)!=null) {
                String vcode = (String) redisUtil.get(request.getParameter("telephone"));
                if (vcode.equals(code)) {
                    Users users = new Users();
                    users.setTelephone(telephone);
                    users.setWxaccount(openId);
                    String username=randomUtils.createData(10);
                    users.setUsername(username);
                    users.setImAccount(username);
                    users.setNickname(username);
                    users.setUserphoto(userPhoto);
                    users.setStatus(0);
                    users.setIsCertification(0);
                    users.setRole(0);

                    if(!ObjectUtils.isEmpty(spreadCode)) {
                        Users search = new Users();
                        search.setSpreadCode(spreadCode);
                        List<Users> parents= usersService.selectByCondition(search);
                        if(parents.isEmpty()){
                            return DataRes.error(ResponseCode.SPREAD_CODE_ERROR);
                        }
                        users.setParentId(parents.get(0).getId());
                    }
                    usersService.insert(users);
                    String token="";
                    SessionUser sessionUser=null;
                    if(redisUtil.get(RedisConstants.USER_TOKEN+users.getId())!=null){
                        sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + users.getId());
                        token=sessionUser.getToken();
                        if (request.getParameter("deviceId") != null) {
                            sessionUser.setDeviceId(request.getParameter("deviceId"));
                        }
                        sessionUser.setUsers(users);
                        redisUtil.set(RedisConstants.USER_TOKEN+sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                    }else {
                        token = UUID.randomUUID().toString() + SessionUser.FLAG + users.getId();
                        sessionUser = new SessionUser();
                        sessionUser.setToken(token);
                        sessionUser.setUsers(users);
                        if (request.getParameter("deviceId") != null) {
                            sessionUser.setDeviceId(request.getParameter("deviceId"));
                        }
                        redisUtil.set(RedisConstants.USER_TOKEN + sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                    }
                    Map<String,Object> map=new HashMap<>();
                    map.put("token",sessionUser.getToken());
                    map.put("role",sessionUser.getUsers().getRole());
                    return DataRes.success(map);
                } else {
                    return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.PHONE_VERIFICATION_CODE_EXPIRED);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
