package com.one.base.proxy;

import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @ClassName: App
 * @Description: 测试JDK动态代理
 * @Author: one
 * @Date: 2020/12/04
 */
public class AopTest {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        // 使用jdk动态代理生成代理对象
        UserService userService1 =  (UserService) JDKProxy.createUserServiceJDKProxy(userService);
        userService1.save();

        System.out.println("-----------------------------------------------------------");
        // 使用CGLIB动态代理生成代理对象
        UserServiceImpl userService2 = CGLIBProxy.createUserServiceCglibProxy(UserServiceImpl.class);
        userService2.save();

        System.out.println("------------------------------------------");
        // spring为了集成上述两种动态代理选择了代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(userService);
        proxyFactory.addAdvice(new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("proxy before advice...");
//                method.invoke(target,args);
            }
        });
        UserService userService3 = (UserService)proxyFactory.getProxy();
        userService3.save();
    }
}
