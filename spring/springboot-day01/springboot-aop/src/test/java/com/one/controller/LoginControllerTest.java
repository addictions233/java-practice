package com.one.controller;


import com.one.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 单元测试
 */
@SpringBootTest
public class LoginControllerTest {
    @Autowired
    private LoginController loginController;

    @Test
    public void checkUser() {
        // 构造入参
        User loginUser = new User("001","张三","root");
        // 测试方法
        loginController.checkUser(loginUser);
    }
}