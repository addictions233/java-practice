package com.one.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * @author  Fox
 */
@Slf4j
public class SyncWaitTest {

    private Object lock = new Object();

    public void test() {
        log.debug(Thread.currentThread().getName()+" start");
        synchronized (lock){
            log.debug(Thread.currentThread().getName()+" execute");
            try {
                // Thread.sleep不会释放synchronized锁
                Thread.sleep(2000);
                Thread.currentThread().join();
                Thread.sleep(2000);
                // lock.wait会释放synchronized锁
//                lock.wait(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug(Thread.currentThread().getName()+" end");
        }

    }


    public static void main(String[] args) {
        SyncWaitTest test = new SyncWaitTest();

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    test.test();
                }
            }, "thread" + i).start();
        }

    }


}
