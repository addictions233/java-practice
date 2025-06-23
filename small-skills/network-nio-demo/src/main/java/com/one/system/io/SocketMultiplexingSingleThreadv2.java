package com.one.system.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 在异步线程里处理读事件和写事件
 */
public class SocketMultiplexingSingleThreadv2 {

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
                while (selector.select(50) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        // 把key从事件结果集中移除, 防止循环时重复进入
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
//                            key.cancel(); //调用cancel方法就是在epoll中调用epoll_ctl把文件描述符从红黑树中移除, 否则调用epoll_wait会一直拿到
                            System.out.println("in.....");

                            // 如何使用interestOps
                            // 注册事件‌：当创建一个Channel并将其注册到Selector时，可以通过SelectionKey的interestOps方法设置希望被监听的事件类型。
                            // 例如，如果希望监听读事件和写事件，可以调用key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE)。
                            // ‌修改事件‌：如果需要修改已经注册的事件类型，
                            // 可以使用key.interestOps(key.interestOps() | SelectionKey.OP_READ)来添加读事件，
                            // 或者使用key.interestOps(key.interestOps() | ~SelectionKey.OP_READ)来移除读事件。
                            key.interestOps(key.interestOps() | ~SelectionKey.OP_READ);

                            readHandler(key);//还是阻塞的嘛？ 即便以抛出了线程去读取，但是在时差里，这个key的read事件会被重复触发

                        } else if(key.isWritable()){  //我之前没讲过写的事件！！！！！
                            //写事件<--  send-queue  只要是空的，就一定会给你返回可以写的事件，就会回调我们的写方法
                            //你真的要明白：什么时候写？不是依赖send-queue是不是有空间
                            //1，你准备好要写什么了，这是第一步
                            //2，第二步你才关心send-queue是否有空间
                            //3，so，读 read 一开始就要注册，但是write依赖以上关系，什么时候用什么时候注册
                            //4，如果一开始就注册了write的事件，进入死循环，一直调起！！！
//                            key.cancel();

                            // 移除写事件关注
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_WRITE);

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
        new Thread(()->{
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
//            key.cancel();

//            try {
////                client.shutdownOutput();
//
////                client.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }).start();

    }

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

    public void readHandler(SelectionKey key) {
        new Thread(()->{
            System.out.println("read handler.....");
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            int read = 0;
            try {
                while (true) {
                    read = client.read(buffer);
                    System.out.println(Thread.currentThread().getName()+ " " + read);
                    if (read > 0) {
                        key.interestOps(SelectionKey.OP_READ);
                        // 注册写事件关注
                        client.register(key.selector(),SelectionKey.OP_WRITE,buffer);
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
        }).start();

    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadv2 service = new SocketMultiplexingSingleThreadv2();
        service.start();
    }
}
