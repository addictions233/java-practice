package com.one.user.config;

import com.one.user.interceptor.FeignAuthRequestInterceptor;
import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 注意： 此处配置@Configuration注解就会交给spring 容器管理,全局生效，如果想指定对应微服务生效，就不能配置@Configuration
//@Configuration
public class FeignConfig {
    /**
     * 日志级别
     * 通过源码可以看到日志等级有 4 种，分别是：
     * NONE：不输出日志。
     * BASIC：只输出请求方法的 URL 和响应的状态码以及接口执行的时间。
     * HEADERS：将 BASIC 信息和请求头信息输出。
     * FULL：输出完整的请求信息。
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 使用Feign原生的注解配置
     * @return
     */
//    @Bean
//    public Contract feignContract() {
//        return new Contract.Default();
//    }

    /**
     * 开启Basic认证
     * @return
     */
//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("fox","123456");
//    }

    /**
     * 自定义拦截器
     * @return
     */
    @Bean
    public FeignAuthRequestInterceptor feignAuthRequestInterceptor(){
        return new FeignAuthRequestInterceptor();
    }
//
    @Bean
    public Request.Options options() {
        return new Request.Options(3000, 4000);
    }

//    @Bean
//    public Decoder decoder() {
//        return new JacksonDecoder();
//    }
//    @Bean
//    public Encoder encoder() {
//        return new JacksonEncoder();
//    }



}