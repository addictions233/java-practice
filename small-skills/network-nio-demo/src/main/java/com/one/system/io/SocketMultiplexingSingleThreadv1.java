package com.one.system.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器编程: 单线程实现
 */
public class SocketMultiplexingSingleThreadv1 {

    //马老师的坦克 一 二期
    private ServerSocketChannel server = null;

    /**
     * java将多路复用器抽象成了selector: 兼容linux, windows多平台
     * linux 多路复用器（select poll    epoll kqueue） nginx  event{}
     */
    private Selector selector = null;
    int port = 9090;

    public void initServer() {
        try {
            // 获取server: 开启服务端进程对端口port进行监听
            // 当一个进程占用一个端口, 其他进程就无法使用这个端口了
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 如果是select  poll  *epoll 三种多路复用器  默认会优先选择：epoll  但是可以 -D修正
            // 如果在epoll模型下，open()方法相当于执行 内控的epoll_create -> fd3
            selector = Selector.open();

            // server 约等于 listen状态的 fd4 服务端对端口进行监听,
            // SelectableChannel#register() 方法
            // 如果select，poll：jvm里开辟一个数组 fd4 放进去, 保存所有的连接
            // 如果是epoll：  其实是调用内核的epoll_ctl(fd3,ADD,fd4, EPOLLIN
            server.register(selector, SelectionKey.OP_ACCEPT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("服务器启动了。。。。。");
        try {
            while (true) {  //死循环

                Set<SelectionKey> keys = selector.keys();
                System.out.println(keys.size()+"   size");


                // 1,调用多路复用器(select,poll  or  epoll  (epoll_wait))
                // select()是啥意思：
                // 1，select，poll  其实是调用内核的select（fd4）  poll(fd4)
                // 2，epoll：  其实调用内核的 epoll_wait() 获取所有的存在事件通知的文件描述符
                // select方法参数可以带超时时间：1.没有超时时间，0 ：阻塞， 2. 如果有超时时间设置一个超时
                // selector.wakeup()  如果没有可读事件发生, select方法结果返回0, 如果有可读事件发生, select方法结果大于0
                // 懒加载：
                // 其实再触碰到selector.select()调用的时候触发了epoll_ctl的调用
                while (selector.select() > 0) {
                    // selector#selectedKeys()方法返回的是有状态的fd集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    // 迭代所有的有状态的fd集合, 处理R/W 读写数据
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    // so，管你啥多路复用器，你呀只能给我状态，我还得一个一个的去处理他们的R/W。同步好辛苦！！！！！！！！
                    // NIO  自己对着每一个fd调用系统调用，浪费资源，那么你看，这里是不是调用了一次select方法，知道具体的那些可以R/W了？
                    // 我前边可以强调过，socket：  listen   通信 R/W
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove(); //set  不移除会重复循环处理
                        if (key.isAcceptable()) {
                            //看代码的时候，这里是重点，如果要去接受一个新的连接
                            //语义上，accept接受连接且返回新连接的文件描述符FD对吧？
                            //那新的FD怎么办？
                            //如果是select，poll，因为他们内核没有空间，那么在jvm中保存和前边的fd4那个listen的一起
                            //如果是epoll： 我们希望通过epoll_ctl把新的客户端fd注册到内核空间进行监听
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);  //连read 还有 write都处理了
                            //在当前线程，这个方法可能会阻塞  ，如果阻塞了十年，其他的IO早就没电了。。。
                            //所以，为什么提出了 IO THREADS
                            //redis  是不是用了epoll，redis是不是有个io threads的概念 ，redis是不是单线程的
                            //tomcat 8,9  异步的处理方式  IO  和   处理上  解耦
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理客户端的连接请求事件: 新的连接建立, 应用进程会生成一个新的文件描述符fd执行该连接,
     * 同时进程要监听该进程的读写事件, 在epoll模式下, 就必须将新的的fd调用epoll_ctl加入
     * 到该进程的监听集合中
     */
    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //来啦，目的是调用accept接受客户端连接  fd7
            // 将连接状态有Listen改为Establish
            SocketChannel client = ssc.accept();
            // 将连接设置为非阻塞
            client.configureBlocking(false);

            // 设置tcp连接的readBuffer, 读缓冲区的大小
            ByteBuffer buffer = ByteBuffer.allocate(8192);  //前边讲过了

            //再次调用了SelectableChannel#register() 方法
            // 同理
            // 如果是select，poll：jvm里开辟一个数组 fd7 放进去
            // 如果是epoll：  epoll_ctl(fd3,ADD,fd7,EPOLLIN
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理客户端的读数据事件
     * @param key
     */
    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        // 获取接收缓冲区, 前面设置了接收缓冲区的大小
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    // 客户端断开, 关闭连接
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv1 service = new SocketMultiplexingSingleThreadv1();
        service.start();
    }
}
