package com.corejava.inheritance;

import java.time.LocalDate;

/**
 *  由于 超类和子类的继承关系导致的方法重载异常
 */
public class Test3 {
    public static void main(String[] args) {
        Manager manager = new Manager("hurry",9000.0, LocalDate.of(1995,9,9));
        OverrideTest test = new OverrideTest();
        test.showSalary(manager);
    }



}

class OverrideTest{
    public void showSalary(Employee employee){
        System.out.println(employee.getSalary());
    }

    public void showSalary(Manager manager){
        System.out.println(manager.getBonus());
    }
}