package com.one.system.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 如果调用epoll_ctl注册写事件关注, 那么会频繁调用写事件的问题
 */
public class SocketMultiplexingSingleThreadv1_1 {

    private ServerSocketChannel server = null;
    private Selector selector = null;   //linux 多路复用器（select poll epoll） nginx  event{}
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();  //  select  poll  *epoll
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {
//                Set<SelectionKey> keys = selector.keys();
//                System.out.println(keys.size()+"   size");
                while (selector.select() > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            // 处理accept连接事件
                            acceptHandler(key);
                        } else if (key.isReadable()) {
//                            key.cancel();
                            // 只处理了read读事件,   并注册 关心这个key的write事件
                            readHandler(key);
                        } else if (key.isWritable()) {
                            // write写事件<--  send-queue  只要是空的，就一定会给你返回可以写的事件，就会回调我们的写方法
                            //你真的要明白：你想什么时候写是依赖你的代码业务逻辑？不是依赖send-queue是不是有空间（多路复用器能不能写是参考send-queue有没有空间）
                            //1，你准备好要写什么了，这是第一步
                            //2，第二步你才关心send-queue是否有空间
                            //3，so，读 read 一开始就要注册，但是write依赖以上关系，什么时候用什么时候注册
                            //4，如果一开始就注册了write的事件，进入死循环，一直调起！！！
//                            key.cancel();  // 对与写事件, 这种频繁的先register再cancel是消耗性能(频繁的系统调用), 不会这么写
                            writeHandler(key);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHandler(SelectionKey key) {

        System.out.println("write handler...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        while (buffer.hasRemaining()) {
            try {
                client.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.clear();
        // 将关注写事件的文件描述符从红黑树中移除
        key.cancel();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理accept连接事件的时候注册read读事件
     */
    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理read读事件的时候注册write写事件
     */
    public void readHandler(SelectionKey key) {
        System.out.println("read handler.....");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    // 注册write写事件
                    // 关心发送缓冲区是否为空  OP_WRITE 其实就是关系send-queue是不是有空间
                    client.register(key.selector(), SelectionKey.OP_WRITE, buffer);
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1_1 service = new SocketMultiplexingSingleThreadv1_1();
        service.start();
    }
}
