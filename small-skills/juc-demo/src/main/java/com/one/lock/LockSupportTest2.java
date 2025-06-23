package com.one.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author one
 * @description 测试LockSupport的使用
 * @date 2025-3-5
 */
public class LockSupportTest2 {

    public static void main(String[] args) throws Exception {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("before park");
                LockSupport.park(this);
                // 再执行park
                System.out.println("after park");
            }
        });
        System.out.println("before unpark");
        parkThread.start();
        Thread.currentThread().sleep(1000);
        LockSupport.unpark(parkThread);
        // 先执行unpark
        System.out.println("after unpark");
    }
}
