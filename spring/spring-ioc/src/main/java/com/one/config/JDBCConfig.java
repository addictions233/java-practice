package com.one.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: JDBCConfig
 * @Description:  获取 DruidDataSource的Bean对象的配置类
 * @Author: one
 * @Date: 2020/12/02
 */

@PropertySource("classpath:jdbc.properties")
public class JDBCConfig {
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    //@Bean()的参数用于指定Bean对象的id值,当不写时，默认值是Bean对象的id值当前方法的方法名
    @Bean("dataSource")
    public DruidDataSource getDataSource(){
//        System.out.println(driver);
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(driver);
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);

        return dds;
    }
}
