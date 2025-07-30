package com.com.springbootmvc.config;

import com.com.springbootmvc.converter.StringToDateConverter;
import com.com.springbootmvc.httpmessageconverter.MultipartJackson2HttpMessageConverter;
import com.com.springbootmvc.httpmessageconverter.MyJsonSupportHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author one
 * @description 注册返回值处理器
 * @date 2022-9-23
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MultipartJackson2HttpMessageConverter(objectMapper));
        converters.add(new MyJsonSupportHttpMessageConverter(objectMapper));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter("yyyy-MM-dd"));
    }
}
