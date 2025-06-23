package com.one.config;

import com.one.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @ClassName: WebConfig
 * @Description: 要让拦截器生效必须将自定义拦截器对象加入到InterceptorRegistry拦截器注册中心中
 * @Author: one
 * @Date: 2022/03/25
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry添加拦截器的先后顺序,即拦截器执行的先后顺序
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // /**表示拦截所有请求
                .excludePathPatterns("/user"); // 排除的拦截路径
        // 拦截器只对登录的请求进行拦截,对/user的请求不进行拦击
    }
}
