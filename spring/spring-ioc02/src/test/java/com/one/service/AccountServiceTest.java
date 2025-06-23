package com.one.service;

import com.one.config.SpringConfig;
import com.one.domain.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @ClassName: AccountServiceTest
 * @Description: 在spring中用注解的方式设置单元测试
 * @Author: one
 * @Date: 2020/12/03
 */
//设置spring专用的类加载器
@RunWith(SpringJUnit4ClassRunner.class)
//设置上下文配置
@ContextConfiguration(classes = SpringConfig.class)
public class AccountServiceTest {
    //用注解的方式进行DI注入
    @Autowired
    private AccountService accountService;

    @Test
    public void  testFind(){
        Account account = accountService.findById(1);
//        System.out.println(account);
        Assert.assertEquals("Mike",account.getName());

        List<Account> accounts = accountService.findAll();
        Assert.assertEquals(3,accounts.size());
    }
}
