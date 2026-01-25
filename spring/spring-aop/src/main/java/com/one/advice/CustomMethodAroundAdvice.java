package com.one.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author one
 * @description 自定义环绕通知
 * @date 2024-12-15
 */
public class CustomMethodAroundAdvice implements MethodInterceptor {

    /**
     * 环绕通知, 可以在方法执行前后添加自定义逻辑, 需要实现MethodInterceptor接口
     * @param invocation the method invocation joinpoint
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("方法执行Around前");
        // 调用目标方法
        Object proceed = invocation.proceed();
        System.out.println("方法执行Around后");
        return proceed;
    }
}
