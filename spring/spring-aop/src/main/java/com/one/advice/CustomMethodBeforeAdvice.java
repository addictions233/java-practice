package com.one.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author one
 * @description 自定义前置通知
 * @date 2024-12-15
 */
public class CustomMethodBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法执行之前执行..");
    }
}
