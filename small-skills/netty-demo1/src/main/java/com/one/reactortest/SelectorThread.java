package com.one.reactortest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author one
 * @description 一个线程对应一个Selector
 * @date 2025-1-14
 */
public class SelectorThread implements Runnable {



    // 每个线程Thread对应一个Selector,
    // 多线程场景下, 该程序的并发客户端channel被分配到多个Selector上
    // 每个客户端channel只绑定其中一个Selector
    private Selector selector;

    /**
     * 保存selector需要绑定的channel
     */
    LinkedBlockingQueue<Channel> blockingQueue = new LinkedBlockingQueue<>();

    SelectThreadGroup selectThreadGroup;

    public SelectorThread(SelectThreadGroup selectThreadGroup) {
        try {
            // 获取selector选择器: 相当于调用etl_create,创建eventpoll对象 返回文件描述符(epoll的文件句柄)
            selector = Selector.open();
            this.selectThreadGroup = selectThreadGroup;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Selector getSelector() {
        return selector;
    }

    public void setWorker(SelectThreadGroup workerGroup) {
        this.selectThreadGroup  = workerGroup;
    }

    @Override
    public void run() {

        // Loop 死循环调用
        while(true) {
            try {
                System.out.println(Thread.currentThread().getName() + " before select...." + selector.keys().size());
                // 1. 调用select()方法: 调用内核的 epoll_wait() 获取所有的存在事件通知的文件描述符
                // 如果没有配置为非阻塞, select()方法是阻塞执行的, 直到有事件通知
                int num = selector.select();
//                int num = selector.select(50); // 带等待超时时间的调用
//                int num = selector.selectNow(); // 非阻塞
//                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " after select...");
                if (num > 0) {
                    // 2.如果有关注的事件通知,需要处理SelectionKey
                    Set<SelectionKey> keys = selector.selectedKeys();
                    // 获取迭代器
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while(iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 删除迭代器中的内容, 防止循环重复遍历到
                        iterator.remove();

                        if (key.isAcceptable()) {
                            // 如果是OP_ACCEPT, 接收Socket连接事件
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            // 如果是OP_READ, 可读事件
                            readHandler(key);
                        } else if (key.isWritable()) {
                            // 如果是OP_WRITE, 可写事件

                        }
                    }
                }

                // 3. 处理一些task
                if (!this.blockingQueue.isEmpty()) {
                    Channel channel = this.blockingQueue.take();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) channel;
                        // 注册 关注连接事件
                        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    } else if (channel instanceof SocketChannel) {
                        SocketChannel socketChannel = (SocketChannel) channel;
                        // 对于读事件需要分配ByteBuffer
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096); // 分配4kb
                        // 注册 关注可读事件
                        socketChannel.register(selector, SelectionKey.OP_READ, byteBuffer);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 处理客户端socket连接事件: 建立新的连接, 应用进程会生成一个新的文件描述符指向该socket连接
     * @param key
     */
    private void acceptHandler(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            System.out.println("acceptHandler...");
            // 1. 调用accept()方法, 接收客户端socket连接, 将连接状态有Listen改为Establish状态
            SocketChannel client = server.accept();
            // 配置客户端为非阻塞
            client.configureBlocking(Boolean.FALSE);

            // 2. 需要调用一个Selector, 注册关注可读事件
            selectThreadGroup.nextSelector(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 处理OP_READ可读事件
     */
    private void readHandler(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // Channel中的数据读取到ByteBuffer中
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        while (true) {
            try {
                // 将Socket接收缓冲区中的数据读取到byteBuffer中
                int read = socketChannel.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();
                    // 将读到的内容直接翻转, 写出到Socket发送缓冲区
                    // 这里只是演示效果, 真实的业务肯定不是这样的
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                } else if(read == 0) {
                    break;
                } else if(read < 0) {
                    // 读到的数据小于0, 表示客户端断开了连接
                    System.out.println("client:" + socketChannel.getRemoteAddress() + "closed...");
                    // 调用epoll_ctl, 将该文件描述符从事件关注中移除 (移出eventpoll的红黑树)
                    key.cancel();
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }


}
