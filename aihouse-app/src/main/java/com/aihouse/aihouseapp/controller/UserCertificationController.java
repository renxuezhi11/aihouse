package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserCertification;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UserCertificationService;
import com.aihouse.aihouseservice.users.UsersService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 用户实名认证
 */
@CrossOrigin
@RestController
public class UserCertificationController {

    @Resource
    private UserCertificationService userCertificationService;

    @Resource
    private UsersService usersService;


    /**
     * 是否实名认证
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/isCertification",method = RequestMethod.POST)
    public DataRes isCertification(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Users users=new Users();
        users.setId(Integer.parseInt(userId));
        users=usersService.selectByPrimaryKey(users);
        return DataRes.success(users.getIsCertification());
    }

    /**
     * 做实名认证
     * @param userCertification
     * @param request
     * @return
     */
    @RequestMapping(value = "app/user/doCertification",method = RequestMethod.POST)
    public DataRes doCertification(UserCertification userCertification,HttpServletRequest request){
        if(userCertification!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            userCertification.setUserId(Integer.parseInt(userId));
            userCertificationService.insert(userCertification);
            Users users=new Users();
            users.setId(Integer.parseInt(userId));
            users.setIsCertification(1);
            usersService.update(users);
            return DataRes.success("提交认证成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
