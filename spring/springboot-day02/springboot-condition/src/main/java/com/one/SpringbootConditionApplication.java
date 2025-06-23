package com.one;

import com.one.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.net.SocketTimeoutException;

/**
 * @author one
 * @ComponentScan: 是为了创建我们使用@Component,@Controller等注解自定义的bean对象到ioc容器中
 * @EnableAutoConfiguration: 在满足bean对象创建的条件下,为我们自动创建spring.factories配置中定义的一百多个bean对象
 */
@SpringBootApplication
public class SpringbootConditionApplication {

    public static void main(String[] args) {
        //启动SpringBoot的应用,返回IOC容器对象context
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootConditionApplication.class, args);

        //获取容器中的Bean对象, redisTemplate
        //当pom文件中没有导入redis依赖是无法获取到redisTemplate对象的
//        Object redisTemplate = context.getBean("redisTemplate"); // 通过名称获取bean对象
//        System.out.println(redisTemplate);

        //获取容器中的Bean对象 User
        User user = context.getBean(User.class); // 根据bean对象的类型进行获取
        System.out.println(user);

//        User user2 = (User)context.getBean("user2"); // 根据bean对象的名称进行获取
//        System.out.println(user2);

//        Object goods = context.getBean("goods");
//        System.out.println(goods);



//        Jedis jedis = context.getBean(Jedis.class);
//        //输出: redis.clients.jedis.Jedis@169da7f2 可以正确获取到jedis的bean对象
//        System.out.println(jedis);

//        jedis.set("username","zhangsan");
//        System.out.println(jedis.get("username"));
    }

    /**
     * 如果我们自己创建了名称为 jedis的bean对象,
     * SpringBoot就不会再自动创建 jedis的bean对象,
     * 因为配置类中使用了@ConditionOnmissBean注解
     * @return redis.clients.jedis.Jedis
     */
    @Bean
    public Jedis jedis(){
        return new Jedis();
    }
}
