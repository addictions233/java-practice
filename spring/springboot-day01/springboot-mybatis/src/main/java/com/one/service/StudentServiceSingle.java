package com.one.service;


import com.one.dao.StudentMapper;
import com.one.pojo.Student;


import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName StudentServiceSingle
 * @Description TODO
 * @Author one
 * @Date 2021/8/28 19:34
 * @Version 1.0
 */
public interface StudentServiceSingle {
    /**
     * 使用异步线程修改数据
     *
     * @param studentMapper
     * @param students
     * @param threadLatch
     */
    void updateStudents(StudentMapper studentMapper, List<Student> students, CountDownLatch threadLatch);
}
