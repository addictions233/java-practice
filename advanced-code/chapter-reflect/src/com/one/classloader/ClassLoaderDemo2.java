package com.one.classloader;

/**
 * @ClassName: ClassLoaderDemo2
 * @Description: 类加载器分类: BootstrapClassLoader, PlatformClassLoader, SystemClassLoader UserClassLoader(自定义类加载器)
 *               双亲委派模型: 处理低层级的类加载器会将加载任务提交给它的上一级类加载器,直到加载任务到达BootstrapClassLoader启动类加载器
 *               当启动类加载器无法完成加载任务时又会逐级将加载任务传递给它的下一级
 * @Author: one
 * @Date: 2021/06/17
 */
public class ClassLoaderDemo2 {
    public static void main(String[] args) {
        // 这些类加载器都是ClassLoader的内部类
        // 获得系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统类加载器:" + systemClassLoader);
        // 获取系统类加载器的父类: 平台类加载器
        ClassLoader platformClassLoader = systemClassLoader.getParent();
        System.out.println("平台类加载器:" + platformClassLoader);
        // 获取平台类加载器的父类: 启动类加载器
        ClassLoader bootstrapClassLoader = platformClassLoader.getParent();
        System.out.println("启动类加载器:" + bootstrapClassLoader);
    }
}
