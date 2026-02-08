package com.corejava.anonymousinnerclass;

public class Person {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void eat() {
        System.out.println(name + "在吃饭!");
    }
}
