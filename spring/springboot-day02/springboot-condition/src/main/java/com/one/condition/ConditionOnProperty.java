package com.one.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author one
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(PropertyCondition.class) //注解功能传递, PropertyCondition是条件判断类
public @interface ConditionOnProperty {
    String value();
}
