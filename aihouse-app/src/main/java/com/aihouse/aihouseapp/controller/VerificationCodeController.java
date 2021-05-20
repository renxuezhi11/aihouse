package com.aihouse.aihouseapp.controller;


import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.users.UsersService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin
public class VerificationCodeController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UsersService usersService;

    /**
     * 请求发送验证码
     * @param response
     * @param request
     * @param telephone
     * @return
     */
    @RequestMapping("app/sendCode")
    public DataRes sendCode(HttpServletResponse response, HttpServletRequest request, String telephone){
        if(telephone!=null &&!telephone.equals("")){
            Users users=new Users();
            users.setTelephone(request.getParameter("telephone"));
            List<Users> list=usersService.selectAll(users);
            if(list.size()==0){
                //用户不存在
                return DataRes.error(ResponseCode.NO_REGISTER_ERROR);
            }
            if(redisUtil.get(telephone+"-sms")==null) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("phoneName", telephone);
                hashMap.put("SignName", SmsConstant.SIGN_NAME);
                hashMap.put("TemplateCode", SmsConstant.LOGIN_CONFIRM);
                String verifyCode = String
                        .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
                hashMap.put("TemplateParam", SmsConstant.CODE + "\"" + verifyCode + "\"}");
                try {
                    SendSmsResponse res = SmsDemo.sendSms(hashMap);
                    if (res.getCode() != null && res.getCode().equals("OK")) {
                        redisUtil.set(telephone, verifyCode, 300);
                        redisUtil.set(telephone + "-sms", verifyCode, 60);
                        return DataRes.success("success");
                    } else {
                        return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                    }
                } catch (ClientException e) {
                    e.printStackTrace();
                    return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.SMS_SEND_REPEAT);
            }
        }else{
            return DataRes.error(ResponseCode.PHONE_ERROR);
        }
    }

    /**
     * 请求发送注册验证码
     * @param telephone
     * @return
     */
    @RequestMapping("app/sendRegisterCode")
    public DataRes sendRegisterCode(String telephone){
        if(telephone!=null &&!telephone.equals("")){
            if(redisUtil.get(telephone+"-sms")==null) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("phoneName", telephone);
                hashMap.put("SignName", SmsConstant.SIGN_NAME);
                hashMap.put("TemplateCode", SmsConstant.USER_REGISTER);
                String verifyCode = String
                        .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
                hashMap.put("TemplateParam", SmsConstant.CODE + "\"" + verifyCode + "\"}");
                try {
                    SendSmsResponse res = SmsDemo.sendSms(hashMap);
                    if (res.getCode() != null && res.getCode().equals("OK")) {
                        redisUtil.set(telephone, verifyCode, 300);
                        redisUtil.set(telephone + "-sms", verifyCode, 60);
                        return DataRes.success("success");
                    } else {
                        return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                    }
                } catch (ClientException e) {
                    e.printStackTrace();
                    return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.SMS_SEND_REPEAT);
            }
        }else{
            return DataRes.error(ResponseCode.PHONE_ERROR);
        }
    }

    /**
     * 请求发送验证码--换绑手机号
     * @param response
     * @param request
     * @param telephone
     * @return
     */
    @RequestMapping("app/sendChangePhoneCode")
    public DataRes sendChangePhoneCode(HttpServletResponse response, HttpServletRequest request, String telephone){
        if(telephone!=null &&!telephone.equals("")){
            if(redisUtil.get(telephone+"-psms")==null) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("phoneName", telephone);
                hashMap.put("SignName", SmsConstant.SIGN_NAME);
                hashMap.put("TemplateCode", SmsConstant.BIND_PHONE);
                String verifyCode = String
                        .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
                hashMap.put("TemplateParam", SmsConstant.CODE + "\"" + verifyCode + "\"}");
                try {
                    SendSmsResponse res = SmsDemo.sendSms(hashMap);
                    if (res.getCode() != null && res.getCode().equals("OK")) {
                        redisUtil.set(telephone, verifyCode, 300);
                        redisUtil.set(telephone + "-psms", verifyCode, 60);
                        return DataRes.success("success");
                    } else {
                        return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                    }
                } catch (ClientException e) {
                    e.printStackTrace();
                    return DataRes.error(ResponseCode.SMS_SEND_ERROR);
                }
            }else{
                return DataRes.error(ResponseCode.SMS_SEND_REPEAT);
            }
        }else{
            return DataRes.error(ResponseCode.PHONE_ERROR);
        }
    }

}
