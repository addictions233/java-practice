package com.one.mybatisplus.datasource.annotation;

import java.lang.annotation.*;

/**
 * @Description 实现aop的注解: mybatis plus自己提供一套动态数据源实现{@link com.baomidou.dynamic.datasource.annotation.DS}
 * @author one
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
