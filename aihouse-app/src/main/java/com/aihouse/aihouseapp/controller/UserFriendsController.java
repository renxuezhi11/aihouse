package com.aihouse.aihouseapp.controller;


import IM.IMUtils;
import com.aihouse.aihouseapp.utils.ImTokenUtils;
import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.HanyuPinyinHelper;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserFocus;
import com.aihouse.aihousedao.bean.UserFriends;
import com.aihouse.aihousedao.bean.UserScreen;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.CommunityService;
import com.aihouse.aihouseservice.users.UserFocusService;
import com.aihouse.aihouseservice.users.UserFriendsService;
import com.aihouse.aihouseservice.users.UserScreenService;
import com.aihouse.aihouseservice.users.UsersService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController

public class UserFriendsController {

    @Resource
    private UserFriendsService userFriendsService;

    @Resource
    private UsersService usersService;

    @Resource
    private CommunityService communityService;

    @Resource
    private UserFocusService userFocusService;

    @Resource
    private UserScreenService userScreenService;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 获取用户的好友列表，accountFrom不为空则是添加完好友的刷新
     * @param accountFrom
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserFriends",method = RequestMethod.POST)
    public DataRes getUserFriends(String accountFrom, HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
        if(accountFrom!=null&&!accountFrom.equals("")){

            Users users=new Users();
            users.setImAccount(accountFrom);
            List<Users> list=usersService.selectAll(users);
            if(list!=null&&list.size()>0){
                users=list.get(0);
                UserFriends userFriends=new UserFriends();
                userFriends.setAccountTo(sessionUser.getUsers().getImAccount());
                userFriends.setAccountFrom(accountFrom);
                userFriends.setLabel(HanyuPinyinHelper.toHanyuPinyin(users.getNickname()));
                userFriends.setRemark(users.getNickname());
                if(users.getNickname()!=null&&!users.equals("")){
                    userFriends.setLetter(HanyuPinyinHelper.getFirstLetter(users.getNickname()));
                }else{
                    userFriends.setLetter("a");
                }
                userFriendsService.insert(userFriends);
            }

        }
        List<Map<String,Object>> letter=userFriendsService.getUserFriendsLetter(sessionUser.getUsers().getImAccount());
        if(letter!=null&&letter.size()>0){
            for(Map map:letter){
                List<Map<String,Object>> data=userFriendsService.getUserFriendsList(sessionUser.getUsers().getImAccount(),map.get("letter").toString());
                map.put("data",data);
            }
        }
        return DataRes.success(letter);
    }


    /**
     * 修改好友备注
     * @param accountFrom
     * @param remark
     * @param request
     * @return
     */
    @RequestMapping(value = "app/modifyFriendsRemark",method = RequestMethod.POST)
    public DataRes modifyFriendsRemark(String accountFrom,String remark,HttpServletRequest request){
        if(accountFrom!=null&&remark!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
            UserFriends userFriends=new UserFriends();
            userFriends.setAccountFrom(accountFrom);
            userFriends.setAccountTo(sessionUser.getUsers().getImAccount());
            List<UserFriends> list=userFriendsService.selectAll(userFriends);
            if(list!=null&&list.size()>0){
                userFriends=list.get(0);
                userFriends.setRemark(remark);
                userFriends.setLabel(HanyuPinyinHelper.toHanyuPinyin(remark));
                userFriends.setLetter(HanyuPinyinHelper.getFirstLetter(remark));
                userFriendsService.update(userFriends);
                List<Map<String,Object>> letter=userFriendsService.getUserFriendsLetter(sessionUser.getUsers().getImAccount());
                if(letter!=null&&letter.size()>0){
                    for(Map map:letter){
                        List<Map<String,Object>> data=userFriendsService.getUserFriendsList(sessionUser.getUsers().getImAccount(),map.get("letter").toString());
                        map.put("data",data);
                    }
                }
                return DataRes.success(letter);
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 删除用户好友
     * @param accountFrom
     * @param request
     * @return
     */
    @RequestMapping(value = "app/deleteUserFriends",method = RequestMethod.POST)
    public DataRes deleteUserFriends(String accountFrom,HttpServletRequest request){
        if(accountFrom!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId);
//            System.out.println(ImTokenUtils.getToken(redisUtil));
//            String result=IMUtils.deleteUserFriends(accountFrom,sessionUser.getUsers().getImAccount(), ImTokenUtils.getToken(redisUtil));
//            Map resil=JSON.parseObject(result,HashMap.class);
//            if(result!=null) {
                UserFriends userFriends = new UserFriends();
                userFriends.setAccountFrom(accountFrom);
                userFriends.setAccountTo(sessionUser.getUsers().getImAccount());
                List<UserFriends> list = userFriendsService.selectAll(userFriends);
                if (list != null && list.size() == 1) {
                    userFriendsService.deleteByPrimaryKey(list.get(0));
                }
//            }
            List<Map<String,Object>> letter=userFriendsService.getUserFriendsLetter(sessionUser.getUsers().getImAccount());
            if(letter!=null&&letter.size()>0){
                for(Map map:letter){
                    List<Map<String,Object>> data=userFriendsService.getUserFriendsList(sessionUser.getUsers().getImAccount(),map.get("letter").toString());
                    map.put("data",data);
                }
            }
            return DataRes.success(letter);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 新朋友搜索，手机号或者爱号
     * @param keyword
     * @param request
     * @return
     */
    @RequestMapping(value = "app/searchFriends",method = RequestMethod.POST)
    public DataRes searchFriends(String keyword,HttpServletRequest request){
        if(keyword!=null){
            List<Map<String,Object>> map=userFriendsService.searchFriends(keyword);
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取用户信息
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getOtherUserInfo",method = RequestMethod.POST)
    public DataRes getUserInfo(Integer userId,HttpServletRequest request){
        Users users=new Users();
        Map<String,Object> map=new HashMap<>();
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
            map.put("imaccount",users.getImAccount());
            map.put("asklist", communityService.selectUserList(users.getId()));
            String userId1=request.getHeader("token").split(SessionUser.FLAG)[1];
            SessionUser sessionUser=(SessionUser) redisUtil.get(RedisConstants.USER_TOKEN+userId1);
            UserFriends userFriends=new UserFriends();
            userFriends.setAccountFrom(users.getImAccount());
            userFriends.setAccountTo(sessionUser.getUsers().getImAccount());
            List<UserFriends> list=userFriendsService.selectAll(userFriends);
            if(list!=null&&list.size()>0){
                map.put("isFriends",true);
                map.put("remark",list.get(0).getRemark());
            }else{
                map.put("isFriends",false);
            }
            UserFocus userFocus=new UserFocus();
            userFocus.setUserId(sessionUser.getUsers().getId());
            userFocus.setFocusUserId(users.getId());
            List<UserFocus> list1=userFocusService.selectAll(userFocus);
            if(list1!=null&&list1.size()>0){
                map.put("isFocus",true);
            }else{
                map.put("isFocus",false);
            }
            UserScreen userScreen=new UserScreen();
            userScreen.setUserId(sessionUser.getUsers().getId());
            userScreen.setScreenUserId(userId);
            List<UserScreen> list3=userScreenService.selectAll(userScreen);
            if(list3!=null&&list3.size()>0){
                map.put("isScreen",true);//已屏蔽
            }else{
                map.put("isScreen",false);//未屏蔽
            }
            map.put("focusCnt",userFocusService.selectCount(users.getId()));
            return DataRes.success(map);
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 会话列表信息
     * @param member
     * @param memberLs
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserInfoByImAccount",method = RequestMethod.POST)
    public DataRes getUserInfoByImAccount(String[] member,String[] memberLs,HttpServletRequest request){
        List<Map<String,Object>> listR=new ArrayList<>();
        if(member!=null &&member.length>0){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            List<Map<String,Object>> list=userFriendsService.getUserFriendsInfo(member,Integer.parseInt(userId));
            listR.addAll(list);
        }
        if(memberLs!=null&&memberLs.length>0){
            List<Map<String,Object>> list1=userFriendsService.getUserFriendsLsInfo(memberLs);
            listR.addAll(list1);
        }
        return DataRes.success(listR);
    }
}
