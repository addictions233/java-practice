package com.one.service.impl;


import com.one.dao.StudentMapper;
import com.one.pojo.Student;
import com.one.service.StudentServiceSingle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName StudentServiceSingleImpl
 * @Description TODO
 * @Author one
 * @Date 2021/8/28 19:40
 * @Version 1.0
 */
@Service
public class StudentServiceSingleImpl implements StudentServiceSingle {

    /**
     * 子线程没有事务, 所有的子线程共用一个transactionStatus,这样能控制多个线程的事务吗???
     *
     * @param studentMapper
     * @param students
     * @param threadLatch
     */
    @Override
    public void updateStudents(StudentMapper studentMapper, List<Student> students, CountDownLatch threadLatch) {
        // 这里的子线程执行的任务没有开启事务
        try {
            students.forEach(s -> {
                // 更新教师信息
                // String teacher = s.getTeacher();
                String newTeacher = "TNO_" + new Random().nextInt(100);
                s.setTeacher(newTeacher);
                studentMapper.update(s);
            });
            System.out.println("子线程：" + Thread.currentThread().getName());
            threadLatch.countDown();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
