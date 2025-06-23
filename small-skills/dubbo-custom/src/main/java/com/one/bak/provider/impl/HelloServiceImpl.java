package com.one.bak.provider.impl;


import com.one.bak.provider.api.HelloService;

/**
 * @description: provider提供的接口实现
 * @author: wanjunjie
 * @date: 2024/10/30
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String userName) {
        return "hello:" + userName;
    }
}
