package com.one.controller;

import com.one.config.AddressConfig;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: UserController
 * @Description: 测试 application.yml, application.properties, application.yaml的执行顺序
 * @Author: one
 * @Date: 2020/12/14
 */
@RestController
public class TestController {
    @Value("${name}")
    private String name;


    @RequestMapping("/user")
    public String getName() {
        System.out.println("user controller is running ....");
        return "hello spring boot!" + name;
    }

    @RequestMapping("/address")
    public void testAddress() {
        System.out.println(AddressConfig.getHost());
        System.out.println(AddressConfig.getPort());
    }
}
