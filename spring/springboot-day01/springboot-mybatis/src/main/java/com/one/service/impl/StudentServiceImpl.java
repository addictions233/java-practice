package com.one.service.impl;


import com.one.thread.StudentTaskError;
import com.one.dao.StudentMapper;
import com.one.pojo.Student;
import com.one.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName StudentServiceImpl
 * @Description 业务背景: 需要批量对表中1万多条数据进行更新,在for循环中执行sql
 *          1,不声明事务: 没有手动进行事务控制,所有每次执行sql都是自动提交事务，所以每次操作事务都会提交所以操作比较慢
 *          2, 对于大量循环数据库提交操作，添加手动事务控制可以有效提高操作效率,多条sql执行结束后,一次性提交事务
 *          3, 虽然手动提交了事务,但是单线程执行还是效率太低,引入多线程,问题是多线程如果保证多个线程的事务一致性?
 * @Author one
 * @Date 2021/8/28 15:50
 * @Version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    /**
     * 下面这种写法,每个线程都开启了事务,无法控制多个线程的事务一致性
     *
     * @param students
     * @param threadLatch
     */
    @Override
    public void updateStudents(List<Student> students, CountDownLatch threadLatch) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        System.out.println("子线程：" + Thread.currentThread().getName());
        try {
            students.forEach(s -> {
                // 更新教师信息
                // String teacher = s.getTeacher();
                String newTeacher = "TNO_" + new Random().nextInt(100);
                s.setTeacher(newTeacher);
                studentMapper.update(s); // 循环执行sql
            });
            // 提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Throwable e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transactionStatus);
        } finally {
            threadLatch.countDown();
        }
    }


    /**
     * 由于多线程提交时，每个线程事务时单独的，无法保证一致性，我们尝试给多线程添加事务控制，来保证每个线程都是在插入数据完成后在提交事务
     *
     * @param students
     * @param threadLatch 第一个countDownLatch
     * @param mainLatch 第二个countDownLatch
     * @param taskStatus
     */
    @Override
    public void updateStudentsThread(List<Student> students, CountDownLatch threadLatch, CountDownLatch mainLatch, StudentTaskError taskStatus) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        System.out.println("子线程：" + Thread.currentThread().getName());
        try {
            students.forEach(s -> {
                // 更新教师信息
                // String teacher = s.getTeacher();
                String newTeacher = "TNO_" + new Random().nextInt(100);
                s.setTeacher(newTeacher);
                studentMapper.update(s);
            });
        } catch (Throwable e) {
            taskStatus.setIsError();
        } finally {
            threadLatch.countDown(); // 切换到主线程执行
        }
        try {
            // 主线程在等待所有子线程执行结束, 在主线程中执行mainLatch.await(),可以起到当前子线程等待所有的其它的子线程执行结束
            mainLatch.await();
        } catch (Throwable e) {
            taskStatus.setIsError();
        }
        // 判断是否有错误，如有错误 就回滚事务, 用一个线程安全的对象taskStatus作为多个线程之间的通讯对象
        if (taskStatus.getIsError()) {
            dataSourceTransactionManager.rollback(transactionStatus);
        } else {
            dataSourceTransactionManager.commit(transactionStatus);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateStudentsTransaction(PlatformTransactionManager transactionManager, List<TransactionStatus> transactionStatuses, List<Student> students) {
        // 使用这种方式将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
        TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态,每个线程都单独生成一个transactionStatus
        transactionStatuses.add(status); // 将transactionStatus事务状态对象放进集合中,最后统一提交
        // 执行sql
        students.forEach(s -> {
            // 更新教师信息
            // String teacher = s.getTeacher();
            String newTeacher = "TNO_" + new Random().nextInt(100);
            s.setTeacher(newTeacher);
            studentMapper.update(s);
        });
        System.out.println("子线程：" + Thread.currentThread().getName());
    }
}
