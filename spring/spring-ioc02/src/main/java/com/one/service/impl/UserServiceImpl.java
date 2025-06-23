package com.one.service.impl;

import com.one.dao.UserDao;
import com.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

// 注解 Service()中的参数值,指定获取该注解Bean对象的id值(即xml中的id值)
@Service("userService")
@Scope("singleton")
public class UserServiceImpl implements UserService {
    // value对基本数据类型(包括String类型)的属性进行注入赋值
    @Value("测试用的service")
    private String desc;

    @Value("version1.0")
    private String version;

    //用@AutoWired注解对引用数据类型属性进行注入赋值, 先采用类型匹配,再采用对象名匹配
    @Autowired
    private UserDao userDao2;

    public void save() {
        System.out.println("user service running..."+
                "描述:"+desc+",版本号:"+version);

        //用成员变量userDao对象调用其方法
        userDao2.save();
    }

//    // 生命周期中的初始化方法
//    @PostConstruct
//    public void init(){
//        System.out.println("user service init...");
//    }
//
//    // 生命周期中的销毁方法  单例模式下调用close()方法才能销毁Bean对象
//    @PreDestroy
//    public void destroy(){
//        System.out.println("user service destroy...");
//    }

}
