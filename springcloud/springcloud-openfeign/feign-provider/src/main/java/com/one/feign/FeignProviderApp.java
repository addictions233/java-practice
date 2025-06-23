package com.one.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: FeignProviderApp
 * @Description: Feign的被调用方
 * @Author: one
 * @Date: 2021/01/18
 */
@SpringBootApplication
public class FeignProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(FeignProviderApp.class,args);
    }
}
