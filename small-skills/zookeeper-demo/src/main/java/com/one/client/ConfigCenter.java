package com.one.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.util.concurrent.TimeUnit;

/**
 * 使用zookeeper模拟配置中心, 当配置中心的数据发生变更, 自动通知
 */
@Slf4j
public class ConfigCenter {

    private final static  String CONNECT_STR="127.0.0.1:2181";


    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = ZooKeeperFactory.create(CONNECT_STR);


        MyConfig myConfig = new MyConfig();
        myConfig.setKey("anykey");
        myConfig.setValue("anyName");

        ObjectMapper objectMapper = new ObjectMapper();

        byte[] bytes = objectMapper.writeValueAsBytes(myConfig);

        //创建持久节点  create /myconfig
        zooKeeper.create("/myconfig", bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 添加监听器, 当数据发生变化时, 调用#process方法
        Watcher watcher = new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.NodeDataChanged
                        && event.getPath() != null && event.getPath().equals("/myconfig")) {
                    log.info(" PATH:{}  发生了数据变化", event.getPath());
                    // 获取配置信息, 参数this 使用递归的方式可以实现永久监听
                    byte[] data = zooKeeper.getData("/myconfig", this, null);
                    MyConfig newConfig = objectMapper.readValue(new String(data), MyConfig.class);
                    log.info("数据发生变化: {}", newConfig);

                }
            }
        };

        byte[] data = zooKeeper.getData("/myconfig", watcher, null);
        MyConfig originalMyConfig = objectMapper.readValue(new String(data), MyConfig.class);
        log.info("原始数据: {}", originalMyConfig);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
