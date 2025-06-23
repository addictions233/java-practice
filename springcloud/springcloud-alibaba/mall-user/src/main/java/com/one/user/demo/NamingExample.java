package com.one.user.demo;

import java.util.Properties;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;


public class NamingExample {

    public static void main(String[] args) throws NacosException {

        Properties properties = new Properties();
        properties.setProperty("serverAddr", "127.0.0.1:8848");
        //核心接口
        NamingService naming = NamingFactory.createNamingService(properties);
        //服务注册
        naming.registerInstance("mall-order", "11.11.11.11", 8888, "TEST1");
        //服务发现
        System.out.println(naming.getAllInstances("mall-order"));


    }
}