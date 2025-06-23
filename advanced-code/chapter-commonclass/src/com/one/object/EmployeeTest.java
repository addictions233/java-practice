package com.one.object;

public class EmployeeTest {
    public static void main(String[] args) {
        Employee employee1 = new Employee("Mike",80000,2010,9,9);
        System.out.println(employee1);
        System.out.println(employee1.hashCode());
    }
}
