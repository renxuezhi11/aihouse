package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousecore.utils.keyword.BadWord;
import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.CommunityService;
import com.aihouse.aihouseservice.users.UserSpreadLogService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * 用户controller
 */
@CrossOrigin
@RestController
public class UserController {
    @Resource
    private UsersService usersService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    CommunityService communityService;

    @Resource
    private UserSpreadLogService userSpreadLogService;

    @Value("${auto.code.filePath}")
    private String filePath;
    /**
     * 修改用户的昵称
     * @param nickname
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/modifyUserNickName",method = RequestMethod.POST)
    public DataRes modifyUserNickName(String nickname, HttpServletRequest request){
        if(nickname!=null &&!nickname.equals("")){
            if(BadWord.isContain(nickname)){
                //过滤敏感词
                nickname=BadWord.replace(nickname,"*");
            }
            Users users=new Users();
            users.setNickname(nickname);
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            users.setId(Integer.parseInt(userId));
            usersService.update(users);
            return DataRes.success("修改昵称成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 验证手机号是否存在
     * @param userPhone
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/veifyPhone",method = RequestMethod.POST)
    public DataRes veifyPhone(String userPhone,HttpServletRequest request){
        if(userPhone!=null&&!userPhone.equals("")) {
            //验证手机号是否绑定过
            Users users = new Users();
            users.setTelephone(userPhone);
            List<Users> list = usersService.selectAll(users);
            if (list.size() > 0) {
                return DataRes.error(ResponseCode.USER_PHONE_REPEAT);
            }else{
                return DataRes.success("success");
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 换绑手机号
     * @param userPhone
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/modifyUserPhone",method = RequestMethod.POST)
    public DataRes modifyUserPhone(String userPhone,String code,HttpServletRequest request){
        if(userPhone!=null&&code!=null&&!userPhone.equals("")&&!code.equals("")){
            //验证手机号是否绑定过
            Users users=new Users();
            users.setTelephone(userPhone);
            List<Users> list=usersService.selectAll(users);
            if(list.size()>0){
                return DataRes.error(ResponseCode.USER_PHONE_REPEAT);
            }
            //验证码是否正确

            String codea=(String)redisUtil.get(request.getParameter("telephone"));
            if(codea!=null) {
                if (codea.equals(code)) {
                    String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                    users.setId(Integer.parseInt(userId));
                    usersService.update(users);
                    SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
                    sessionUser.getUsers().setTelephone(users.getTelephone());
                    redisUtil.set(RedisConstants.USER_TOKEN + sessionUser.getUsers().getId(), sessionUser, RedisConstants.EXPIRE_30_DAY);
                    return DataRes.success("绑定成功");
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

    /**
     * 绑定第三方账号
     * @param openId
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/bangThirdParty",method = RequestMethod.POST)
    public DataRes bangThirdParty(String openId,Integer type,HttpServletRequest request){
        if(openId!=null){
            Users users=new Users();
            if(type==1){
                users.setWxaccount(openId);
            }else {
                users.setAliaccount(openId);
            }
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            users.setId(Integer.parseInt(userId));
            usersService.update(users);
            return DataRes.success("绑定成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 第三方登录账号解绑
     * @param type
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/noBangThirdParty",method = RequestMethod.POST)
    public DataRes noBangThirdParty(Integer type,HttpServletRequest request){
        if(type!=null){
            Users users=new Users();
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            users.setId(Integer.parseInt(userId));
            if(type==1){
                users.setWxaccount("0");
            }else{
                users.setAliaccount("0");
            }
            usersService.update(users);
            return DataRes.success("解绑成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 设置，修改密码
     * @param newPassword
     * @param oldPassword
     * @param request
     * @param type
     * @return
     */
    @RequestMapping(value = "app/user/modifyPassword",method = RequestMethod.POST)
    public DataRes modifyPassword(String newPassword,String oldPassword,Integer type,HttpServletRequest request){
        if(type!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            if(type==1){//设置密码
                if(newPassword!=null){
                    Users users=new Users();
                    users.setPassword(newPassword);
                    users.setId(Integer.parseInt(userId));
                    usersService.update(users);
                    return DataRes.success("设置密码成功");
                }else{
                    return DataRes.error(ResponseCode.DATA_ERROR);
                }
            }else{//修改密码
                if(newPassword!=null&&oldPassword!=null){
                    Users users1=new Users();
                    users1.setId(Integer.parseInt(userId));
                    users1=usersService.selectByPrimaryKey(users1);
                    if(users1.getPassword().equals(oldPassword)){
                        Users users=new Users();
                        users.setId(Integer.parseInt(userId));
                        users.setPassword(newPassword);
                        usersService.update(users);
                        return DataRes.success("修改密码成功");
                    }else{
                        return DataRes.error(ResponseCode.USER_PASSWORD_ERROR);
                    }
                }else{
                    return DataRes.error(ResponseCode.DATA_ERROR);
                }
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 个人中心
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/getPersonal",method = RequestMethod.POST)
    public DataRes getPersonal(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Users users=new Users();
        users.setId(Integer.parseInt(userId));
        users=usersService.selectByPrimaryKey(users);
        Map<String,Object> map=new HashMap<>();
        map.put("id",users.getId());
        map.put("nickname", users.getNickname());
        map.put("userphoto",users.getUserphoto());
        map.put("telephone",users.getTelephone());
        map.put("iscertification",users.getIsCertification());
        if(users.getAliaccount()!=null&&!users.getAliaccount().equals("0")){
            map.put("isWbBang",true);
        }else{
            map.put("isWbBang",false);
        }
        if(users.getWxaccount()!=null&&!users.getWxaccount().equals("0")){
            map.put("isWxBang",true);
        }else{
            map.put("isWxBang",false);
        }
        if(users.getPassword()!=null){
            map.put("isPassword",true);
        }else{
            map.put("isPassword",false);
        }
        if(users.getParentId()!=null){
            map.put("isSetSpread",true);
            Users users1=new Users();
            users1.setId(users.getParentId());
            users1=usersService.selectByPrimaryKey(users1);
            if(!ObjectUtils.isEmpty(users1)) {
                map.put("parentSpreadCode", users1.getSpreadCode());
            }
        }else{
            map.put("isSetSpread",false);
        }
        map.put("username",users.getUsername());
        return DataRes.success(map);
    }

    /**
     * 获取用户信息
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/getUserInfo",method = RequestMethod.POST)
    public DataRes getUserInfo(Integer userId,HttpServletRequest request){
        Users users=new Users();
        Map<String,Object> map=new HashMap<>();
        if(userId==null){
            userId=Integer.parseInt(request.getHeader("token").split(SessionUser.FLAG)[1]);
            map.put("type",1);
        }else{
            map.put("type",0);
        }
        users.setId(userId);
        users=usersService.selectByPrimaryKey(users);
        if(users!=null) {
            map.put("id", users.getId());
            map.put("nickname", users.getNickname());
            map.put("signname", users.getSignname());
            map.put("cityname", users.getCityname());
            map.put("areaname", users.getAreaname());
            map.put("sex", users.getsex_());
            map.put("username", users.getUsername());
            map.put("userphoto", users.getUserphoto());
            map.put("asklist", communityService.selectUserList(users.getId()));
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 更改用户头像
     * @param uploadfile
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/modifyUserPhoto")
    public DataRes modifyUserPhoto(@RequestParam("file")MultipartFile uploadfile,HttpServletRequest request){
        String filename="";
        if(uploadfile.getOriginalFilename().contains("\\.")){
             filename = UUID.randomUUID().toString().replaceAll("-", "")+"."+uploadfile.getOriginalFilename().split("\\.")[1];
        }else{
            filename = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
        }
        // 获得输入流：
        String fileDir= DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/");
        String newFileName=DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename;
        try{
            InputStream input = uploadfile.getInputStream();
            File p = new File(filePath + fileDir);
            if (!p.exists()) {
                p.mkdirs();
            }
            File file = new File(p, newFileName);
            uploadfile.transferTo(file);
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            Users users=new Users();
            users.setId(Integer.parseInt(userId));
            users.setUserphoto(fileDir+newFileName);
            usersService.update(users);
            return DataRes.success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return DataRes.error(ResponseCode.USER_EDIT_PHOTO_ERROR);
        }
    }

    /**
     * 修改用户信息
     * @param cityname
     * @param areaname
     * @param sex
     * @param request
     * @param signname
     * @return
     */
    @RequestMapping(value = "app/user/modifyUserInfo",method = RequestMethod.POST)
    public DataRes modifyUserInfo(String cityname,String areaname,Integer sex,HttpServletRequest request,String signname){
        try {
            Users users = new Users();
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            users.setId(Integer.parseInt(userId));
            if (cityname != null) {
                users.setCityname(cityname);
            }
            if (areaname != null) {
                users.setAreaname(areaname);
            }
            if (sex != null) {
                users.setSex(sex);
            }
            if (signname != null) {
                if(BadWord.isContain(signname)){
                    //过滤敏感词
                    signname=BadWord.replace(signname,"*");
                }
                users.setSignname(signname);
            }
            usersService.update(users);
            return DataRes.success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return DataRes.error(ResponseCode.USER_EDIT_INFO_ERROR);
    }


    /**
     * 获取所有的经纪人列表
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getAllBroker",method = RequestMethod.POST)
    public DataRes getAllBroker(Integer page,Integer pageSize,HttpServletRequest request){
        if(page!=null) {
            if(pageSize==null){
                pageSize=10;
            }
            return DataRes.success(usersService.selectAllBroker(page,pageSize));
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 设置推广码
     * @param spreadCode
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/setSpreadCode",method = RequestMethod.POST)
    public DataRes setSpreadCode(String spreadCode,HttpServletRequest request){
        if(spreadCode!=null){
            Users users=new Users();
            users.setSpreadCode(spreadCode);
            List<Users> list=usersService.selectAll(users);
            if(list.size()>0){
                String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
                users=new Users();
                users.setId(Integer.parseInt(userId));
                users.setParentId(list.get(0).getId());
                usersService.update(users);
                //添加用户的推广记录
                UserSpreadLog userSpreadLog=new UserSpreadLog();
                userSpreadLog.setUserId(users.getId());
                userSpreadLog.setParentId(users.getParentId());
                userSpreadLogService.insert(userSpreadLog);
                return DataRes.success("设置成功");
            }else{
                return DataRes.error(ResponseCode.SPREAD_CODE_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
