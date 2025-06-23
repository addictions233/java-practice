package com.one.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author one
 * @description 注入静态成员变量:
 *                  1, spring不允许/不支持把值注入到静态变量中
 *                  2, Spring的@Value依赖注入是依赖set方法
 * @date 2022-9-25
 */
@Configuration
public class AddressConfig {
    private static String host;

    private static Integer port;

    @Value("${server.port}")
    public static void setIp(String host) {
        AddressConfig.host = host;
    }

    @Value("${server.port}")
    public static void setPort(Integer port) {
        AddressConfig.port = port;
    }

    public static String getHost() {
        return host;
    }

    public static Integer getPort() {
        return port;
    }
}
