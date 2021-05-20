package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserFocus;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserFocusService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户关注
 */
@CrossOrigin
@RestController
public class UserFocusController {
    @Resource
    private UserFocusService userFocusService;

    @Resource
    private UsersService usersService;
    /**
     *关注用户
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/focusUser",method = RequestMethod.POST)
    public DataRes focusUser(Integer userId, HttpServletRequest request){
        if(userId!=null){
            //检查用户是否存在
            Users users=new Users();
            users.setId(userId);
            users=usersService.selectByPrimaryKey(users);
            if(users!=null){
                UserFocus userFocus=new UserFocus();
                userFocus.setFocusUserId(userId);
                String id=request.getHeader("token").split(SessionUser.FLAG)[1];
                userFocus.setUserId(Integer.parseInt(id));
                userFocus=userFocusService.selectUsersAndUserFocus(userFocus);
                if(userFocus.getRows().size()>0){
                   return DataRes.error(ResponseCode.USER_FOCUS_REPEAT);
                }else{
                    userFocus.setFocusUserName(users.getNickname());
                    userFocusService.insert(userFocus);
                    return DataRes.success(userFocusService.selectCount(users.getId()));
                }
            }else{
                return DataRes.error(ResponseCode.USER_FOCUS_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取用户的关注列表  分页
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/getUserFocusList",method = RequestMethod.POST)
    public DataRes getUserFocusList(UserFocus userFocus,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        userFocus.setUserId(Integer.parseInt(userId));
        userFocus.setOrderByString(" order by createtime desc");
        userFocusService.selectAllByPaging(userFocus);
        return DataRes.success(userFocus);
    }

    /**
     * 取消关注成功
     * @param userFocus
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/cancleUserFocus",method = RequestMethod.POST)
    public DataRes cancleUserFocus(UserFocus userFocus,HttpServletRequest request){
        if(userFocus.getId()!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            userFocus.setUserId(Integer.parseInt(userId));
            List<UserFocus> list=userFocusService.selectAll(userFocus);
            if(list.size()>0){
                userFocusService.deleteByPrimaryKey(userFocus);
                return DataRes.success("取消关注成功");
            }else{
                return DataRes.error(ResponseCode.USER_FOCUS_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
