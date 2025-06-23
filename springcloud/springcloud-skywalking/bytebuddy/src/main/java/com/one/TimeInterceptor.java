package com.one;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author one
 * 在bytebuddy的拦截器中执行具体的业务逻辑
 */
public class TimeInterceptor {
    
    @RuntimeType
    public static Object intercept(@Origin Method method,
            @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原方法执行
            return callable.call();
        } finally {
            // 打印方法执行时间
            System.out.println(method + ": cost " + (System.currentTimeMillis() - start) + "ms");
        }
    }
    
}
