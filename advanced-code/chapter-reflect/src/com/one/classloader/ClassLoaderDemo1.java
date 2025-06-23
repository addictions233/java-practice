package com.one.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author one
 * 使用类加载器进行获取文件资源  ClassLoader.getSystemResourceAsStream()   classLoader.getResourceAsStream()
 */
public class ClassLoaderDemo1 {
    public static void main(String[] args) throws IOException {
//        method1();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        // 使用成员方法: getResourceAsStream(InputStream is)来加载资源
        InputStream is = classLoader.getResourceAsStream("prop.properties");
        //创建Properties集合对象,
        Properties prop = new Properties();
        prop.load(is);
        System.out.println("prop = " + prop);
    }

    private static void method1() throws IOException {
        // 使用类加载器加载资源文件, 调用的是静态方法: getSystemResourceAsStream(InputStream is)
        InputStream is = ClassLoader.getSystemResourceAsStream("prop.properties");
        //创建Properties集合对象
        Properties prop = new Properties();
        prop.load(is);
        System.out.println("prop = " + prop);

        //关流
        is.close();
    }
}
