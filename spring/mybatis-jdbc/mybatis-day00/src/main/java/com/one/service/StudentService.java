package com.one.service;

import com.one.bean.Student;
import com.one.mapper.StudentMapper;
import com.one.mapper.impl.StudentMapperImpl;

import java.util.List;


public class StudentService {

    public static void main(String[] args) {
        /**
         *  service层要想调用mapper层的方法必须在本类中创建mapper层实体类的对象
         */
        StudentMapper studentMapper = new StudentMapperImpl();
        insert(studentMapper);
//        selectAllTest(studentMapper);
//        insert(studentMapper);
        Student student = studentMapper.selectById(1);
        System.out.println(student);
    }

    /**
     * 向数据表中插入数据
     * @param studentMapper
     */
    private static void insert(StudentMapper studentMapper) {
        Student student = new Student();
        student.setId(null);
        student.setName("赵六");
        student.setAge(27);
        Integer i = studentMapper.insert(student);
        System.out.println(i);
    }


    /**
     *  测试mapper层的StudentMapperImpl类中的selectAll方法
     */
    private static void selectAllTest(StudentMapper studentMapper) {
        List<Student> students = studentMapper.selectAll();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
