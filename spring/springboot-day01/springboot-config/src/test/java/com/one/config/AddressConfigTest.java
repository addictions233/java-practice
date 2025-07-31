package com.one.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
public class AddressConfigTest {

    @Resource
    private AddressConfig addressConfig;


    @Test
    public void addressConfigTest() {
        System.out.println("静态注入属性值:" + addressConfig.getPort());
    }
}