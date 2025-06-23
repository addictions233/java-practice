package com.one.annotation3;

import java.lang.annotation.*;

/**
 * 注解中为什么要用方法来定义属性?
 * @author one
 */
//自定义注解
@Target(ElementType.TYPE)   //该注解作用于类上
@Retention(RetentionPolicy.RUNTIME) //该注解生命周期到运行期
@Documented
public @interface HelloAnnotation {
    /**
     * 属性
     * @return 字符串类型
     */
    String say() default "Hi";
}
