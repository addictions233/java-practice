package com.one.springsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * @author one
 * @description SpringSecurity默认提供了登录页面, 访问地址:  http://localhost:8080/login
 */
//@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })  // 关闭spring security
@SpringBootApplication
//@EnableWebSecurity  // 在 Spring Boot 2.7+ / 3.x 项目中，不需要显式添加 @EnableWebSecurity
public class MainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
		// 获取所有的默认的过滤器链
		DefaultSecurityFilterChain filterChains = context.getBean(DefaultSecurityFilterChain.class);

	}

}