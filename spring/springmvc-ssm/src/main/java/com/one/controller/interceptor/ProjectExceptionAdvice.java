package com.one.controller.interceptor;

import com.one.system.exception.BusinessException;
import com.one.system.exception.SystemException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ProjectExceptionAdvice
 * @Description: 项目异常处理的拦截器
 * @Author: one
 * @Date: 2020/12/09
 */
@ControllerAdvice
public class ProjectExceptionAdvice {

    /**
     * 对BusinessException进行异常拦截处理
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String doBusinessException(BusinessException e){
        return e.getMessage();
    }

    /**
     * 对BusinessException进行异常拦截处理
     */
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public String doSystemException(SystemException e){
        return e.getMessage();
    }
}
