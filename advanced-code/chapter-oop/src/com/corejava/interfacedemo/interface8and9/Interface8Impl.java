package com.corejava.interfacedemo.interface8and9;

/**
 * @ClassName: Interface8Impl
 * @Description: 为什么jdk8要增加 public default修饰的默认方法和 public static 修饰的静态方法
 *      原因: 假如我们在项目中使用一个接口,并写对该接口的实现类,
 *      但是我们在下一个版本想在接口中添加功能,即新的方法, 一个让该接口所有实现类都能使用的方法
 *      如果我们直接在接口中添加抽象方法,那么该接口的所用实现类都必须重写新添加的抽象方法,这样过于繁琐
 *      如果我们是添加的是jdk8中 公共的默认方法或者静态方法,就可以在其子类中直接使用父接口的
 * @Author: one
 * @Date: 2021/06/19
 */
public class Interface8Impl implements Interface8 {
    /**
     * 接口的实现类必须重写父接口中的抽象放,可以选择性重写父接口中的默认方法
     */
    @Override
    public void show() {
        System.out.println("this is the override method in Class Interface8Impl");
    }

    @Override
    public void say() {
        System.out.println("this is override method");
    }

}
