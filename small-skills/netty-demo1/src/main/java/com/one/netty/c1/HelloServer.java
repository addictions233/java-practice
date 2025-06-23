package com.one.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author one
 * @description 编写netty服务端, netty编程的模式都是一样的, 只需要添加自己需要的ChannelHandler
 * @date 2024-4-2
 */
public class HelloServer {
    public static void main(String[] args) {
        // 1.启动器 负责组织netty组件, 启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop, WorkerEventLoop(selector,thread), group组
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                // 4. boss负责处理连接 worker(child)负责处理读写, 决定了worker(child)能执行哪些操作(handler)
                .childHandler(
                        // 5. channel代表和客户端进行数据读写的通道Initialize初始化, 负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 6.ChannelPipeline中可以添加多个ChannelHandler
                                ch.pipeline().addLast(new StringDecoder()); // 将ByteBuf转换为字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){  // 自定义handler
                                    // 读事件
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                        // 打印上一步转换好的字符串
                                        System.out.println(msg);
                                    }
                                });
                            }
                        }
                )
                // 绑定端口
                .bind(8080);
    }
}
