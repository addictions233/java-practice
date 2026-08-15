package com.corejava.genenricinterface;

public class Main {

    public static void main(String[] args) {
        // 通过子类实例化泛型对象

        Info<String> str1 = new StrInfoImpl("aaa");
        System.out.println(str1.getVar());

        // 假如没有泛型, 我们要兼容下面的几种情况, 又要创建 StrInfoImpl类, 又要创建 InterInfoImpl类
        // 但是有了泛型之后, 我们直接使用 InfoImpl<T> 这一个类替代多个类的功能

        // 直接实例化泛型对象
        Info<String> str2 = new InfoImpl<>("bbb");
        System.out.println(str2.getVar());

        Info<Integer> int1 = new InfoImpl<>(10);
        System.out.println(int1.getVar());
    }
}
