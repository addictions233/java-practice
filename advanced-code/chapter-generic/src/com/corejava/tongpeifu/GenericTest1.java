package com.corejava.tongpeifu;

import com.corejava.genericclass.ArrayAlg;

/**
 *  泛型使用在遇到子父类继承关系时是怎么处理的?
 *          例如: Manager extends Employee
 *
 */
public class GenericTest1 {
    public static void main(String[] args) {
        //定义一个子类 Manager的对象数组

        Manager[] managers = {new Manager(),new Manager(),new Manager()};
        /*
            这样写是错误的, Pair<Employee> 和 Pair<Manager>是两种完全不同的类型,不存在继承关系
         */
//        ArrayAlg.Pair<Employee> tPair = ArrayAlg.minMax(managers); // 错误写法
        ArrayAlg.Pair<Manager> managerPair = ArrayAlg.minMax(managers);
        /*
            数组 employees每个元素都可Employee类型,根据多态特性可以接收数组managers里面的所有元素         */
        Employee[] employees = managers; // 数组的这种写法是允许的
    }
}
