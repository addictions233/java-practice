package com.one.task;

import com.one.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * @ClassName: QueryUserTask
 * @Description: 查询任务对象
 * @Author: one
 * @Date: 2022/01/11
 */
public class QueryUserTask implements Runnable {
    /**
     * 让主线程等待异步线程执行结束
     */
    private CountDownLatch countDownLatch;

    /**
     * 执行任务对象，并获取结果
     */
    private Supplier<List<User>> supplier;

    /**
     * 保存异步线程执行结果
     */
    private List<User> userList;

    public QueryUserTask(List<User> userList, CountDownLatch countDownLatch, Supplier<List<User>> supplier) {
        this.countDownLatch = countDownLatch;
        this.supplier = supplier;
        this.userList = userList;
    }

    @Override
    public void run() {
        try {
            List<User> users = Optional.ofNullable(supplier.get()).orElse(new ArrayList<>());
            userList.addAll(users);
        } finally {
            countDownLatch.countDown();
        }
    }
}
