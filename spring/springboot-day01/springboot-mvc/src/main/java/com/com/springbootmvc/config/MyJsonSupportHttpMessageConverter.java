package com.com.springbootmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author one
 * @description TODO
 * @date 2024-1-31
 */
public class MyJsonSupportHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    MyJsonSupportHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, new MediaType("text","json", StandardCharsets.UTF_8));
    }
}
