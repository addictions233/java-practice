package com.one.advisor;

import com.one.advice.CustomAfterReturningAdvice;
import com.one.advice.CustomAroundAdvice;
import com.one.advice.CustomBeforeAdvice;
import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author one
 * @description 测试Advisor的使用: Advisor = Advice + Pointcut
 * @date 2024-12-15
 */
public class AdvisorTest {

    public static void main(String[] args) {
        UserService target = new UserServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvisor(new PointcutAdvisor() {
            /**
             * 定义Pointcut切入点
             * @return Pointcut
             */
            @Override
            public Pointcut getPointcut() {
                return new StaticMethodMatcherPointcut() {
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
                return new CustomAroundAdvice();
            }

            @Override
            public boolean isPerInstance() {
                return false;
            }
        });

        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.save();
    }
}
