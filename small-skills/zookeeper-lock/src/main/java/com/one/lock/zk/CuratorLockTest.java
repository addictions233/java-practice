package com.one.lock.zk;/*
/**
 * @Author Fox
 *
 */

import com.one.service.OrderCodeGenerator;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 测试Curator提供的分布式锁
 */
public class CuratorLockTest implements Runnable {

    /**
     * 建立zookeeper客户端连接
     */
    final static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181")
            .retryPolicy(new ExponentialBackoffRetry(100, 1)).build();

    /**
     * Curator已经实现的可重入互斥分布式锁
     */
    final InterProcessMutex lock = new InterProcessMutex(client, "/curator_lock");


    public static void main(String[] args) throws InterruptedException {
        client.start();

        for (int i = 0; i < 30; i++) {
            new Thread(new CuratorLockTest()).start();
        }
        Thread.currentThread().join();

    }

    @Override
    public void run() {
        try {
            // 加锁
            lock.acquire();

            String orderCode = new OrderCodeGenerator().getOrderCode();
            System.out.println("生成订单号 " + orderCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放锁
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
