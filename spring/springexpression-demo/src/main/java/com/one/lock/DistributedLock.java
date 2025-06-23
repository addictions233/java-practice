package com.one.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author one
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DistributedLock {

    /**
     * 前缀:不支持EL表达式
     */
    String prefix();

    /**
     * 等待超时时间
     */
    long waitTime() default 30;

    /**
     * 分布式锁的key,支持EL表达式
     */
    String key();

    /**
     * 当条件满足的时候，才会加锁，默认满足。
     * <li>EL表达式</li>
     */
    String condition() default "";

    /**
     * 时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 默认抛出运行异常
     * 1.beanName#methodName
     * 2.methodName
     */
    String tryFailed() default "";
}
