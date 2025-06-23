package com.com.springbootmvc.config;

import com.com.springbootmvc.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter> myFilter() {
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/*"); // 设置过滤器应用于哪些URL模式
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置过滤器的顺序（数字越小，优先级越高）
        return registrationBean;
    }
}