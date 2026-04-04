package com.one.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 元注解: 修饰注解的注解, jdk提供了几个元注解
 * @Target：描述注解能够作用的位置
 *     ElementType取值：
 * 	   TYPE：可以作用于类上
 * 	   METHOD：可以作用于方法上
 * 	   FIELD：可以作用于成员变量上
 * @Retention：描述注解被保留的阶段
 * @Retention(RetentionPolicy.RUNTIME)：当前被描述的注解，会保留到class字节码文件中，并被JVM读取到
 * @Documented：描述注解是否被抽取到api文档中
 * @Inherited：描述注解是否被子类继承
 * @Deprecated：表示该方法有缺陷，不建议使用
 *
 * @author one
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Anno1 {
    /**
     * 注解中可以有属性, 但是是以方法的方式定义属性
     * 1. 如果定义属性时，使用default关键字给属性默认初始化值，则使用注解时，可以不进行属性的赋值。
     * 2. 如果只有一个属性需要赋值，并且属性的名称是value，则value可以省略，直接定义值即可。
     * 3. 数组赋值时，值使用{}包裹。如果数组中只有一个值，则{}可以省略
     * @return 属性值是int类型
     */
    public abstract int num() default 10;

    /**
     *
     * @return 属性值是Class类型
     */
    public abstract Class clazz() default MyAnno1.class;
}
