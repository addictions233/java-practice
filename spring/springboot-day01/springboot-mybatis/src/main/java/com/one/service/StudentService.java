package com.one.service;

import com.one.thread.StudentTaskError;
import com.one.pojo.Student;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName StudentService
 * @Description TODO
 * @Author one
 * @Date 2021/8/28 15:49
 * @Version 1.0
 */
public interface StudentService {
    /**
     * 使用多线程分配更新表数据, 每个线程都是一个单独事务,无法控制多个线程之间的事务
     *
     * @param students 需要更新的学生数据
     * @param threadLatch countDownLatch
     */
    void updateStudents(List<Student> students, CountDownLatch threadLatch);

    /**
     * 由于上面的多线程提交时，每个线程事务时单独的，无法保证一致性，我们尝试给多线程添加事务控制，来保证每个线程都是在插入数据完成后在提交事务，
     * 这里我们使用两个 CountDownLatch 来控制主线程与子线程事务提交
     *
     * @param students
     * @param threadLatch 第一个countDownLatch
     * @param mainLatch 第二个countDownLatch
     * @param taskStatus
     */
    void updateStudentsThread(List<Student> students, CountDownLatch threadLatch, CountDownLatch mainLatch, StudentTaskError taskStatus);

    /**
     * 使用多个transactionStatues事务集合来进行多线程之间的事务控制
     *
     * @param transactionManager
     * @param transactionStatuses
     * @param students
     */
    void updateStudentsTransaction(PlatformTransactionManager transactionManager, List<TransactionStatus> transactionStatuses, List<Student> students);
}
