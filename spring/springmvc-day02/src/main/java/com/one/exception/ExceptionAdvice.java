package com.one.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ExceptionAdvice
 * @Description: 使用注解开发异常处理器
 * @Author: one
 * @Date: 2020/12/08
 */
// 声明该类是一个Controller类的通知类,声明之后该类会被加载为异常处理器
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody  //返回结果不进入视图解析器,直接放入响应体中返回到浏览器页面
    public String doNullException(Exception e){
        return "空指针异常";
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public String doArigthmeticException(Exception e){
        return "算术异常";
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String doException(Exception e){
        return "all";
    }

}
