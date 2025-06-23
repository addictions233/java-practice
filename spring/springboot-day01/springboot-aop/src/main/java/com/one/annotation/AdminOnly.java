package com.one.annotation;

import javax.xml.stream.events.EndElement;
import java.lang.annotation.*;

/**
 * @author one
 *   自定义注解作为AOP的切点表达式
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminOnly {
    String program();
}
