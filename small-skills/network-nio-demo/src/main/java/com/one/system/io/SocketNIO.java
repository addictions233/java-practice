package com.one.system.io;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class SocketNIO {

    //  what   why  how
    public static void main(String[] args) throws Exception {

        /**
         * 保存所有的客户端连接对象
         */
        LinkedList<SocketChannel> clients = new LinkedList<>();

        // NIO 在java中 new IO
        ServerSocketChannel ss = ServerSocketChannel.open();  //服务端开启监听：接受客户端
        ss.bind(new InetSocketAddress(9090));
        // NIO在linux操作系统中 NONBlocking IO
        ss.configureBlocking(false); //重点  OS  NONBLOCKING!!!  //只让接受客户端  不阻塞

//        ss.setOption(StandardSocketOptions.TCP_NODELAY, false);
//        StandardSocketOptions.TCP_NODELAY
//        StandardSocketOptions.SO_KEEPALIVE
//        StandardSocketOptions.SO_LINGER
//        StandardSocketOptions.SO_RCVBUF
//        StandardSocketOptions.SO_SNDBUF
//        StandardSocketOptions.SO_REUSEADDR


        while (true) {
            //接受客户端的连接
            Thread.sleep(1000);
            // 服务端等待连接, 不会阻塞, 如果没有客户端连接请求, 操作系统层面返回-1, java层面返回null
            SocketChannel client = ss.accept(); //不会阻塞？  -1 NULL
            //accept  调用内核了：1，没有客户端连接进来，返回值？在BIO 的时候一直卡着，但是在NIO ，不卡着，返回-1，NULL
            //如果来客户端的连接，accept 返回的是这个客户端的fd  5，client  object
            //NONBLOCKING 就是代码能往下走了，只不过有不同的情况

            if (client == null) {
             //   System.out.println("null.....");
            } else {
                //重点  socket（服务端的listen socket<连接请求三次握手后，往我这里扔，我去通过accept得到连接的socket>，连接socket<连接后的数据读写使用的> ）
                // 客户端client设置为非阻塞的作用: 调用client读取数据时不会IO阻塞, 没有数据读取时返回0
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("client..port: " + port);
                clients.add(client);
            }

            // 直接内存: 堆外分配, 但是还是java进程占用的内存里
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);  //可以在堆里   堆外

            //遍历已经链接进来的客户端能不能读写数据
            for (SocketChannel c : clients) {   //串行化！！！！  多线程！！
                int num = c.read(buffer);  // >0  -1  0   //不会阻塞
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println(c.socket().getPort() + " : " + b);
                    buffer.clear();
                }


            }
        }
    }

}
