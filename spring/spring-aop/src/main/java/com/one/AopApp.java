package com.one;

import com.one.advice.CustomAroundAdvice;
import com.one.service.UserService;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: App
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/02
 */
@ComponentScan("com.one")
//@EnableAspectJAutoProxy(proxyTargetClass = true) // 使用@Enable*注解启用aspectj注解的支持, 本质上还是引入AnnotationAwareAspectJAutoProxyCreator
public class AopApp {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        UserService userService = (UserService)applicationContext.getBean("userService");
//        userService.save();
//        double money = userService.sell(200, 2);
//        System.out.println("最后的总价:"+ money);


        AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext(AopApp.class);
        UserService userService1 = (UserService) applicationContext1.getBean("userService");
        userService1.save();

    }

    /**
     * 定义用来生成代理对象的BeanPostProcessor
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 定义Advisor
     * @return
     */
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.addMethodName("save");
        // Advisor = Advice + Pointcut
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(new CustomAroundAdvice());
        return advisor;
    }
}
