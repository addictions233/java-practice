package com.one.usersentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MallUserSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserSentinelApplication.class, args);
    }

}
