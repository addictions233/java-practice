package com.corejava.tongpeifu;

import java.time.LocalDate;

/**
 *  定义一个雇员类 Employee
 */
public class Employee {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee() {
    }

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year,month,day);
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
        return hireDay;
    }

    public void setBirthday(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    /**
     *  定义一个成员方法: 用来提升工资salary的方法
     */
    public void raiseSalary(double byPercent){
        double raise = salary*byPercent/100;
        salary = salary + raise;
    }

}
