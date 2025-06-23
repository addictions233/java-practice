package com.corejava.interfacedemo.inter1;

/**
 * @author one
 *  JDK7 中 成员变量默认用public static final修饰的常量, 成员方法默认用public abstract修饰的抽象方法
 *  接口并不能直接创建对象,接口更多的是为它的实现类创建一种规范或者说一种约束, 接口的实现类对象可以使用接口中
 *  所有的成员变量和成员方法, 但同时受到接口的规范约束,必须重写接口中的抽象方法
 */
public interface Inter7 {
    public static final int num = 100;
    public abstract void show();
    public abstract int function();
}
