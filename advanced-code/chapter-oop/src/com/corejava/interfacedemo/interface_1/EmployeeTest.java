package com.corejava.interfacedemo.interface_1;

import java.util.Arrays;

/**
 *  Employee类中重写了CompareTo()方法, 用Arrays工具类的sor()方法可以按照工资高低将数据中的元素进行排序
 */
public class EmployeeTest {
    public static void main(String[] args) {
        Employee[] staffs = new Employee[5];
        staffs[0] = new Employee("张三",30000);
        staffs[1] = new Employee("李四",35000);
        staffs[2] = new Employee("王五",9000);
        staffs[3] = new Employee("赵六",5000);
        staffs[4] = new Employee("马七",18000);

        Arrays.sort(staffs);

        for (int i = 0; i < staffs.length; i++) {
            System.out.println("名字:"+staffs[i].getName()+", 工资:"+staffs[i].getSalary());
        }
     }
}
