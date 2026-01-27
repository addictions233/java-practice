package com.one.advisor;

import com.one.advice.CustomMethodAroundAdvice;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class CustomPointcutAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {

            /**
             * 定义Pointcut切入点
             * @return Pointcut
             */
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return method.getName().equals("save");
            }
        };
    }

    /**
     * 定义Advice通知
     * @return Advice
     */
    @Override
    public Advice getAdvice() {
        return new CustomMethodAroundAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
