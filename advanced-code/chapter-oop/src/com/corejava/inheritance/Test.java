package com.corejava.inheritance;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        LocalDate ld = LocalDate.now();
        System.out.println(ld);
        Manager manager = new Manager("mike", 9000.0, ld, 500.0);
        System.out.println(manager.getSalary());  //输出: 9500.0;
        System.out.println("----------------");
        Manager boss = new Manager("jack",19000.0,ld);
        boss.setBonus(1000);
        Employee[] employees = new Employee[3];
        Employee employee1 = new Employee("tom",3000,ld);
        Employee employee2 = new Employee("jery",4000,ld);
        employees[0] = boss;
        employees[1] = employee1;
        employees[2] = employee2;
        for (int i = 0; i < employees.length; i++) {
            System.out.println(employees[i].getSalary());// 输出: 20000.0 , 3000.0 , 4000.0
        }
    }

}
