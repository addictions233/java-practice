package com.corejava.generictype;

public class StudentTest {
    public static void main(String[] args) {
        Student stu = new Student();
        stu.setName("张三");
        stu.setAge(23);   // A age ;   在创建对象时没有确定泛型的话默认泛型就是Object类型
        System.out.println(stu);

        Student<String> stu1 = new Student<>();
        stu1.setName("李四");
        stu1.setAge("24");  //  A age;  在创建对象将泛型确定为String类型
        System.out.println(stu1);


        Student<Integer> stu2 = new Student<>();
        stu2.setName("王五");
        stu2.setAge(25); // A age;   在创建对象时将泛型确定为 Integer类型
        System.out.println(stu2);
    }
}
