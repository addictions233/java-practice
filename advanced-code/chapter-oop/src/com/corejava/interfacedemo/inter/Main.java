package com.corejava.interfacedemo.inter;

/**
 * @ClassName: Main
 * @Description: 测试类
 * @Author: one
 * @Date: 2021/06/19
 */
public class Main {
    public static void main(String[] args) {
        InterImpl interImpl = new InterImpl();
        // 子类可以直接调用父类中的非私有成员, 不能直接调用父类中的私有成员
        // 可以在子类中创建父类对象,然后调用父类的私用成员
        interImpl.say();
    }
}
