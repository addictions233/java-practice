package com.one.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: RedisAutoConfiguration
 * @Description: 需求: 自定义redis-starter, 要求当导入redis坐标时,SpringBoot会自动创建Jedis的bean对象
 * 步骤: 1, 创建redis-spring-boot-autoconfigure模块
 *       2, 创建redis-spring-boot-starter模块并引入redis-spring-boot-autoconfigure的模块依赖
 *       3, 在redis-spring-boot-autoconfigure模块中初始化Jedis的bean,并将配置类的名称定义spring.factories文件中
 *       4,在测试模块中引入自定义的redis-spring-boot-starter依赖,测试模块会扫描spring.factories文件并创建bean对象
 * @Author: one
 * @Date: 2021/04/06
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class) // 配合@ConfigurationProperties使用,读取配置文件并封装成bean对象
@ConditionalOnClass(Jedis.class)  //条件注解:只用Jedis的依赖导入时才会创建本类的bean对象
public class RedisAutoConfiguration {
    /**
     * 这里使用 @Configuration + @Bean注解的方法创建Jedis的bean对象, 但是jedis的host和port需要根据用户在配置文件中的内容指定
     * 所用加入了 RedisProperties类来读取配置文件中的host和port,然后在本类中使用,一定要以方法形参的方式注入,而不能以成员变量的
     * 方式注入,成员变量的方式注入可能没有初始化
     * @param redisProperties 读取配置文件生产的bean对象
     * @return Jedis
     */
    @Bean
    @ConditionalOnMissingBean(name = "jedis")  // 当ioc容器中没有名称为jedis的bean对象才会执行本方法创建名称为jedis的bean对象
    public Jedis jedis(@Autowired RedisProperties redisProperties){
        System.out.println("创建jedis的bean对象的方法执行了");
        return  new Jedis(redisProperties.getHost(),redisProperties.getPort());
    }
}
