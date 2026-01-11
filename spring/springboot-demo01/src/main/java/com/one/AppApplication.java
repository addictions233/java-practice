package com.one;

import com.one.event.Request;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author one
 * @description 启动类
 * @date 2025-4-11
 */
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args);
        Request request = (Request) context.getBean("request");
        request.doRequest();
    }
}
