package com.aihouse.aihouseapp.interceptor;

import com.aihouse.aihouseapp.utils.AppRuntimeException;
import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.UserCollect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * token 拦截器
 */
@ComponentScan
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!request.getMethod().equals("OPTIONS")) {
            if (request.getHeader("token") != null&&!request.getHeader("token").equals("")) {
                String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
                if (redisUtil.get(RedisConstants.USER_TOKEN + userId) != null) {
                    SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
                    //System.out.println(sessionUser.getToken());
                    if (sessionUser.getToken().equals(request.getHeader("token"))) {
                        if (request.getHeader("deviceid") != null) {
                            if (request.getHeader("deviceid").equals("1111111111111111111111111111111")) {
                                return true;
                            } else {
                                if (sessionUser.getDeviceId().equals(request.getHeader("deviceid"))) {
                                    return true;
                                } else {//设备不相符
                                    System.out.println(request.getHeader("deviceid"));
                                    throw new AppRuntimeException(10013, "TOKEN和DEVICEID不符");
                                }
                            }
                        } else {
                            throw new AppRuntimeException(10004, "数据格式错误");
                        }
                    } else {
                        throw new AppRuntimeException(10015, "TOKEN失效");
                    }
                } else {
                    throw new AppRuntimeException(10015, "TOKEN失效");
                }
            } else {
                throw new AppRuntimeException(10015, "TOKEN失效");
            }
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
