package com.one.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author one
 * @description 配置dataSource
 * @date 2022-9-25
 */
@Configuration
public class JDBCConfig {
    @Bean
    public DataSource dataSource(@Autowired JDBCProperty jdbcProperty) {
        System.out.println("读取到的配置信息:" + jdbcProperty);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(jdbcProperty.getDriver());
        dataSource.setUrl(jdbcProperty.getUrl());
        dataSource.setUsername(jdbcProperty.getUserName());
        dataSource.setPassword(jdbcProperty.getPassword());
        return dataSource;
    }
}
