package com.one.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * SpringBoot项目是不能直接创建第三方jar包中创建的bean对象的,需要借助@Enable*注解,
 * Springboot提供了很多@Enable开头的注解,这些注解都用于动态开启某些功能,
 * 其底层原理是使用@Import注解导入一些配置类实现bean的动态加载
 *
 * 注解@Enable*是第三方jar包用来主动适配SpringBoot框架,自动创建bean对象给ioc容器,
 * 第三方jar需要自己手动创建希望让ioc容器管理的bean对象, 并自己写好@Enable*注解
 * 然后SpringBoot工程中如果想使用这个第三方jar包中创建的bean对象,就在启动类上使用第三方jar包中定义的@Enable*注解
 * 其实底层是使用@Import注解加载第三方jar包中的bean对象
 * @author one
 * @description: 使用@Enable*注解来加载第三jar的类的bean对象,本质上是对@Import注解的封装
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(UserAutoConfig.class) //@EnableUser注解具有其元注解@Import注解的功能,@Enable注解本质上是对@Import注解的封装
public @interface EnableUser {
}
