package com.one.configdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableFeignClients //扫描和注册feign客户端bean定义
@EnableScheduling   // 开启定时任务功能
public class MallUserConfigApplication {

    public static void main(String[] args) throws InterruptedException{
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MallUserConfigApplication.class, args);

//        while (true) {
//            //当动态配置刷新时，会更新到 Enviroment中，因此这里每隔3秒中从Enviroment中获取配置
//            String userName = applicationContext.getEnvironment().getProperty("common.name");
//            String userAge = applicationContext.getEnvironment().getProperty("common.age");
//            System.err.println("common name:" + userName + "; age: " + userAge);
//            TimeUnit.SECONDS.sleep(3);
//
//        }

    }

}
