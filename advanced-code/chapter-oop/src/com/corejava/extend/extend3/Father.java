package com.corejava.extend.extend3;

public class Father {
    private String name = "father";

    /**
     * 反面例子: 不要在构造器中调用可能被子类重写的方法
     * 子类初始化对象时会调用父类的构造方法,即会调用sayHi()和sayHello()方法, 但是子类又重写了sayHi()方法, 根据多态特点,
     * 在父类和子类构造器调用的sayHi()方法都是调用子类Daughter中的sayHi()方法, 但是在父类调用sayHi()方法时,子类的name属性是没有初始化的,所以是null
     * 而sayHello()方法没有重写的,则调用的是父类Father中的sayHello()方法
     */
    public Father() {
        sayHi(); // 子类重写sayHi()方法
        sayHello();
    }

    public void sayHi() {
        System.out.println("father:" + name);
    }

    public void sayHello() {
        System.out.println("father:" + name);
    }
}