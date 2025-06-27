package com.one.service;

import com.one.domain.Account;
import com.one.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @ClassName: TestAccountService
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/02
 */
public class AccountServiceTest2 {
    @Test
    public void testAccountService(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        AccountService accountService = ctx.getBean("accountService", AccountServiceImpl.class);
//        accountService.save(new Account(null,"rose",10000.00));
        List<Account> all = accountService.findAll();

        System.out.println(all);
    }
}
