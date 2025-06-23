package com.one.properties;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @ClassName: PropertiesDemo2
 * @Description: Properties作为双列集合一些特有的方法
 * @Author: one
 * @Date: 2021/06/16
 */
public class PropertiesDemo2 {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // setProperty()方法等同于map集合中的put()方法
        properties.setProperty("C语言","面向过程编程语言");
        properties.setProperty("Java","面向对象编程语言");
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        // getProperty()方法等同于map集合中的get()方法,通过key查找value
        String c语言 = properties.getProperty("C语言");
        System.out.println(c语言);
        // stringPropertyNames()方法获取所有的不可修改的键集
        Set<String> strings = properties.stringPropertyNames();
        System.out.println(strings);
    }
}
