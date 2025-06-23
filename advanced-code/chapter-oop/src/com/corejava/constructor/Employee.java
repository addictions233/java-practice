package com.corejava.constructor;

import java.util.Random;

public class Employee {
    // 成员变量
    private static int nextId;
    private int id;
    private String name = ""; //直接对成员变量进行初始化
    private double salary;

    //静态代码块
    static {
        Random random = new Random();
        nextId = random.nextInt(10000);
    }

    //构造代码块
    {
        id = nextId;
        nextId++;
    }
    //无参构造
    public Employee(){

    }

    // 有参构造方法
    public Employee(String name, double salary){
        this.name = name;
        this.salary = salary;
    }

    public static int getNextId() {
        return nextId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}
