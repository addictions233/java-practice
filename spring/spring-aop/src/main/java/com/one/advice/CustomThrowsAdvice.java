package com.one.advice;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author one
 * @description 自定义异常通知
 * @date 2024-12-15
 */
public class CustomThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target, Throwable e) {
        System.out.println("方法抛出异常后执行...");
    }
}
