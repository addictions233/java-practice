package com.one.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

/**
 * @author one
 * @date 2022-9-25
 * @description 测试读取第三方配置文件
 *   注解@PropertySource是Spring用于加载第三方配置文件，默认支持.properties与.xml两种配置文件。@PropertySource属性如下：
 *        name：默认为空，不指定Spring自动生成
 *        value：配置文件
 *        ignoreResourceNotFound：没有找到配置文件是否忽略，默认false，4.0版本加入
 *        encoding：配置文件编码格式，默认UTF-8 4.3版本才加入
 *        factory：配置文件解析工厂，默认：PropertySourceFactory.class 4.3版本才加入，如果是之前的版本就需要手动注入配置文件解析Bean
 */
@Data
@Repository
@PropertySource("classpath:jdbc.properties")
public class JDBCProperty {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.userName}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
}
