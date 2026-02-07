package com.one;

import com.one.config.SpringConfig;
import com.one.domain.Account;
import com.one.service.AccountService;
import com.one.service.impl.AccountServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class IocApp {
    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//
//
//        UserService userService = (UserService) ctx.getBean("userService");
//        userService.save();

            //测试 注解引入第三方jar包类的Bean对象
//        DataSource dataSource = (DataSource) ctx.getBean("getDataSource");
//        System.out.println(dataSource);


//        DataSource dataSource = ctx.getBean("dataSource",DataSource.class);
//        System.out.println(dataSource);
//        BookDao bookDao = ctx.getBean("bookDao", BookDaoImpl.class);
//        bookDao.save();

        // 纯注解开发方式下获取ApplicationContext对象
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ctx.getBean("accountService", AccountServiceImpl.class);
        Account account = accountService.findById(1);
        System.out.println(account);
        List<Account> all = accountService.findAll();
        System.out.println(all);
//


    }
}
