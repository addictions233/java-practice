package com.one.advice;

import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author one
 * @description 自定义正常返回通知
 * @date 2024-12-15
 */
public class CustomAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法正常结束后执行...");
    }
}
