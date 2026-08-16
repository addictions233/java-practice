package com.one.enumdemo;

public class Main {

    public static void main(String[] args) {
        Class<? super Season> superclass = Season.class.getSuperclass();
        // class java.lang.Enum
        // 所有的枚举类都是 Enum 这个抽象类的子类实现
        System.out.println(superclass);
    }
}
