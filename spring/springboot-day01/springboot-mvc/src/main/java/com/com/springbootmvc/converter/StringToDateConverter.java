package com.com.springbootmvc.converter;

import org.springframework.core.convert.converter.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转换为日期类型的转换器
 * 这里的Convert接口与HttpMessageConverter接口截然不同，
 * 后者是用于从HTTP请求和响应转换为HTTP请求和响应的策略接口。
 * Converter接口
 * 用于将HTTP请求中的字符串参数转换为目标Java类型，例如将"123"转换为Integer或自定义对象‌
 * 它支持处理：
 * 注解@RequestParam参数：如查询字符串或表单数据（application/x-www-form-urlencoded）。
 * 注解@PathVariable参数：URL路径变量。
 * 注解@ModelAttribute参数：表单绑定对象‌
 * Converter不直接处理@RequestBody参数。@RequestBody用于请求体（如JSON或XML），
 * 由HttpMessageConverter（如MappingJackson2HttpMessageConverter）独立处理，而非ConversionService
 */
public class StringToDateConverter implements Converter<String, Date> {

    private final String datePattern;

    public StringToDateConverter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public Date convert(String source) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            // 处理转换异常
            return null; // 或者抛出异常
        }
    }
}