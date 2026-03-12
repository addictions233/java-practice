package com.one.propertysource;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 可以在运行时动态添加/修改PropertySource
 */
@Component
public class DynamicPropertySource implements EnvironmentAware {
    
    @Override
    public void setEnvironment(Environment environment) {
        ConfigurableEnvironment configurableEnv = (ConfigurableEnvironment) environment;
        
        // 创建自定义属性源
        Map<String, Object> myProps = new HashMap<>();
        myProps.put("custom.key", "dynamic-value");
        myProps.put("custom.timestamp", System.currentTimeMillis());
        
        PropertySource<?> customSource = new MapPropertySource("dynamicSource", myProps);
        
        // 添加到环境（可以指定优先级位置）
        configurableEnv.getPropertySources().addFirst(customSource);  // 最高优先级
        // 或 addLast(), addBefore(), addAfter()
    }
}