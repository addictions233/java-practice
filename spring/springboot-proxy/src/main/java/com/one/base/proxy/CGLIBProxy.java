package com.one.base.proxy;

import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * @ClassName: UserServiceCGLIBProxy
 * @Description: CGLIB动态代理
 * @Author: one
 * @Date: 2020/12/05
 */
public class CGLIBProxy {

    public static <T> T createUserServiceCglibProxy(Class<T> clazz){
        //创建Enhancer对象(可以理解为动态的创建一个类的字节码)
        Enhancer enhancer = new Enhancer();
        //设置Enhancer对象的父类是指定类型UserServiceImpl
        enhancer.setSuperclass(clazz);
        //设置回调方法
        enhancer.setCallbacks(new Callback[] {
                new MethodInterceptor() {
                    /**
                     * 只用代理对象proxy调用被代理方法method就会进入#intercept代理逻辑
                     * @param proxy 生成的代理对象
                     * @param method 被代理的方法
                     * @param args 方法参数
                     * @param methodProxy 生成的代理方法
                     */
                    @Override
                    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                        System.out.println("cglib proxy before ...");
                        // 调用目标对象的方法, 调用代理对象的父类的这个方法,就是被代理方法
//                        Object result = methodProxy.invokeSuper(proxy, args);
                        Object result = methodProxy.invoke(proxy, args); // 会进入死循环, 无限自调用
//                        Object result = method.invoke(proxy, args); // 会进入死循环
                        System.out.println("cglib proxy after ...");
                        return result;
                    }
                }, NoOp.INSTANCE
        });
        enhancer.setCallbackFilter(new CallbackFilter() {
            /**
             * @return 根据返回结果选择不同下标对应的方法拦截器
             */
            @Override
            public int accept(Method method) {
                if (method.getName().equals("save")) {
                    return 0;  // save()方法使用第二个通知方法
                } else {
                    return 1; // sell()方法使用第一个通知方法 NoOp.INSTANCE
                }
            }
        });
        // 使用Enhancer创建对应的代理对象并返回
        return (T)enhancer.create();
    }
}
