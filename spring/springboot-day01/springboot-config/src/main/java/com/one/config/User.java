package com.one.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName: User
 * @Description: 使用@ConfigurationProperties注解读取配置文件,对Bean对象进DI注入
 *  注解@ConfigurationProperties的作用: 读取SpringBoot核心配置文件,创建该类Bean对象时自动进行属性注入
 * @Author: one
 * @Date: 2020/12/15
 */
@Component
@Data
@ConfigurationProperties(prefix = "user")
public class User {
    private String name;
    private Integer age;
    private String address;
}
