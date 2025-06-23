package com.one.annotation1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
/**
 * 自定义注解, 注解本质是实现了java.lang.annotation.Annotation接口的接口
 * @author one
 */
public @interface Hello {
    /**
     * 注解本质上是一个接口, jdk8之前接口中的成员只能有自定义常量和抽象方法
     * @return 返回String类型
     */
    String value();
}
