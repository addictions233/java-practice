package com.one.interfaceidempotence.config;

import com.one.interfaceidempotence.intceptor.ApiIdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MvcConfig
 * @Description: 让自定义拦截器生效的配置类,将自定义拦截器加入到拦截器链中
 * @Author: one
 * @Date: 2021/03/24
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private ApiIdempotentInterceptor apiIdempotentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 将自定义拦截器加入到拦截器链中
         * "/**"表示拦截所有的请求
         * addInterceptor 需要添加一个实现了HandlerInterceptor接口的拦截器实例
         *             addPathPatterns 用于设置拦截器的过滤路径规则, addPatnPatterns("/**")对所有请求进行拦截
         *             excludePathPatterns:  用于设置不需要拦截器的过滤规则
         */
        registry.addInterceptor(apiIdempotentInterceptor).addPathPatterns("/**");
    }
}
