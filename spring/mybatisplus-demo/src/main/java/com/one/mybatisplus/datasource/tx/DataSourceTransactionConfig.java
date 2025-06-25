//package com.one.mybatisplus.datasource.tx;
//
//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.support.TransactionTemplate;
//
////@Configuration
//public class DataSourceTransactionConfig {
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DynamicRoutingDataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
//        return new TransactionTemplate(transactionManager);
//    }
//}