package com.one;

import com.one.base.proxy.JDKProxy;
import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.junit.Test;

public class JdkProxyTest {
    @Test
    public void testJdkProxy() {
        UserService userService = new UserServiceImpl();
        // 使用jdk动态代理生成代理对象
        UserService userService1 =  (UserService) JDKProxy.createUserServiceJDKProxy(userService);
        userService1.save();
    }
}
