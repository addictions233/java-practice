package com.one.service.impl;


import com.one.dao.AccountDao;
import com.one.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * Spring的事务控制是在service业务层,不是在Dao层,这样可以控制多条sql语句的事务
 * 主要使用三个类:
 *   1, 平台式事务控制器 PlatformTransactionManager
 *   2, 事务定义对象 TransactionDefinition
 *   3, 事务状态对象 TransactionStatus
 *  Mybatis只能实现在Dao层的事务控制,如果想要在service层对多个数据进行操作,就需要在service层进行事务控制,
 *  将数据库的多个操作组成一个共同事务,要不同时完成,要不同时回滚,达到一致性
 *
 * @author one
 * @description 学习spring在数据层面的事务控制
 * 事务的五个要素都定义在了 TransactionDefinition类中:
 * 1,事务名称   String getName()
 * 2,只读事务或者读写事务 boolean isReadOnly()
 * 3,事务隔离级别   int getIsolationLevel()
 * 4,事务的超时时间 int getTimeOut()   默认  int TIMEOUT_DEFAULT = -1 永不超时
 * 5,事务的传播行为  int getPropagationBehavior()
 */
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * service层注入dao层对象作为成员变量
     */
    private AccountDao accountDao;

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    /**
     * 使用编程式事务控制, 需要在控制事务的地方注入dataSource数据源对象
     * PlatformTransactionManager平台事务管理器对象中的datasource数据源一定要和构造mybatis的
     * datasource数据源一致, 这样才能保证Spring的编程式事务控制能生效
     */
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 1,第一种:没有进行事务控制的情况下进行转账
     *
     * @param outName 出账账户
     * @param inName  入账账户
     * @param money   金额
     */
//    @Override
//    public void transfer(String outName, String inName, Double money) {
//        accountDao.inMoney(outName, money);
//        int i = 1 / 0;
//        accountDao.outMoney(inName, money);
//    }

    /**
     * 第二种: 用Spring的编程式事务管理器,即用java代码实现Service层事务管理
     *
     * @param outName 出账账户
     * @param inName 入账账户
     * @param money 金额
     */
    @Override
    public void transferMoney(String outName, String inName, Double money) {
        PlatformTransactionManager platformTransactionManager = null;
        TransactionStatus transactionStatus = null;
        try {
            // 获取平台事务管理器: 包括dataSourceTransactionManager, jpaTra
            platformTransactionManager = new DataSourceTransactionManager(dataSource);
            // 获取事务定义对象: 采用默认的事务定义对象
            // DefaultTransactionDefinition类是一个默认的TransactionDefinition接口的实现, 它的传播行为是PROPAGATION_REQUIRED(如果当前没有事务,就创建一个,
            // 如果当前有事务,就加入当前事务), 隔离级别是数据库的隔离级别, getTransaction()方法中会调用startTransaction()来开启事务
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            // 1, 获取事务(开启事务): getTransaction()方法: 返回一个已经存在的事务或者创建一个新的事务
            transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
            //执行业务方法
            accountDao.inMoney(inName,100.0);
            int i = 1/0;
            accountDao.outMoney(outName,100.0);
            // 2, 提交事务
            platformTransactionManager.commit(transactionStatus);
        } catch (TransactionException e) {
            e.printStackTrace();
            // 3, 回滚事务
            if (platformTransactionManager != null && transactionStatus != null) {
                platformTransactionManager.rollback(transactionStatus);
            }
        }
    }

    //3,第三种:用AOP和自己手写通知类来进行事务控制  编程式事务控制
//    @Override
//    public void transfer(String outName,String inName,Double money){
//        accountDao.outMoney(inName,100.0);
//        int i = 1/0;
//        accountDao.inMoney(outName,100.0);
//    }

//    /**
//     * 4,第四种: 使用Spring中的声明式事务控制   xml配置方式
//     * @param outName
//     * @param inName
//     * @param money
//     */
//    @Override   //Spring中事务控制就是这用XML方式下声明式注解开发方式
//    public void transfer(String outName,String inName, Double money){
//        accountDao.outMoney(inName,100.0);
//        int i = 1/0;
//        accountDao.inMoney(outName,100.0);
//    }

    //5,第五种: 使用Spring中的声明式事务控制    基于注解的开发方式
//    @Override
//    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
//    public void transferMoney(String outName,String inName, Double money){
//        accountDao.outMoney(inName,100.0);
//        int i = 1/0;
//        accountDao.inMoney(outName,100.0);
//    }


}
