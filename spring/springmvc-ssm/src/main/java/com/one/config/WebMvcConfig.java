package com.one.config;


import com.one.interceptor.SimpleAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author one
 * @description 对拦截器进行注册, 手动实现一个简单的认证和授权可以使用拦截器
 * @date 2022-9-18
 */
@EnableWebMvc // 基于注解的mvc配置, 会自动注册常用的 HandlerMapping 和 HandlerAdapter
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 使用自己定义的拦截实现一个简单的认证功能
     */
    @Resource
    private SimpleAuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }
}
