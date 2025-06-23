package com.one.processor;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) bean;
            hikariDataSource.setPassword("root");
        }

        if (bean instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) bean;
            druidDataSource.setPassword("root");
        }
        return bean;
    }
}
