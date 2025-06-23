package com.one.controller;

import com.one.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: 读取application.yml文件中的内容进行DI注入
 *  SpringBoot读取配置文件的三种方式:
 *          1,使用@Vlaue注解读取普通数据类型
 *          2,使用@ConfigurationProperties读取复杂数据类型
 *          3,使用Environment对象读取全部配置
 * @Author: one
 * @Date: 2020/12/15
 */
@RestController
public class UserController {

    /**
     * 方式一: 使用@Value注解用于单个数据注入
     */
    @Value("${user.username}")
    private String username;

    /**
     * 方式二: 使用environment对象用于批量注入 evn对象中有配置文件中所有的属性值
     */
    @Autowired
    private Environment environment;

    /**
     * 方式三: 使用@ConfigurationProperties注解, 将配置文件中的属性值用于指定对象注入
     */
    @Autowired
    private User user;

    @RequestMapping("/findUser")
    public String findUser() {
        System.out.println("方式1取值: username:" + username);
        System.out.println("方式2取值: env.getProperty(user.username):" + environment.getProperty("user.username"));
        System.out.println("方式3取值: user:" + user.toString());
        return "hello springboot!!";
    }

}
