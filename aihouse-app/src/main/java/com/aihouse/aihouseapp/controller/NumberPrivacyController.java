package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UsersService;
import numberProtection.NumberPrivacy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * 隐私保护controller
 */
@CrossOrigin
@RestController
public class NumberPrivacyController {

    @Resource
    private UsersService usersService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 绑定关系，获取隐私号码
     * @param request
     * @param brokerId
     * @return
     */
    @RequestMapping(value = "app/numberprivacy/getNumber",method = RequestMethod.POST)
    public DataRes getNumber(Integer brokerId, HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Users users=new Users();
        users.setId(Integer.parseInt(userId));
        users=usersService.selectByPrimaryKey(users);
        if(users.getTelephone()!=null) {
            String phoneNoA = users.getTelephone();
            users = new Users();
            users.setId(brokerId);
            users = usersService.selectByPrimaryKey(users);
            String phoneNoB = users.getTelephone();
            if(redisUtil.get(phoneNoA+"to"+phoneNoB)!=null){
                Map map=(Map)redisUtil.get(phoneNoA+"to"+phoneNoB);
                return DataRes.success(map);
            }else {
                Map map = NumberPrivacy.BindAxb(phoneNoA, phoneNoB);
                if(map.get("Code").toString().equals("OK")){
                    redisUtil.set(phoneNoA+"to"+phoneNoB,map,120);
                }
                return DataRes.success(map);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
