package com.corejava.comparable;

import java.util.TreeSet;

public class StudentTest {
    public static void main(String[] args) {
        TreeSet<Student> set = new TreeSet<>();
        set.add(new Student("张三",23));
        set.add(new Student("李四",24));
        set.add(new Student("王五",25));

        System.out.println(set);
    }
}
