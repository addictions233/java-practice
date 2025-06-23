package com.one.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: UserBeanConfig
 * @Description: 读取nacos配置中心的配置,并创建UserBean对象
 * @Author: one
 * @Date: 2022/04/12
 */
@ConfigurationProperties(prefix = "who")
@Data
@RefreshScope // 实时动态获取nacos配置中心的最新值
public class UserBean {
    private String name;

    private Integer age;
}
