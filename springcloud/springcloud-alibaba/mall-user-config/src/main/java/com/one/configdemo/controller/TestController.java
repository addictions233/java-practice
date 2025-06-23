package com.one.configdemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //动态感知修改后的值, 如果配置中心的值修改后, 这个配置bean会先destroy
public class TestController implements ApplicationListener<RefreshScopeRefreshedEvent> {

    @Value("${common.age}")
     String age;
    @Value("${common.name}")
     String name;

    @GetMapping("/common")
    public String hello() {
        return name+","+age;
    }

    //触发@RefreshScope执行逻辑会导致@Scheduled定时任务失效
    @Scheduled(cron = "*/3 * * * * ?")  //定时任务每隔3s执行一次
    public void execute() {
        System.out.println("定时任务正常执行。。。。。。");
    }


    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        this.execute();
    }
}