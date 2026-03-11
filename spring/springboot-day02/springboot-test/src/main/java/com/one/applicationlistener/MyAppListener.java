package com.one.applicationlistener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyAppListener implements ApplicationListener<ApplicationReadyEvent> {
    
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 应用完全启动后执行
        System.out.println("✅ 应用已就绪，执行初始化任务...");
        // 如：加载字典缓存、预热连接池、注册服务到注册中心等
    }
}