package com.one.threadbase;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author one
 * @description 测试java线程的六中状态: java.lang.Thread.State
 * @date 2024-4-11
 */
@Slf4j
public class ThreadStateTest {

    public static void main(String[] args) throws InterruptedException {
        // java创建线程需要从用户态切换到内核态, 调用系统内核创建线程, 很重
        // 由于创建线程很重, 所以要使用线程池达到线程复用, 避免频繁的创建和销毁线程
        Thread thread = new Thread(() -> {
            // 如果加synconized, 可以让线程进入 BLOCK 阻塞状态
            LockSupport.park();  // WAITING 让线程进入等待状态

        });
        log.debug("线程状态:{}", thread.getState()); // NEW 线程初始状态
        thread.start();
        log.debug("线程状态:{}", thread.getState()); // RUNNABLE 线程可执行状态(可能在执行, 也可能没执行)
        Thread.sleep(100); // sleep方法不会释放锁
        log.debug("线程状态:{}", thread.getState());  // WAITING 线程进入等待状态
        LockSupport.unpark(thread); // 结束线程等待状态
        Thread.sleep(100);
        log.debug("线程状态:{}", thread.getState()); // TERMINATED 线程终止状态
    }
}
