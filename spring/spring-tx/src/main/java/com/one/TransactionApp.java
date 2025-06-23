package com.one;

import com.one.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: TransfactionTest
 * @Description: 对Spring的编程式事务控制和声明式事务控制进行测试
 * @Author: one
 * @Date: 2021/06/08
 */
public class TransactionApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = (AccountService)applicationContext.getBean("accountService");
        accountService.transferMoney("rose","lucus",100.0);
//        AccountService accountServiceImpl01 = (AccountService) applicationContext.getBean("accountServiceImpl01");
//        accountServiceImpl01.transfer("rose","lucus",100.00);
    }
}
