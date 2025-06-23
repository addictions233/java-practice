package com.corejava.abstractclass;

/**
 *  定义一个抽象的 Person类
 */
public abstract class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //抽象方法
    public abstract String getDescription();
}
