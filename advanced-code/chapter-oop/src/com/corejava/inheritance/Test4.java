package com.corejava.inheritance;

import java.time.LocalDate;

public class Test4 {
    public static void main(String[] args) {
        Employee employee = new Employee("zhangsan", 5000, LocalDate.now());
        Manager manager = new Manager("lisi",7999,LocalDate.now());
        Employee employee1 = new Manager("zhangsan", 5000, LocalDate.now());
        System.out.println(employee instanceof Manager); // false
        System.out.println(employee1 instanceof Manager); // true
        System.out.println(manager instanceof Employee); // true
    }

}
