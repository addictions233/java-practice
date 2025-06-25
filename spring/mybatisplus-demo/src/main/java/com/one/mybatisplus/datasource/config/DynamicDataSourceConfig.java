//package com.one.mybatisplus.datasource.config;
//
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import com.one.mybatisplus.datasource.DynamicDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @ClassName: DynamicDataSource
// * @Description: 配置动态数据源
// * @Author: one
// * @Date: 2021/08/31
// */
////@Configuration
//public class DynamicDataSourceConfig {
//
//    @Bean("master")
//    @ConfigurationProperties("spring.datasource.druid.master")
//    public DataSource masterDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean("slave")
//    @ConfigurationProperties("spring.datasource.druid.slave")
//    public DataSource slaveDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean("test")
//    @ConfigurationProperties("spring.datasource.druid.test")
//    public DataSource dbTestDataSource() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    /**
//     * 使用mybatis plus提供的动态数据源
//     * @param master
//     */
////    @Bean
////    @Primary
//    public DynamicDataSource dynamicDataSource(@Qualifier("master") DataSource master,
//                                               @Qualifier("slave") DataSource slave,
//                                               @Qualifier("test") DataSource test) {
//        Map<String, DataSource> targetDataSourceMap = new HashMap<>(3);
//        targetDataSourceMap.put("master", master);
//        targetDataSourceMap.put("slave", slave);
//        targetDataSourceMap.put("test", test);
//        return new DynamicDataSource(master, targetDataSourceMap);
//    }
//
//}
