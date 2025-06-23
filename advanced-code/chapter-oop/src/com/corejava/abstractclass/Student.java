package com.corejava.abstractclass;

/**
 *  用 Student类继承 Person抽象类
 */
public class Student extends Person {
    private String major;

    public Student(String name, String major) {
        super(name);
        this.major = major;
    }

    @Override
    public String getDescription() {
        return "a student majoring in"+major;
    }
}
