package com.one;

import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class ProxyFactoryTest {

    @Test
    public void testProxyFactory() {
        UserService userService = new UserServiceImpl();

        // spring aop 代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();

        // 设置目标对象
        proxyFactory.setTarget(userService);

        // 设置代理逻辑
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("before advice...");
//                method.invoke(target, args); // 调用目标对象的方法, spring aop 会自动调用目标对象的方法, 这里不用手动调用
            }
        });

        // 创建代理对象
        UserService proxy = (UserService) proxyFactory.getProxy();

        proxy.save();

    }
}
