package com.one.reactortest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author one
 * @description Selector线程组
 * @date 2025-1-15
 */
public class SelectThreadGroup {

    ServerSocketChannel serverSocketChannel;

    SelectorThread[] selectorThreads;

    AtomicInteger seq = new AtomicInteger(0);

    AtomicInteger workerSeq = new AtomicInteger(0);


    /**
     * 如果没设置worker线程组, 默认的工作线程组就是自己这个线程组
     */
    SelectThreadGroup workerGroup = this;


    public SelectThreadGroup(int num) {
        // 参数num是指线程数
        selectorThreads = new SelectorThread[num];
        for (int i = 0; i < selectorThreads.length; i++) {
            selectorThreads[i] = new SelectorThread(this);

            // 每个Selector需要绑定一个线程去执行
            new Thread(selectorThreads[i]).start();
        }
    }

    public void setWorker(SelectThreadGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public void bind(int port) {
        try {
            // 1.应用进程开启对端口的监听
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(Boolean.FALSE);
            serverSocketChannel.bind(new InetSocketAddress(port));

            // 2. 将serverSocketChannel绑定到哪个selector上
            nextSelector(serverSocketChannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void nextSelectorV3(Channel channel) throws IOException {
        if (channel instanceof ServerSocketChannel) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)channel;
            // 监听端口, 处理客户端来连接的channel, 用自己的Boss线程组来处理注册
            SelectorThread boss = next();
            boss.blockingQueue.add(channel);
            // 每个Boss线程都需要设置自己的workerGroup 工作线程组
            boss.setWorker(this.workerGroup);

            // 唤醒阻塞线程
            boss.getSelector().wakeup();
        } else if (channel instanceof SocketChannel) {
            SocketChannel socketChannel = (SocketChannel) channel;
            SelectorThread worker = nextV3();
            worker.blockingQueue.add(channel);

            // 唤醒阻塞线程
            worker.getSelector().wakeup();

        }
    }

    /**
     * 将Channel绑定到Selector上
     * @param channel 这个channel可能是ServerSocketChannel(Server服务端) , 也可能是SocketChannel(Client客户端)
     */
    public void nextSelector(Channel channel) throws ClosedChannelException {
        SelectorThread selectorThread = next();

        // 1. 通过队列传递数据
        selectorThread.blockingQueue.add(channel);

        // 前面调用了 selector.select()方法进行了阻塞, 这里唤醒阻塞的selector
        selectorThread.getSelector().wakeup();

//        // 重点: 这个channel可能是ServerSocketChannel(Server服务端) , 也可能是SocketChannel(Client客户端)
//        if (channel instanceof ServerSocketChannel) {
//            ServerSocketChannel server = (ServerSocketChannel) channel;
//            // 前面调用了 select.select()方法进行了阻塞, 这里唤醒阻塞, 返回不阻塞
//            selectorThread.getSelector().wakeup();
//            // 注册接收连接请求关注事件
//            server.register(selectorThread.getSelector(), SelectionKey.OP_ACCEPT);
//        }
    }

    /**
     * 从workerGroup中挑选一个线程来处理R/W读写事件
     * @return worker线程
     */
    private SelectorThread nextV3() {
        return workerGroup.selectorThreads[workerSeq.getAndIncrement() % workerGroup.selectorThreads.length];
    }

    /**
     * 从bossGroup中挑选一个线程来处理Accept连接事件
     * @return boss线程
     */
    private SelectorThread next() {
        return selectorThreads[seq.getAndIncrement() % selectorThreads.length];
    }
}
