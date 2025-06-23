package com.one.jmm;

/**
 * @author  Fox
 * hsdis-amd64.dll
 * 查看汇编指令
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp
 *  DCL为什么要使用volatile
 */
public class SingletonFactory {

    /**
     * 单例对象需要使用volatile修饰
     */
    private static SingletonFactory myInstance;
//    private static volatile SingletonFactory myInstance;

    public static SingletonFactory getMyInstance() {
        // 第二个线程进来 判断myInstance对象不是null, 但是myInstance对象没有初始化
        if (myInstance == null) {
            synchronized (SingletonFactory.class) {
                if (myInstance == null) {
                    // 第一个进程进来初始化单例对象
                    // 1. 开辟一片内存空间
                    // 3. myInstance指向内存空间的地址
                    // 2. 对象初始化
                    myInstance = new SingletonFactory();
                }
            }
        }
        // 可能返回一个没有初始化的对象
        return myInstance;
    }

    public static void main(String[] args) {
        SingletonFactory.getMyInstance();
    }
}
