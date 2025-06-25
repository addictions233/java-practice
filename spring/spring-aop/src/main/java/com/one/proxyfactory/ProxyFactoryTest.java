package com.one.proxyfactory;

import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author one
 * @description spring中可以直接使用ProxyFactory生成代理对象
 * @date 2024-12-15
 */
public class ProxyFactoryTest {

    public static void main(String[] args) {
        UserService target = new UserServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setInterfaces(UserService.class);
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before advice 执行了...");
            }
        });

        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.save();
    }
}
