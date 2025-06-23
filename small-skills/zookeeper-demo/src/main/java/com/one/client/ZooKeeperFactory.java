package com.one.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 使用工厂模式构建zookeeper客户端连接对象
 * @author one
 */
public class ZooKeeperFactory {

    /**
     * 建立连接的客户端超时时间
     */
    private static final int SESSION_TIMEOUT = 5000;

    /**
     * 构建客户端连接对象
     * @param connectionString 连接地址
     */
    public static ZooKeeper create(String connectionString) throws Exception {
        final CountDownLatch connectionLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectionString, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType()== Event.EventType.None
                        && event.getState() == Event.KeeperState.SyncConnected) {
                    connectionLatch.countDown();
                    System.out.println("连接建立");
                }
            }
        });
        // 阻塞等待连接建立
        System.out.println("等待连接建立...");
        connectionLatch.await();

        return zooKeeper;
    }

}
