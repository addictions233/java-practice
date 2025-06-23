package com.one.nacos.controller;

import com.one.nacos.config.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserBeanController
 * @Description: TODO
 * @Author: one
 * @Date: 2022/04/12
 */
@RestController
@RequestMapping("/userBean")
@EnableConfigurationProperties(UserBean.class) // 创建UserBean的bean对象
public class UserBeanController {

    private final UserBean userBean;

    /**
     * 使用构造器注入userBean的属性值
     * @param userBean bean对象
     */
    public UserBeanController(UserBean userBean) {
        this.userBean = userBean;
    }

    @GetMapping("/query")
    public UserBean getUserBean() {
        return userBean;
    }
}
