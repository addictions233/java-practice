package com.one.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: ExceptionResolver
 * @Description: 自己写一个异常处理器类
 * @Author: one
 * @Date: 2020/12/08
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.out.println("异常处理器正在执行中");
        ModelAndView modelAndView = new ModelAndView();
        //定义异常出现后,返回给用户的页面
        modelAndView.setViewName("error.jsp");
        modelAndView.addObject("msg","出错了");
        return modelAndView;

    }
}
