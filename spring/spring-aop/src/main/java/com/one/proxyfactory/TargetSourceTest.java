package com.one.proxyfactory;

import com.one.advice.CustomMethodBeforeAdvice;
import com.one.base.decorator.UserServiceImplDecorator;
import com.one.service.impl.UserServiceImpl;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;

public class TargetSourceTest {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();

        // 1.设置目标对象, 不直接设置代理对象, 而是设置目标对象, 代理对象会在后面根据目标对象动态生成
        proxyFactory.setTargetSource(new TargetSource() {
            @Override
            public Class<?> getTargetClass() {
                return UserServiceImpl.class;
            }

            @Override
            public boolean isStatic() {
                return false;
            }

            @Override
            public Object getTarget() throws Exception {
                return new UserServiceImpl();
            }

            @Override
            public void releaseTarget(Object target) throws Exception {

            }
        });

        // 2.设置通知, 通知会在后面根据目标对象动态生成代理对象时, 织入到代理对象中
        proxyFactory.addAdvice(new CustomMethodBeforeAdvice());

        UserServiceImpl proxy = (UserServiceImpl) proxyFactory.getProxy();
        proxy.save();
    }
}
