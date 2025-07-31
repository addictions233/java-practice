package com.one.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName: UserControllerTest
 * @Description: 测试类
 * @Author: one
 * @Date: 2022/04/07
 */
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private TestController testController;

    @Autowired
    private PersonController personController;

    @Test
    public void findUserTest() {
        String result = userController.findUser();
        System.out.println(result);
    }

    @Test
    public void profileTest() {
        // 看哪个配置文件最终生效: application.properties生效
        String name = testController.getName();
        System.out.println(name);
    }

    @Test
    public void personControllerTest() {
        String result = personController.getPerson();
        System.out.println(result);
    }
}
