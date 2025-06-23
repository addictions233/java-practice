package com.corejava.inheritance;

import java.time.LocalDate;

/**
 * y演示数组存储异常 ArrayStoreException
 */
public class Test2 {
    public static void main(String[] args) {
        Manager[] managers = new Manager[3];  //子类 经理数组
        Employee[] employees = new Employee[3]; // 父类 员工数组
        employees = managers; // 由于多态特殊,这个操作没有问题
        LocalDate ld = LocalDate.of(1997,2,21);
        employees[0] = new Employee("rose",3000.0,ld);
        System.out.println(managers[0].getBonus());  // 输出: java.lang.ArrayStoreException
    }
}
