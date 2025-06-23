package com.one.properties;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * @author one
 * Properties类的特点:
 *          1, Properties继承自父类 HashTable<Object,Object>,所以是双列集合,但Properties泛型固定, 在使用Properties集合时,只存储字符串类型数据
 *          2, Properteis类中提供类IO流相关的方法: load() 和 store()
 *          3,
 */
public class PropertiesDemo1 {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        // 作为map集合的添加元素
        prop.put("java","高级编程语言");
        prop.put("C++","高级编程语言");
        prop.put("c","底层编程语言");
        prop.put("汇编","更底层编程语言");
        System.out.println(prop);
        // 作为map集合的删除元素
        prop.remove("java");
        // 作为map集合修改元素
        prop.put("汇编","不想学习的编程语言");
        System.out.println(prop);
        // 作为map集合遍历
        Set<Object> objects = prop.keySet();
        for (Object object : objects) {
            System.out.println(prop.get(object));
        }
        prop.store(new FileWriter("My_Day13\\语言.properties"),"编程语言描述");
    }
}
