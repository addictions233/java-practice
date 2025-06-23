package com.one.properties;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @ClassName: PropertiesDemo3
 * @Description: Properties类中提供的用于操作IO的方法  load()方法加载配置文件,  store()生成配置文件
 * @Author: one
 * @Date: 2021/06/16
 */
public class PropertiesDemo3 {
    public static void main(String[] args) throws IOException {
        storeTest();
        Properties properties = new Properties();
        /**
         * 将配置文件中的内容读取到Properties集合中,
         *   load(Reader reader)
         *   load(InputStream inputStream)
         *   读取配置文件也是两个重载方法,一个字节流读取配置文件,一个字符流读取配置文件
         */
        BufferedReader br = new BufferedReader(new FileReader("My_Day13/src/com/itheima/properties/info.properties"));
        properties.load(br);
        System.out.println(properties);
    }

    private static void storeTest() throws IOException {
        Properties properties = new Properties();
        // Properties集合中存储的元素类型必须是String类型,才能进行IO流操作
        properties.setProperty("author","one");
        properties.setProperty("version","1.0");
        properties.setProperty("date","2021-6-16");
        /**
         * 将Properties集合中的内容写入到文件中,
         * store(Writer,writer,String comment)
         * store(Outputstream os,String comment)
         * 两个重载方法,既能按照字节流输出到文件中,也能按照字符流输出到文件中
         */
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day13/src/com/itheima/properties/info.properties", StandardCharsets.UTF_8));
        properties.store(bw,"自定义配置文件");
    }
}
