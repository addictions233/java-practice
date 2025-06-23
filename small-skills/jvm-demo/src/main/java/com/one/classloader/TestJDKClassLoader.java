package com.one.classloader;

import java.sql.BatchUpdateException;

/**
 * @author one
 * @description 类从字节码文件到内存中的过程称为类加载过程, 主要是由类加载器实现的
 *         引导类加载器: 负责加载支撑JVM运行的位于JRE的lib目录下的核心类库, 比如rt.jar, charset.jar等
 *         扩展类加载器: 负载加载支持JVM运行的位于JRE的lib目录下的ext扩展目录中的JAR类包
 *         应用程序类加载器: 负责加载ClassPath路径下的类包, 主要是加载你自己写的那些类
 *         自定义加载器: 负载加载用户自定义路径下的类包
 * @date 2024-11-11
 */
public class TestJDKClassLoader {

    public static void main(String[] args) {
        // 获取某个类的类加载器名称
        System.out.println(String.class.getClassLoader());
        System.out.println(BatchUpdateException.class.getClassLoader().getClass().getName());
        System.out.println(TestJDKClassLoader.class.getClassLoader().getClass().getName());
        System.out.println("---------------------------------------------------------------");

        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("appClassLoader:" + appClassLoader);
        ClassLoader platformClassLoader = appClassLoader.getParent();
        System.out.println("platformClassLoader:" + platformClassLoader);
        // 我们获取不到引导类加载器，返回是个null。那是因为引导类加载器（BootstrapClassLoader）是用C和C++语言来实现的，所以这里是获取不到它的对象的
        ClassLoader bootstrapClassLoader = platformClassLoader.getParent();
        System.out.println("bootstrapClassLoader:" + bootstrapClassLoader);
        System.out.println("---------------------------------------------");



    }
}
