package com.one.propertysource;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class EncryptedPropertySource implements BeanFactoryPostProcessor {
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        ConfigurableEnvironment env = beanFactory.getBean(ConfigurableEnvironment.class);
        
        // 包装现有的PropertySource，添加解密逻辑
        env.getPropertySources().stream()
            .filter(ps -> ps.getName().contains("applicationConfig"))
            .findFirst()
            .ifPresent(original -> {
                PropertySource<?> encrypted = new PropertySourceWrapper(original) {
                    @Override
                    public Object getProperty(String name) {
                        Object value = super.getProperty(name);
                        if (name.endsWith(".encrypted") && value != null) {
                            return "xxx";  // 解密逻辑
                        }
                        return value;
                    }
                };
                env.getPropertySources().replace(original.getName(), encrypted);
            });
    }
}