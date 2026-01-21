package com.one.base.proxy;

import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * @ClassName: UserServiceJDKProxy
 * @Description: jdk动态代理
 * @Author: one
 * @Date: 2020/12/04
 */
public class JDKProxy {

    /**
     * JDK动态代理: 只有代理对象proxy调用被代理方法才会进入#invoke代理逻辑
     * @param target 被代理对象
     * @return 代理对象
     */
    public static Object createUserServiceJDKProxy(Object target){
        //获取类加载器
        ClassLoader classLoader = target.getClass().getClassLoader();
        //获取接口
        Class<?>[] interfaces = target.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("jdk proxy before...");
                Object result = method.invoke(target, args);
//                Object result = method.invoke(proxy, args); // 会进入死循环
                System.out.println("jdk proxy after...");
                return result;
            }
        });
    }
}
