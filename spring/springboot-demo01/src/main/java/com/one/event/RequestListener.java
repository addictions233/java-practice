package com.one.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 事件监听器: Spring中所有的事件监听器都需要实现ApplicationListener接口
 */
@Component
public class RequestListener implements ApplicationListener<RequestEvent> {

    @Override
    public void onApplicationEvent(RequestEvent event) {
        System.out.println("监听到了RequestEvent事件, 执行了方法...");
    }
}
