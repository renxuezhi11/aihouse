package com.aihouse.aihousesys.controller;

import com.aihouse.aihousedao.bean.SysAuth;
import com.aihouse.aihousedao.bean.SysLoginLog;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihouseservice.SysAuthService;
import com.aihouse.aihouseservice.SysLoginLogService;
import com.aihouse.aihousesys.utils.AuthTreeUtils;
import com.aihouse.aihousesys.utils.RandomCodeUtil;
import com.aihouse.aihousesys.utils.RequestUtils;
import com.aihouse.aihousesys.utils.SessionConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Resource
    private SysAuthService sysAuthService;

    @Resource
    private SysLoginLogService sysLoginLogService;

    @RequestMapping("/")
    public String index(){
        return "redirect:/tologin";
    }
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }
    /**
     * 获取图形验证码
     * @return
     */
    @RequestMapping("/login/imageCode")
    public void gotoIndex(HttpSession session, HttpServletResponse response){
        String rand = RandomCodeUtil.getVerifyCode(4);// 生成随机四位验证码
        session.setAttribute(SessionConstant.imageLgoinCode, rand);
        // 绘制验证码图片
        RandomCodeUtil.createValidateCode(response, rand);
    }

    @RequestMapping("/login/loginout")
    public String logout(HttpSession session){
        session.removeAttribute(SessionConstant.auths);
        session.removeAttribute(SessionConstant.sysUserSession);
        return "login";
    }

    @RequestMapping("/login")
    public Object login(HttpServletRequest request, Map<String,Object> map){
        System.out.println("login");
        String msg="";
        if(!RandomCodeUtil.isValidImageVerifyCode(request,"verifyCode")){
            msg="验证码错误";
        }else {
            // 登录失败从request中获取shiro处理的异常信息。
            // shiroLoginFailure:就是shiro异常类的全类名.
//        String exception=(String) request.getAttribute("shiroLoginFailure");
//        System.out.println("exception="+exception);

//        if(exception!=null){
//            if (UnknownAccountException.class.getName().equals(exception)){
//                System.out.println("UnknownAccountException -- > 账号不存在：");
//                msg = "UnknownAccountException -- > 账号不存在：";
//            }else if(IncorrectCredentialsException.class.getName().equals(exception)){
//                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
//                msg = "IncorrectCredentialsException -- > 密码不正确：";
//            }else if(IncorrectCaptchaException.class.getName().equals(exception)){
//                System.out.println("IncorrectCaptchaException -- > 验证码错误");
//                msg = "kaptchaValidateFailed -- > 验证码错误";
//            }else{
//                msg = "else >> "+exception;
//                System.out.println("else -- >" + exception);
//            }
//        }
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(request.getParameter("loginName"), request.getParameter("password"));
            Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(usernamePasswordToken);   //完成登录
                SysUser user=(SysUser) subject.getPrincipal();
                List<SysAuth> recurve;
                List<SysAuth> sysAuths;
                HttpSession session = request.getSession();
                //sysAuths = sysAuthService.queryByUser(user.getId());

                SysAuth sysAuth=new SysAuth();
                sysAuth.setOrderByString(" order by sort asc");
                //sysAuths = sysAuthService.selectAll(sysAuth);查询全部
                sysAuths=sysAuthService.queryByUser(user.getId());//查询用户权限
                //遍历, 已url为key存放用户权限到session中
                Map<String,SysAuth> userAuth=new HashMap<>();
                Map<Integer,SysAuth> authAllById=new HashMap<>();
                sysAuths.forEach(t-> {userAuth.put(t.getHref(),t);
                    authAllById.put(t.getId(),t);});
                session.setAttribute(SessionConstant.userAuth,userAuth);
                session.setAttribute(SessionConstant.authAllById,authAllById);
                recurve = AuthTreeUtils.recurve(sysAuths);
                session.setAttribute(SessionConstant.auths,recurve);
                session.setAttribute(SessionConstant.sysUserSession,user);
                SysLoginLog sysLogin=new SysLoginLog();
                sysLogin.setBrowser(RequestUtils.getOsAndBrowserInfo(request));
                sysLogin.setIp(RequestUtils.getIp(request));
                sysLogin.setSysUserId(user.getId());
                sysLoginLogService.insert(sysLogin);
                msg = "success";
                return "redirect:/index";
            } catch (UnknownAccountException e) {
                msg = "用户名不存在";
            } catch (IncorrectCredentialsException e) {
                msg = "密码错误";
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return  "login";
    }
}
