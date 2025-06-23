package com.one.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.PrintService;

/**
 * @ClassName: PersonConfig
 * @Description: 使用@ConfigurationProperties修饰方法,直接读取配置文件内容
 * @Author: one
 * @Date: 2022/04/07
 */
@Configuration
public class PersonConfig {
    /**
     * 使用@ConfigurationProperties+@Bean式配置法属于隐式绑定
     * 要求配置文件编写的key与对应类的字段名称相同
     * @return Person
     */
    @ConfigurationProperties(prefix = "person2")
    @Bean("person2")
    public Person person() {
        // 隐式绑定配置中心的数据
        return new Person();
    }
}
