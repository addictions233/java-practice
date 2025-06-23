package com.corejava.extend.extend3;

/**
 * 子类对象的初始过程：
 * （1）初始化父类中的静态成员变量和静态代码块，按照在程序中出现的顺序初始化；
 * （2）初始化子类中的静态成员变量和静态代码块，按照在程序中出现的顺序初始化；
 * （3）初始化父类的普通成员变量和执行构造代码块，再执行父类的构造方法；
 * （4）初始化子类的普通成员变量和执行构造代码块，再执行子类的构造方法；
 */
public class Daughter extends Father {
    private String name = "Daughter";

    /**
     * 子类初始化时会调用父类的构造方法,
     */
    public Daughter() {
        sayHi();
        sayHello();
    }

    @Override
    public void sayHi() {
        System.out.println("Daughter:" + name);
    }

}
