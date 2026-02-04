package com.one.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

/**
 * @ClassName: AOPAdvice
 * @Description: 所有的通知方法都写在本类中
 * @Author: one
 * @Date: 2020/12/05
 */
public class TxAdvice {
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * 环绕通知: around类型的advice
     * @param joinPoint 连接点
     */
    public void transactionManager(ProceedingJoinPoint joinPoint){
        PlatformTransactionManager platformTransactionManager = null;
        TransactionStatus transactionStatus = null;
        try {
            // 获取平台事务管理器对象: JDBC使用事务接口的实现对象是: dataSourceTransactionManager
            platformTransactionManager = new DataSourceTransactionManager(dataSource);
            // 获取事务定义对象,使用默认事务定义对象
            TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            // 1,获取事务, 开启事务:
            transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
            // 执行业务层方法,使用切面默认的方法
            Object result = joinPoint.proceed(joinPoint.getArgs());
            // 2,提交事务
            platformTransactionManager.commit(transactionStatus);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //3, 回滚事务
            if( platformTransactionManager != null && transactionStatus != null) {
                platformTransactionManager.rollback(transactionStatus);
            }
        }
    }
}
