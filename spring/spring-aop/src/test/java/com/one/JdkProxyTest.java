package com.one;

import com.one.base.proxy.JDKProxy;
import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTest {
    @Test
    public void testJdkProxy() {

        UserService userService = new UserServiceImpl();

        // 使用jdk动态代理生成代理对象
        UserService proxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("jdk proxy before advice...");
                        method.invoke(userService, args); // 注意：这里是调用目标对象的方法,不是代理对象的方法
                        return null;
                    }
                });

        proxy.save();
    }
}
