package com.corejava.tongpeifu;

import java.util.ArrayList;

public class GenericTest2 {
    public static void main(String[] args) {
        ArrayList<Employee> list = new ArrayList<>();
        Employee employee = new Employee();
        Manager manager = new Manager();

        list.add(employee);
        list.add(manager);
        System.out.println(list);

        ArrayList<Manager> list2 = new ArrayList<>();
        /*
            这样写是错误的, ArrayList<Employee>和 ArrayLsit<Manager>是完全不相干的类型
         */
//        list = list2; //错误写法

        ArrayList<? extends Employee> list3 = new ArrayList<>();
        /*
            ArrayList<Manager>类型是 ArrayList<? extend Employee>类型的子类
         */
        list3 = list2; //正确写法

    }
}
