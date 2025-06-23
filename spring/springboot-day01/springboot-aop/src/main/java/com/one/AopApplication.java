package com.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @ClassName: AOPApplication
 * @Description: 启动类
 * @Author: one
 * @Date: 2021/04/03
 */
@SpringBootApplication
@ServletComponentScan("com.one") // 使用过滤器Filter必须在启动类上加上servlet的包扫描
public class AopApplication {
    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }
}
