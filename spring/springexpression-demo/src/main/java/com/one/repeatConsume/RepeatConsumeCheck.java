package com.one.repeatConsume;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mq消息重复消费的拦截
 *
 * @author wanjunjie
 * @date 2023/6/1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatConsumeCheck {
	/**
	 * 业务类型, 不支持EL表达式
	 */
	String businessType();

	/**
	 * 业务code, 支持EL表达式
	 */
	String businessUniqueCode();

	/**
	 * 当条件满足的时候，才会进行重复消费校验，默认满足。
	 * <li>EL表达式</li>
	 */
	String condition() default "";
}
