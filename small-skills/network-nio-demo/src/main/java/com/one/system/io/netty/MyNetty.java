package com.one.system.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author: 马士兵教育
 * @create: 2020-06-30 20:02
 */
public class MyNetty {

    /*

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
    </dependency>


    今天主要是netty的初级使用，如果对初级知识过敏的小伙伴可以
    先学点高级的 -。-
    非常初级。。。。
     */

    /*
    目的：前边 NIO 逻辑
    恶心的版本---依托着前面的思维逻辑
    channel  bytebuffer  selector
    bytebuffer   bytebuf【pool】
     */


    @Test
    public void myBytebuf(){

//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
        //pool
//        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);

        buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
        buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);




    }

    public static void print(ByteBuf buf){
        System.out.println("buf.isReadable()    :"+buf.isReadable());
        System.out.println("buf.readerIndex()   :"+buf.readerIndex());
        System.out.println("buf.readableBytes() "+buf.readableBytes());
        System.out.println("buf.isWritable()    :"+buf.isWritable());
        System.out.println("buf.writerIndex()   :"+buf.writerIndex());
        System.out.println("buf.writableBytes() :"+buf.writableBytes());
        System.out.println("buf.capacity()  :"+buf.capacity());
        System.out.println("buf.maxCapacity()   :"+buf.maxCapacity());
        System.out.println("buf.isDirect()  :"+buf.isDirect());
        System.out.println("--------------");






    }


    /*
    客户端
    连接别人
    1，主动发送数据
    2，别人什么时候给我发？  event  selector
     */

    @Test
    public void loopExecutor() throws Exception {
        //group  线程池
        NioEventLoopGroup selector = new NioEventLoopGroup(2);
        selector.execute(()->{
            try {
                for (;;){
                    System.out.println("hello world001");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
 selector.execute(()->{
            try {
                for (;;){
                    System.out.println("hello world002");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.in.read();
    }



    @Test
    public void clientMode() throws Exception {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);

        //客户端模式：
        NioSocketChannel client = new NioSocketChannel();

        thread.register(client);  //epoll_ctl(5,ADD,3)

        //响应式：
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());

        //reactor  异步的特征
        ChannelFuture connect = client.connect(new InetSocketAddress("192.168.150.11", 9090));
        ChannelFuture sync = connect.sync();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        //马老师的多线程
        sync.channel().closeFuture().sync();

        System.out.println("client over....");

    }

    @Test
    public void nettyClient() throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
//                .handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("192.168.150.11", 9090));

        Channel client = connect.sync().channel();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        client.closeFuture().sync();

    }





    @Test
    public void serverMode() throws Exception {

        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();


        thread.register(server);
        //指不定什么时候家里来人。。响应式
        ChannelPipeline p = server.pipeline();
        p.addLast(new MyAcceptHandler(thread,new ChannelInit()));  //accept接收客户端，并且注册到selector
//        p.addLast(new MyAcceptHandler(thread,new MyInHandler()));  //accept接收客户端，并且注册到selector
        ChannelFuture bind = server.bind(new InetSocketAddress("192.168.150.1", 9090));


        bind.sync().channel().closeFuture().sync();
        System.out.println("server close....");


    }

    @Test
    public void nettyServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
//                .childHandler(new ChannelInit())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress("192.168.150.1", 9090));

        bind.sync().channel().closeFuture().sync();

    }







}





