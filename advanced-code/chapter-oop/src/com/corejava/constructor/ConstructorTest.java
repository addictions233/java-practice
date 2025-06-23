package com.corejava.constructor;

public class ConstructorTest {
    public static void main(String[] args) {
        // 创建一个数组存储三个 Employee类的对象
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("张三",5000);
        staff[1] = new Employee("李四",10000);
        staff[2] = new Employee();

        for (Employee employee : staff) {
            System.out.println(employee.getId()+"----"+employee.getName()+"----"+employee.getSalary()+"----"+Employee.getNextId());
        }
        /*
            输出:  2134----张三----5000.0----2137
                  2135----李四----10000.0----2137
                  2136--------0.0----2137
         */
    }
}
