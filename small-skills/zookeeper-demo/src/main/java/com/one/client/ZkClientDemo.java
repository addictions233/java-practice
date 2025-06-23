package com.one.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * zookeeper官方提供的客户端使用demo
 */
public class ZkClientDemo {

    private static final  String  CONNECT_STR="127.0.0.1:2181";
//    private final static  String CLUSTER_CONNECT_STR="192.168.65.163:2181,192.168.65.184:2181,192.168.65.186:2181";

    public static void main(String[] args) throws Exception {
        // 创建连接, 获取zookeeper对象
        ZooKeeper zooKeeper = ZooKeeperFactory.create(CONNECT_STR);

        //CONNECTED
        System.out.println(zooKeeper.getState());
        // 判断节点是否存在
        Stat stat = zooKeeper.exists("/user", false);
        if (null == stat) {
            //创建持久节点
            zooKeeper.create("/user", "fox".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        //永久监听  addWatch -m mode  /user
        zooKeeper.addWatch("/user", new Watcher() {
            // 只要监听的节点发生变化都会调用这个方法
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event);
                //TODO 监听到节点发生变化, 执行业务逻辑
            }
        }, AddWatchMode.PERSISTENT);


        stat = new Stat();
        byte[] data = zooKeeper.getData("/user", false, stat);
        System.out.println(" data: " + new String(data));
        // -1: 无条件更新
        //zooKeeper.setData("/user", "third".getBytes(), -1);
        // 带版本号的条件更新
        int version = stat.getVersion();
        zooKeeper.setData("/user", "fox".getBytes(), version);

        Thread.sleep(Integer.MAX_VALUE);

    }

}
