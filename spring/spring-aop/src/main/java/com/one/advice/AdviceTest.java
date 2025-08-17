package com.one.advice;

import com.one.service.UserService;
import com.one.service.impl.UserServiceImpl;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author one
 * @description 测试自定义Advice使用
 * @date 2024-12-15
 */
public class AdviceTest {

    public static void main(String[] args) {
        UserService target = new UserServiceImpl();
        // 使用spring提供的proxyFactory生成代理对象
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(new CustomBeforeAdvice());
        proxyFactory.addAdvice(new CustomAfterReturningAdvice());
        proxyFactory.addAdvice(new CustomAroundAdvice());

        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.save();
    }
}
