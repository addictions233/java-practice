package com.corejava.generictype;

import java.util.Objects;

/**
 *  在学生类Student上声明泛型:目的是提供一种不确定的数据类供学生类的定义中使用
 * @param <A>
 */
public class Student<A> {  // 声明泛型
    private String name;
    private A age;   // 使用泛型A

    public Student() {
    }

    public Student(String name, A age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public A getAge() {
        return age;
    }

    public void setAge(A age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student<?> student = (Student<?>) o;
        return Objects.equals(name, student.name) &&
                Objects.equals(age, student.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
