package com.one.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 *  把原有的注解作为自定义注解的元注解(修饰注解的注解),注解的功能传递
 *  注解@Conditional: 使用@Conditional注解必须传递一个实现了Condition接口的子类的字节码对象,
 *  而实现conditional接口就必须重写该接口中的matches()方法
 *  当matches方法的返回值为true时,Spring容器将创建Bean对象,如果matches方法返回值为false,Spring容器将不创建Bean对象
 *
 * 注解@ConditionOnclass: 自定义注解的作用,当我们使用该注解时会对注解的value属性进行赋值,
 *          然后我们在ClassCondition的matches()方法中会通过这个value属性值获取其字节码对象,
 *          如果在pom文件中引入了该类的依赖,那么我们就能获取字节码成功不抛出异常,
 *          如果我们在pom文件中没有引入该类的依赖,那么获取字节码对象就失败抛出异常
 * @author one
 */
@Conditional(ClassCondition.class)  // 一个注解修饰另外一个注解,两个注解都会生效,且两个注解中的得属性值能互相取到,注解没有继承功能,只能用元注解的方式
public @interface ConditionOnClass {
    /**
     * 动态赋值
     * 使用value作为注解的成员变量的好处: 赋值时不用写value = (xxx),默认是对value属性进行赋值
     */
    String[] value();

    /**
     * SpringBoot官方提供的几个判断是否自动创建Bean对象的注解:\
     *      1, ConditionOnClass: 在pom文件中导入依赖才自动创建bean对象
     *      2, ConditionOnMissingBean: bean容器中没有该名称的bean对象,才自动创建bean对象
     *      3, ConditionOnProperty: 在配置文件中进行了配置,才创建自动创建bean对象
     *      ......
     */
}
