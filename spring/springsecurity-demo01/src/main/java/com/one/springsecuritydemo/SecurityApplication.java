package com.one.springsecuritydemo;

import com.one.springsecuritydemo.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.DefaultSecurityFilterChain;

import java.util.Collections;
import java.util.List;

/**
 * @author one
 * @description SpringSecurity默认提供了登录页面, 访问地址:  http://localhost:8080/login
 */
//@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })  // 关闭spring security
@SpringBootApplication
//@EnableWebSecurity  // 在 Spring Boot 2.7+ / 3.x 项目中，不需要显式添加 @EnableWebSecurity
public class SecurityApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SecurityApplication.class, args);
		// 获取所有的默认的过滤器链
		DefaultSecurityFilterChain filterChains = context.getBean(DefaultSecurityFilterChain.class);

	}

	/**
	 * 使用FilterRegistrationBean来注册Filter过滤器
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<MyFilter> myFilterFilterRegistrationBean() {
		FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new MyFilter());
		registrationBean.setUrlPatterns(Collections.singletonList("/*"));
		return registrationBean;
	}

}