package com.one.propertysource;

import org.springframework.core.env.PropertySource;

public class PropertySourceWrapper<T> extends PropertySource<T> {

    private  PropertySource<T> propertySource;

    public PropertySourceWrapper(PropertySource<T> propertySource) {
        // 子类的构造方法中必须调用父类的构造方法
        super("propertySourceWrapper");
        this.propertySource = propertySource;
    }

    @Override
    public Object getProperty(String name) {
        return propertySource.getProperty(name);
    }
}
