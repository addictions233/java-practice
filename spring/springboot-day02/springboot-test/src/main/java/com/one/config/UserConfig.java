package com.one.config;

import com.one.condition.ConditionOnClass;
import com.one.domain.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName: UserConfig
 * @Description: 配置类用于获取User对象
 *                      注解@Conditon*的作用: 用于条件判断是否创建某些bean,只有在满足了@Condition*注解中定义的条件后,才会创建bean对象
 * @Author: one
 * @Date: 2020/12/16
 */
@Configuration     //表明该类为一个配置类
public class UserConfig {
    /**
     * 通过实例工厂模式 + @Configuration注解 + @Bean注解来创建User的Bean对象
     * 注解@Conditional: SpringBoot框架提供的条件注解,该注解的作用:根据条件判断是否创建Bean对象
     * 注解@ConditionOnclass: 自定义的注解,判读该注解的value属性值对应的类是否在pom文件中引入了依赖,
     * 如果引入了依赖就会创建该类的Bean对象,放在ioc容器,如果没有引入依赖,就不创建bean对象
     * @return User对象
     */
    @Primary // 多个同类型的bean对象,优先使用@Primary注解修饰的
    @Bean("user") //不指定bean对象的名称,默认使用方法名称作为bean对象的名称
//    @Conditional(ClassCondition.class)  //根据是否存在依赖来判断是否创建Bean对象
    @ConditionOnClass("redis.clients.jedis.Jedis")  // 直接使用@Conditional注解存在硬编码问题,需要嵌套注解
    public User getUser(){
        return new User("张三",23);
    }

    /**
     * 使用SpringBoot提供的注解@CondtitionOnProperty来判断配置文件中有相关配置,才自动创建bean对象
     * @return User
     */
    @Bean("user2")
    @ConditionalOnProperty(name = "name", havingValue = "one")
    public User getUser2() {
        return new User("李四",24);
    }

}

