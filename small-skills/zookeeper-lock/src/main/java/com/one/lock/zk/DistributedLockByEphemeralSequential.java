package com.one.lock.zk;

import com.one.lock.Lock;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 使用zookeeper临时有序节点创建分布式锁: : 天然的能实现分布式阻塞队列
 */
public class DistributedLockByEphemeralSequential implements Lock {

    // zookeeper server列表
    private String connectString = "localhost:2181";
    // 超时时间
    private int sessionTimeout = 5000;

    private ZooKeeper zooKeeper;

    private String rootNode = "locks";
    private String subNode = "seq-";

    // 当前client等待的子节点
    private String waitPath;

    //ZooKeeper连接
    private CountDownLatch connectLatch = new CountDownLatch(1);
    //ZooKeeper节点等待
    private CountDownLatch waitLatch = new CountDownLatch(1);

    // 当前client创建的子节点
    private String currentNode;

    // 和zk服务建立连接，并创建根节点
    public DistributedLockByEphemeralSequential() {

        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    // 连接建立时, 打开latch, 唤醒wait在该latch上的线程
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        connectLatch.countDown();
                    }

                    // 发生了waitPath的删除事件
                    if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath)) {
                        waitLatch.countDown();
                    }
                }
            });
            // 等待连接建立
            connectLatch.await();

            //获取根节点状态
            Stat stat = zooKeeper.exists("/" + rootNode, false);

            //如果根节点不存在，则创建根节点，根节点类型为永久节点
            if (stat == null) {
                System.out.println("根节点不存在");
                zooKeeper.create("/" + rootNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 加锁: 公平锁
     */
    @Override
    public void lock() {

        try {
            //在根节点下创建临时顺序节点，返回值为创建的节点路径
            currentNode = zooKeeper.create("/" + rootNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            // wait一小会, 让结果更清晰一些
            Thread.sleep(50);

            // 注意, 没有必要监听"/locks"的子节点的变化情况
            List<String> childrenNodes = zooKeeper.getChildren("/" + rootNode, false);

            // 列表中只有一个子节点, 那肯定就是currentNode , 说明client获得锁
            if (childrenNodes.size() == 1) {
                return;
            } else {
                //对根节点下的所有临时顺序节点进行从小到大排序
                Collections.sort(childrenNodes);

                //当前节点名称
                String thisNode = currentNode.substring(("/" + rootNode + "/").length());
                //获取当前节点的位置
                int index = childrenNodes.indexOf(thisNode);

                if (index == -1) {
                    System.out.println("数据异常");
                } else if (index == 0) {
                    // index == 0, 说明thisNode在列表中最小, 当前client获得锁
                    return;
                } else {
                    // 获得排名比currentNode 前1位的节点
                    this.waitPath = "/" + rootNode + "/" + childrenNodes.get(index - 1);
                    // 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法
                    zooKeeper.getData(waitPath, true, new Stat());
                    //进入等待锁状态
                    waitLatch.await();
                }
            }
        } catch (Exception e) {
        }
    }

    // 解锁方法
    @Override
    public void unlock() {
        try {
            zooKeeper.delete(this.currentNode, -1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


}
