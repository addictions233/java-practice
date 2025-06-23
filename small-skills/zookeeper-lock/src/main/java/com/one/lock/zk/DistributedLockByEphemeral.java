package com.one.lock.zk;

import com.one.lock.AbstractLock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author one
 * 使用 Zookeeper临时节点实现分布式锁
 */
public class DistributedLockByEphemeral extends AbstractLock {

    private static final String connectString = "localhost:2181";
    private static final int sessionTimeout = 5000;

    private CountDownLatch lockAcquiredSignal = new CountDownLatch(1);

    /**
     * zookeeper客户端
     */
    private ZooKeeper zooKeeper;

    /**
     * 锁的key
     */
    private String lockPath;

    /**
     * 加锁: 非公平锁
     */
    public DistributedLockByEphemeral(String lockPath) {
        this.lockPath = lockPath;
        try {
            CountDownLatch connectLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    // 连接建立时, 打开latch, 唤醒wait在该latch上的线程
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        connectLatch.countDown();
                    }

                    // 发生了waitPath的删除事件
                    if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(lockPath)) {
                        // 使用事件监听 + JDK锁实现阻塞唤醒
                        lockAcquiredSignal.countDown();
                    }
                }
            });
            // 等待连接建立
            connectLatch.await();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean tryLock() {
        try {
            // 创建临时节点/lock
            zooKeeper.create(lockPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            // 节点已经存在，创建失败
            return false;
        }

        return true;
    }

    @Override
    public void waitLock() {
        try {
            //判断是否存在，监听节点
            Stat stat = zooKeeper.exists(lockPath, true);
            if (null != stat) {
                // 使用事件监听 + JDK锁实现阻塞唤醒, 获取锁失败的线程进入阻塞
                lockAcquiredSignal.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unlock() {
        try {
            //删除临时节点
            zooKeeper.delete(lockPath, -1);
            System.out.println("-------释放锁------");
        } catch (Exception e) {
        }
    }


}