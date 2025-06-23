package com.com.springbootmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

/**
 * @author one
 * @description 使用@RequestPart注解必须注册自定义springmvc的返回值处理器(区别于参数解析器)
 * @date 2022-9-23
 */
//@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }
//
//    @Override
//    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//        return false;
//    }
//
//    @Override
//    protected boolean canWrite(MediaType mediaType) {
//        return false;
//    }
//
//    @Override
//    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
//        return false;
//    }
}
