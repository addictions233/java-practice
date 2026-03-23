package com.one;

import com.one.config.JDBCConfig;
import com.one.config.MyBatisConfig;
import com.one.domain.Account;
import com.one.service.AccountService;
import com.one.service.impl.AccountServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.util.List;

@ComponentScan("com.one")  //指定spring在初始化容器时要扫描的包,指定纯注解方式下扫描的包路径(该路径下包含有所有注解的Bean类)
@PropertySource("classpath:jdbc.properties")  //引入资源文件
//@MapperScan("com.one.dao") // 扫描mapper层接口, 效果等同于在IOC容器中注册 MapperScannerConfigurer 的Bean对象
@Import({JDBCConfig.class, MyBatisConfig.class}) //引入第三方Bean作为被spring控制的资源
public class IocApp {
    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
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
        ApplicationContext ctx = new AnnotationConfigApplicationContext(IocApp.class);
        AccountService accountService = ctx.getBean("accountService", AccountServiceImpl.class);
        Account account = accountService.findById(1);
        System.out.println(account);
        List<Account> all = accountService.findAll();
        System.out.println(all);
//


    }
}
