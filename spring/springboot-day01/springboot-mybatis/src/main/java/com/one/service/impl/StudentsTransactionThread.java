package com.one.service.impl;


import com.one.dao.StudentMapper;
import com.one.pojo.Student;
import com.one.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @ClassName StudentsTransactionThread
 * @Description 问题: 基于多个线程的事务控制的问题: 由于这个方式需要等待线程执行完成后才会提交事务，所有任务会占用Jdbc连接池，
 *       如果线程数量超过连接池最大数量会产生连接超时。所以在使用过程中任要控制线程数量
 * @Author one
 * @Date 2021/8/29 9:50
 * @Version 1.0
 */
@Service
public class StudentsTransactionThread {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 基于多个TransactionStatus的集合来控制多线程事务提交
     */
    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<TransactionStatus>());

    /**
     * 实现主线程和异步线程之间的事务控制:
     * 基本思路: 每个异步线程都一个事务,当所有的异步线程执行结束后,在主线程中为所有的
     * 异步线程统一提交事务
     *
     * @throws InterruptedException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateStudentWithThreadsAndTrans() throws InterruptedException {
        //查询总数据
        List<Student> allStudents = studentMapper.getAll();

        // 线程数量
        final Integer threadCount = 2;

        //每个线程处理的数据量
        final Integer dataPartionLength = (allStudents.size() + threadCount - 1) / threadCount;

        // 创建多线程处理任务
        ExecutorService studentThreadPool = Executors.newFixedThreadPool(threadCount);
        CountDownLatch threadLatchs = new CountDownLatch(threadCount);
        AtomicBoolean isError = new AtomicBoolean(false);
        try {
            for (int i = 0; i < threadCount; i++) {
                // 每个线程处理的数据
                List<Student> threadDatas = allStudents.stream()
                        .skip(i * dataPartionLength).limit(dataPartionLength).collect(Collectors.toList());
                studentThreadPool.execute(() -> {
                    try {
                        try {
                            studentService.updateStudentsTransaction(transactionManager, transactionStatuses, threadDatas);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            isError.set(true);
                        }finally {
                            threadLatchs.countDown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isError.set(true);
                    }
                });
            }

            // 倒计时锁设置超时时间 30s
            boolean await = threadLatchs.await(30, TimeUnit.SECONDS);
            // 判断是否超时
            if (!await) {
                isError.set(true);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            isError.set(true);
        }

        if (!transactionStatuses.isEmpty()) {
            if (isError.get()) {
                // 回滚所有的事务transactionStatus
                transactionStatuses.forEach(transactionStatus -> transactionManager.rollback(transactionStatus));
            } else {
                // 提交所有的事务基于transactionStatus
                transactionStatuses.forEach(transactionStatus -> transactionManager.commit(transactionStatus));
            }
        }
        System.out.println("主线程完成");
    }
}
