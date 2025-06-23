package com.one.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: ConnectionUtil
 * @Description: 获取rabbitmq的连接工厂的工具类
 * @Author: one
 * @Date: 2021/04/04
 */
public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置主机
        connectionFactory.setHost("192.168.31.114");
        // 设置端口
        connectionFactory.setPort(5672);
        // 设置虚拟主机
        connectionFactory.setVirtualHost("my_vhost");
        // 设置用户名
        connectionFactory.setUsername("admin");
        // 设置密码
        connectionFactory.setPassword("admin");

        return connectionFactory.newConnection();
    }
}

