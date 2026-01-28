package com.one;

import com.one.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: TransfactionTest
 * @Description: 对Spring的编程式事务控制和声明式事务控制进行测试
 * @Author: one
 * @Date: 2021/06/08
 */
@ComponentScan("com.one")
@EnableTransactionManagement //作用是用来引入 InfrastructureAdvisorAutoProxyCreator 这个 BeanPostProcessor 来生成代理对象
// 还会引入 TransactionInterceptor 这个 Advisor 来处理事务
public class TransactionApp {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AccountService accountService = (AccountService)applicationContext.getBean("accountService");
//        accountService.transferMoney("rose","lucus",100.0);
//        AccountService accountServiceImpl01 = (AccountService) applicationContext.getBean("accountServiceImpl01");
//        accountServiceImpl01.transfer("rose","lucus",100.00);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TransactionApp.class);
        applicationContext.refresh();
        AccountService accountService = applicationContext.getBean(AccountService.class);
        accountService.transferMoney("rose","lucus",100.0);
    }
}
