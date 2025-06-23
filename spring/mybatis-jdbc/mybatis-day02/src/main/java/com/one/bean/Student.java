package com.one.bean;

import java.util.List;

/**
 * @author one  多对多: 一个学生可以选多个课程,一个课程可以被多个学生选择
 */
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private List<Course> courses;

    public Student() {
    }

    public Student(Integer id, String name, Integer age, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}
