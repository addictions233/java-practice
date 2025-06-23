package com.one.config;

import com.one.config.AddressConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressConfigTest {

    @Resource
    private AddressConfig addressConfig;


    @Test
    public void addressConfigTest() {
        System.out.println("静态注入属性值:" + addressConfig.getPort());
    }
}