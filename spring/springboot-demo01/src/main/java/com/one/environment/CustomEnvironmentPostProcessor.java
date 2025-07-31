package com.one.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 环境变量的后置处理器,可以对环境变量进行一些修改
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String value = environment.getProperty("env.key");
        System.out.println(value);
        environment.getSystemEnvironment().putIfAbsent("key","value");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
