package com.corejava.extend.extend3;

/**
 * @ClassName: Apple
 * @Description: 方法中使用成员变量时, 究竟使用的是父类的成员变量还是子类的同名成员变量, 是由调用的方法所属的类决定的,即方法在父类中定义和执行, 则访问的是父类的成员变量
 *               如果方法在子类中定义和执行(包括覆盖的父类方法), 则访问的是子类的成员变量
 * @Author: one
 * @Date: 2022/05/24
 */
public class Apple extends Fruit {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
