package com.corejava.interfacedemo.inter;

/**
 * @author one
 * JDK中接口中只允许有以下两种成员:
 *      1, 用 public static final 修饰的成员变量(也就是常量)
 *      2, 用 public abstract 修改的成员方法(抽象方法)
 *   接口的目的: 就是为了对其实现类作约束,并使用其中的常量,所以都是用public修饰, 成员变量和成员方法的修饰符可以不写
 */
public interface InterA {
    int num = 8;
}
