package com.com.springbootmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // 开启Servlet注解扫描
public class SpringbootMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMvcApplication.class, args);
    }

}
