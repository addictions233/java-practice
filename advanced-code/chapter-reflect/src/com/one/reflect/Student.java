package com.one.reflect;

public class Student {
    private String name;
    private int age;
    public Student(){

    }
    private Student(String str, int num){
        this.name =str;
        this.age  = num;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
