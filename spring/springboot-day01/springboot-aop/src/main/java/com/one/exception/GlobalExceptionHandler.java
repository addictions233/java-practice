package com.one.exception;

import com.one.domain.HttpResponseEntity;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 实现全局异常处理器
 *         注意: tokenInterceptor拦截器和controller方法中抛出的异常会到全局异常处理器
 *               但是filter过滤器中抛出的异常不会到全局异常处理器中
 *          同样,全局异常处理器中对controller方法抛出的异常进行了处理,那么异常也不会到达tokenInterceptor拦截器
 *          中的afterCompletion()方法的exception对象中,该对象为null
 * @Author: one
 * @Date: 2022/03/24
 */
@ControllerAdvice //全局异常处理器是处理Controller返回的结果,所以用@ControllerAdvice,该注解也能用于其他类
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) // 定义全局异常处理器处理的异常类型
    @ResponseBody // 将方法返回的结果放在响应体中
    public HttpResponseEntity<Void> handlerException(Exception ex) {
        System.out.println("全局异常处理器执行了...");
        log.error("throw exception:",ex);
        // 同一使用返回结果
        HttpResponseEntity<Void> responseEntity = new HttpResponseEntity<>();
        responseEntity.setStatus("FAIL");
        if (ex instanceof ApplicationContext) {  // 抛出的自定义异常
            ApplicationException exception = (ApplicationException)ex;
            responseEntity.setCode(exception.getCode());
            responseEntity.setErrorMsg(exception.getMessage());
        } else { // 未知异常
            responseEntity.setCode(500);
            responseEntity.setErrorMsg(ex.getMessage());
        }
        // 给前端响应
        return responseEntity;
    }
}
