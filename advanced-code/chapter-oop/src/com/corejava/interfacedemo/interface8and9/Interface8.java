package com.corejava.interfacedemo.interface8and9;

/**
 * @InterfaceName: interface8
 * @Description: jdk8和jdk9中的接口
 *      jdk7,jdk8,jdk9中的接口中的成员对比:
 *          jdk7: 接口中只允许有 public static final修饰的常量和 public abstract修饰的抽象方法
 *          jdk8: 接口中添加了 public default修饰的默认方法和public static 修饰的静态方法
 *          jdk9: 接口中添加了 private 修饰的私有成员方法和私有静态方法
 * @Author: one
 * @Date: 2021/06/19
 */
public interface Interface8 {

    /**
     * jdk7中: 接口中只允许有public abstract修饰的抽象方法, 接口中的抽象方法必须被其实现类重写
     */
    public abstract void show();

    /**
     * jdk8新特性: 接口中允许有 public default修饰的默认方法, 默认方法可以被其实现类选择性的重写
     */
    public default void say() {
        System.out.println("this is public default method in interface8");
    }

    /**
     * jdk8新特性: 接口中允许有用 public static 修饰的静态方法, 根据多态原则, 静态方法不能被其实现类重写
     * @return int
     */
    public static int count() {
        System.out.println("this is public static method in interface8");
        return 10;
    }
}
