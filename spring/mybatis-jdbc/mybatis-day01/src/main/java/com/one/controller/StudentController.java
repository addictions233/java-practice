package com.one.controller;

import com.one.domain.Student;
import com.one.service.StudentService;
import com.one.service.impl.StudentServiceImpl;

import java.util.List;

/**
 * Controller控制层 调用service层
 * @author one
 */
public class StudentController {
    public static void main(String[] args) {
        StudentService service = new StudentServiceImpl();
//        selectById(service);
        selectAll(service);
//        Student stu = new Student();
////        stu.setId(1);
//        stu.setName("王大锤");
//        stu.setAge(18);
//        Integer i = service.insert(stu);
//        System.out.println(i);

    }

    /**
     *  测试service层的 selectAll()方法
     * @param service
     */
    private static void selectAll(StudentService service) {
        List<Student> students = service.selectAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    /**
     * 测试service层的 selectById()方法
     * @param service
     */
    private static void selectById(StudentService service) {
        Student student = service.selectById(1);
        System.out.println(student);
    }
}
