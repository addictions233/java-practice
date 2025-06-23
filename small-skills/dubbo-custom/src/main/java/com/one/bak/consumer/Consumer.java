package com.one.bak.consumer;

import com.one.bak.framework.ProxyFactory;
import com.one.bak.provider.api.HelloService;

/**
 * @description: consumer消费端
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class Consumer {

    public static void main(String[] args) {
        // 进行rpc远程调用
        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String result = helloService.sayHello("one");
        System.out.println(result);
    }
}
