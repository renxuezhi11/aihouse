package com.aihouse.aihouseapp.interceptor;

import com.aihouse.aihouseapp.utils.AppRuntimeException;
import com.aihouse.aihousecore.utils.DataRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = AppRuntimeException.class)
    @ResponseBody
    public DataRes defaultErrorHandler(HttpServletRequest req, AppRuntimeException e)  {
        return DataRes.error(e.getCode()+"",e.getMessage());
    }
}
