package com.one.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author one
 * @description 自定义环绕通知
 * @date 2024-12-15
 */
public class CustomAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("方法执行Around前");
        Object proceed = invocation.proceed();
        System.out.println("方法执行Around后");
        return proceed;
    }
}
