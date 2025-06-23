package com.one.service.impl;


import com.one.api.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceImplTest {

    @DubboReference(protocol = "rest")
    private HelloService helloService;

    @Test
    void sayHello() {
        String hello = helloService.sayHello("one");
        System.out.println(hello);
    }

    @Test
    void sayHelloServerStream() {
    }

    @Test
    void sayHelloStream() {
    }

    @Test
    void createOrder() {
    }
}