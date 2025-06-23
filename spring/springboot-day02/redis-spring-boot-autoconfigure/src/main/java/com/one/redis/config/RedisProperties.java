package com.one.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: RedisProperties
 * @Description: 创建jedis的bean对象需要指定ip和端口号,可以从application.yml文件中读取
 * @Author: one
 * @Date: 2021/04/06
 */
@ConfigurationProperties(prefix = "redis")
//单独使用@ConfigurationProperties只会读取配置文件并不会创建bean对象,
// 要么结合@EnableConfigurationProperties创建Bean对象,要么结合@Component使用创建bean对象
//@PropertySource("xxx") //用来指定资源文件的路径,如果是在application.yml这个默认配置文件中就不用使用本注解
public class RedisProperties {
    /**
     * Redis主机默认值, 如果在application.yml中进行了配置,就使用配置文件中的值
     */
    private String host = "localhost";
    /**
     * Redis端口默认值, 如果在application.yml中进行了配置,就使用配置文件中的值
     */
    private int port = 6379;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
