package com.one.interfaceidempotence.annotation;

import java.lang.annotation.*;

/**
 * @author one
 * 自定义注解,只有携带该接口的处理器接口才会进行幂等性接口判断
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApildempotentAnn {
    /**
     *  注解中的属性就是元数据,可以在反射阶段通过对象调用方法的额方式获取该数据
     */
    boolean value() default true;
}
