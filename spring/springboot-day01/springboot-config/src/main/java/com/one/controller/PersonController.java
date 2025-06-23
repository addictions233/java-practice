package com.one.controller;

import com.one.config.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: PersonController
 * @Description: 需要使用@EnableConfigurationProperties注解来引入配置类 Person
 * @Author: one
 * @Date: 2021/04/06
 */
@RestController
@RequestMapping
@EnableConfigurationProperties(Person.class)
public class PersonController {
    /**
     * 这里使用field反射注入bean对象, 共有三种注入方式:
     *      1,构造器注入
     *      2,setter方式注入
     *      3,field反射注入
     */
    @Autowired
    @Qualifier("person-com.one.config.Person")  // 这个bean对象的名称就奇葩
    private Person person;

    @GetMapping("/findPerson")
    public String getPerson() {
        System.out.println(person);
        return "访问成功!";
    }
}
