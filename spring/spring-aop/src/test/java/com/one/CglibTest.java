package com.one;

import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

public class CglibTest {

    @Test
    public void testCglib() {

        Enhancer enhancer = new Enhancer();

        // 设置代理类, 只能设置一个, java是单继承
        enhancer.setSuperclass(UserServiceImpl.class);

        // 也可以让生产的代理对象实现UserService接口, 这里传数组, 应该java是对接口多实现
        enhancer.setInterfaces(new Class[]{UserService.class});

        // 设置代理拦截器
        enhancer.setCallbacks(new Callback[] {
                // 第一个拦截器
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object proxy, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        System.out.println("before");
                        // 由于cglib是基于继承来实现的, 所以这里不能用method.invoke来调用方法, 否则会进入死循环
                        // 要调用父类的方法, 才能避免死循环, 才能调用到代理原方法
                        // Object result = method.invoke(target, objects); // 会进入死循环
                        methodProxy.invokeSuper(proxy, objects);
                        return null;
                    }
                },
                // 第二个拦截器, 什么也不做
                NoOp.INSTANCE
        });

        // 设置拦截器过滤器
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                // 只有save方法才会被第一个拦截器拦截
                return method.getName().equals("save") ? 0 : 1;
            }
        });

        // 创建代理对象
        UserServiceImpl userService = (UserServiceImpl) enhancer.create();

        userService.save(); // 这里会调用第一个拦截器, 打印before

        userService.save2(); // 这里会调用第二个拦截器, 什么也不做
    }
}
