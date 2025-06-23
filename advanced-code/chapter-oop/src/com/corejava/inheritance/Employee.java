package com.corejava.inheritance;

import java.time.LocalDate;

/**
 *  定义一个雇员类 Employee
 */
public class Employee {
    private String name;
    private double salary;
    private LocalDate birthday;

    public Employee(String name, double salary, LocalDate birthday) {
        this.name = name;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     *  定义一个成员方法 提升工资的方法
     */
    public void raiseSalary(double byPercent){
        double raise = salary*byPercent/100;
        salary = salary + raise;
    }
}
