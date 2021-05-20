package com.aihouse.aihousesys.interceptor;

import com.aihouse.aihousedao.bean.SysAuth;
import com.aihouse.aihousedao.bean.SysOperationLog;
import com.aihouse.aihousedao.bean.SysUser;
import com.aihouse.aihouseservice.SysOperationLogService;
import com.aihouse.aihousesys.utils.RequestUtils;
import com.aihouse.aihousesys.utils.SessionConstant;
import com.aihouse.aihousesys.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志拦截器
 */
@ComponentScan
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private SysOperationLogService sysOperationLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }
        SysUser user = UserUtils.getUser(request);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(user!=null){
            String uri = request.getRequestURI();
            uri = uri.replace(request.getContextPath(), "");
            // 去除根路径
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            HttpSession session = request.getSession();
            Map<String, SysAuth> href = (Map<String, SysAuth>) session.getAttribute(SessionConstant.userAuth);
            Map<Integer, SysAuth> sysAuthMap = (Map<Integer, SysAuth>) session.getAttribute(SessionConstant.authAllById);
            SysAuth sysAuth=href.get(uri);
            if(sysAuth!=null){
                SysOperationLog sysOperationLog=new SysOperationLog();
                sysOperationLog.setSysUserId(user.getId());
                sysOperationLog.setSysAuthId(sysAuth.getId());
                StringBuilder stringBuilder=new StringBuilder();
                getAuthName(sysAuth,sysAuthMap,stringBuilder);
                sysOperationLog.setAuthName(stringBuilder.substring(0,stringBuilder.length()-2));
                sysOperationLog.setIp(RequestUtils.getIp(request));
                ObjectMapper objectMapper=new ObjectMapper();
                sysOperationLog.setRequestParam(objectMapper.writeValueAsString(request.getParameterMap()));
                sysOperationLog.setAuthHref(uri);
                try {
                    sysOperationLogService.insert(sysOperationLog);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        return true;
    }

    private void getAuthName(SysAuth sysAuth, Map<Integer, SysAuth> sysAuthMap, StringBuilder sb){
        sb.insert(0,sysAuth.getName()+"->");
        if (sysAuth.getParentAuthId()!=null&&!sysAuth.getParentAuthId().equals(0)){
            getAuthName(sysAuthMap.get(sysAuth.getParentAuthId()),sysAuthMap,sb);
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
