package com.one;

import com.one.advice.CustomMethodAroundAdvice;
import com.one.advice.CustomMethodBeforeAdvice;
import com.one.service.UserService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName: App
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/02
 */
@ComponentScan("com.one")
// 启用aspectj注解的支持, 本质上还是引入AnnotationAwareAspectJAutoProxyCreator, 用来创建代理对象
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopApp {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        UserService userService = (UserService)applicationContext.getBean("userService");
//        userService.save();
//        double money = userService.sell(200, 2);
//        System.out.println("最后的总价:"+ money);

        // 基于注解的方式启动IOC容器
        AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext(AopApp.class);

//        UserService userService1 = applicationContext1.getBean("proxyFactoryBean", UserService.class);

        UserService userService1 = (UserService) applicationContext1.getBean("userService");
        userService1.save();

    }

    /*** ------------------------------利用ProxyFactoryBean接生成一个代理对象----------------------------------- ***/

    /**
     * 定义一个ProxyFactoryBean, 用来生成代理对象并注入到IOC容器中
     * @return ProxyFactoryBean 本质上是一个FactoryBean, 用来生成代理对象
     */
    @Bean
    public ProxyFactoryBean proxyFactoryBean(){
        ProxyFactoryBean proxy = new ProxyFactoryBean();
        proxy.setTargetName("userService");
        proxy.addAdvice(new CustomMethodBeforeAdvice());
        return proxy;
    }

    /** ----------------------------------利用BeanNameAutoProxyCreator生成代理对象----------------------------------- ***/

    @Bean
    public CustomMethodBeforeAdvice customMethodBeforeAdvice() {
        return new CustomMethodBeforeAdvice();
    }

//    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        // 根据BeanName来判断是否生成代理对象
        proxyCreator.setBeanNames("userSer*");
        // 为符合条件的Bean添加Advice
        proxyCreator.setInterceptorNames("customMethodBeforeAdvice");
        return proxyCreator;
    }

    /*** ------------------------------利用DefaultAdvisorAutoProxyCreator生成代理对象----------------------------------- ***/

    /**
     * 用来自动化生成代理对象的BeanPostProcessor
     * @return DefaultAdvisorAutoProxyCreator 用来自动化生成代理对象的BeanPostProcessor
     */
//    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 定义Advisor, 提供给上面的DefaultAdvisorAutoProxyCreator 来使用
     * @return DefaultPointcutAdvisor 定义的Advisor, 提供给上面的DefaultAdvisorAutoProxyCreator 来使用
     */
//    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        // 定义切入点
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        // 只切save方法
        pointcut.addMethodName("save");
        // Advisor = Advice + Pointcut
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new CustomMethodAroundAdvice());
        return advisor;
    }
}
