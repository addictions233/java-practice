package com.one;

import com.one.service.AccountService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @ClassName: TransactionTest
 * @Description: 对Spring的编程式事务控制和声明式事务控制进行测试
 * @Author: one
 * @Date: 2021/06/08
 */
@ComponentScan("com.one")
@MapperScan("com.one.dao")
//作用是用来引入 InfrastructureAdvisorAutoProxyCreator 这个 BeanPostProcessor 来生成代理对象
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
// 还会引入 TransactionInterceptor 这个 Advisor 来处理事务
public class TransactionApp {
    public static void main(String[] args) {

//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//        AccountService accountService = (AccountService)applicationContext.getBean("accountService");
//        accountService.transferMoney("rose","lucus",100.0);

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TransactionApp.class);
        applicationContext.refresh();
        AccountService accountService = applicationContext.getBean(AccountService.class);
//        accountService.transferMoney("rose","lucus",100.0);
//        accountService.transferMoney2("rose","lucus",100.0);
        accountService.transferMoney5("rose","lucus",100.0);
    }

    /**
     * 使用@Transactional注解生效的前提是创建了事务管理器的Bean对象
     * @param dataSource 事务管理器的dataSource需要和执行sql的dataSource一致, 这样才能控制事务
     * @return 事务管理器
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
