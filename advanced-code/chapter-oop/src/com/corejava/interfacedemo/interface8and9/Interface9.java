package com.corejava.interfacedemo.interface8and9;

/**
 * @InterfaceName: Interface9
 * @Description: JDK9的接口中: 允许有私用的成员方法和静态方法
 *      为什么要引入私用的成员方法和静态方法? 由于接口是不能创建对象的,所以理论上接口中的所有成员必须是public修饰的,
 *      让其实现类可以使用,在接口中定义private修饰的私有成员是没有意义的,但是jdk8增加了public修饰的默认方法和静态方法
 *      jdk9定义的私有的成员方法和静态方法是为了jdk8中公共的默认方法和静态法方法服务的
 * @Author: one
 * @Date: 2021/06/19
 */
public interface Interface9 {

    /**
     * jdk7中: 接口中只允许有public abstract修饰的抽象方法
     */
    public abstract void show();

    /**
     * jdk8接口中的默认方法
     */
    public default void say() {
        System.out.println("this is public default method in Class Interface9");
        // 默认方法可以调用私有的成员方法
        System.out.println("count:" + getNumber(10));
    }

    /**
     * jdk8接口中的公共静态方法
     *   静态方法中只能调用静态成员,不能调用非静态成员
     */
    public static void hello() {
        System.out.println("this is public static method in Class Interface9");
        eat();
    }

    /**
     * jdk9中提供接口中的私用成员方法
     * @param count count
     * @return int
     */
    private int getNumber(int count) {
        return ++count;
    }

    /**
     * jdk9中接口中的私有的静态方法
     */
    private static void eat() {
        System.out.println("this is private static method in Class Interface9");
    }
}
