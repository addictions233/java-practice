package com.corejava.interfacedemo.interface8and9;

/**
 * @ClassName: Main
 * @Description: 测试类
 * @Author: one
 * @Date: 2021/06/19
 */
public class Main {
    public static void main(String[] args) {
        Interface8Impl interface8 = new Interface8Impl();
        /**
         * 接口中的public default修饰的默认方法可以被其实现类使用,
         * 而抽象方法必须被其实现类重写之后才能使用
         */
        interface8.show();
        interface8.say();

        // 接口中的静态方法: 直接用接口名调用
        int count = Interface8.count();

        Interface9 interface9 = new Interface9() {
            @Override
            public void show() {

            }
        };
        // 调用接口中的公共的默认方法
        interface9.say();
        // 调用接口中的公共的静态方法
        Interface9.hello();
    }
}
