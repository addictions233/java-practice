package com.one.mapstruct.demo1;

public class Main {

    public static void main(String[] args) {
        Student student = new Student("张三",23);
        StudentVO studentVO = student.convert();
        System.out.println(studentVO);

        StudentVO2 studentVO2 = student.convert2();
        System.out.println(studentVO2);
    }
}
