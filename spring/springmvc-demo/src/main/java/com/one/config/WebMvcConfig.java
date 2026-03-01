package com.one.config;

import com.one.converter.MyDateConverter;
import com.one.interceptor.MyHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author one
 * 采用零配置的方式来使用springmvc, 可以添加一些自定义的springmvc核心组件
 */
@Configuration
// @EnableWebMvc 在 springmvc 用来替代<mvc:annotation-driven>, 而在springboot项目中, 直接使用 WebMvcConfigurer
@EnableWebMvc // 在配置类上添加 @EnableWebMvc 注解, 会自动注册常用的 HandlerMapping 和 HandlerAdapter
@ComponentScan("com.one.controller")
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 配置自定义的视图解析器
     * @return 视图解析器
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * 添加自定义的拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor());
    }

    /**
     * 配置自定义的消息转换器
     * @param converters the list of configured converters to extend.
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        // 消息转换器支持中文响应的编码
        stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(1, stringHttpMessageConverter);

        // 执行响应体返回JSON数据
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        converters.add(jackson2HttpMessageConverter);
    }
}