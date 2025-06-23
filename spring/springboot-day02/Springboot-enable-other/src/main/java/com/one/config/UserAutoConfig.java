package com.one.config;

import com.one.domain.Role;
import com.one.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: UserConfig
 * @Description: 在第三方jar包创建bean, 思考如何让Springboot-enable模块中获取本配置类创建的Bean对象?
 *                    注解@Configuration的作用:
 *                         用于定义配置类, 可替换xml配置文件, 被注解的类内部包含有一个或者多个被@Bean注解修饰的方法,
 *                         这些方法将会被AnnotationConfigApplicationContext或者AnnotationConfigWebApplicationContext类
 *                         进行扫描,并用于构建bean对象,初始化IOC容器
 * @Author: one
 * @Date: 2021/03/28
 */
@Configuration
public class UserAutoConfig {
    @Bean
    public User user(){
        return new User();
    }

    @Bean
    public Role role(){
        return new Role();
    }
}
