package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserFocus;
import com.aihouse.aihousedao.bean.UserScreen;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserScreenService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户拉黑controller
 */
@CrossOrigin
@RestController
public class UserScreenController {

    @Resource
    private UserScreenService userScreenService;

    @Resource
    private UsersService usersService;

    /**
     *拉黑用户
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/screenUser",method = RequestMethod.POST)
    public DataRes screenUser(Integer userId, HttpServletRequest request){
        if(userId!=null){
            //检查用户是否存在
            Users users=new Users();
            users.setId(userId);
            users=usersService.selectByPrimaryKey(users);
            if(users!=null){
                UserScreen userScreen=new UserScreen();
                userScreen.setScreenUserId(userId);
                String id=request.getHeader("token").split(SessionUser.FLAG)[1];
                userScreen.setUserId(Integer.parseInt(id));
                //检查是否拉黑
                List<UserScreen> list=userScreenService.selectByCondition(userScreen);
                if(list!=null&&list.size()>0){
                    return DataRes.error(ResponseCode.USER_SCREEN_REPART);
                }else{
                    userScreenService.insert(userScreen);
                    return DataRes.success("屏蔽成功");
                }
            }else{
                return DataRes.error(ResponseCode.USER_CREEN_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 获取用户的拉黑列表  分页
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/getUserScreenList",method = RequestMethod.POST)
    public DataRes getUserScreenList(UserScreen userScreen,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        userScreen.setUserId(Integer.parseInt(userId));
        userScreen.setOrderByString(" order by createtime desc");
        userScreen=userScreenService.selectAllByPaging(userScreen);
        return DataRes.success(userScreen);
    }

    /**
     * 取消拉黑
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/cancleUserScreen",method = RequestMethod.POST)
    public DataRes cancleUserScreen(Integer[] ids,HttpServletRequest request){
        if(ids!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            for(Integer id:ids){
                UserScreen userScreen=new UserScreen();
                userScreen.setId(id);
                userScreen=userScreenService.selectByPrimaryKey(userScreen);
                if(userScreen!=null&&userScreen.getUserId()==Integer.parseInt(userId)){
                    userScreenService.deleteByPrimaryKey(userScreen);
                }
            }
            return DataRes.success("取消成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


    /**
     * 取消拉黑通过用户id
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/cancleUserScreenByUserId",method = RequestMethod.POST)
    public DataRes cancleUserScreenByUserId(Integer userId,HttpServletRequest request){
        if(userId!=null){
            UserScreen userScreen=new UserScreen();
            userScreen.setScreenUserId(userId);
            String id=request.getHeader("token").split(SessionUser.FLAG)[1];
            userScreen.setUserId(Integer.parseInt(id));
            //检查是否拉黑
            List<UserScreen> list=userScreenService.selectByCondition(userScreen);
            if(list!=null&&list.size()>0){
                userScreenService.deleteByPrimaryKey(list.get(0));
            }
            return DataRes.success("取消成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
