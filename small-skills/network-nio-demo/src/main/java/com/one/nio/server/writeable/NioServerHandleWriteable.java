package com.one.nio.server.writeable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import static com.one.nio.server.Const.response;


/**
 * @author Mark老师
 * 类说明：nio通信服务端处理器
 */
public class NioServerHandleWriteable implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean started;
    /**
     * 构造方法
     * @param port 指定要监听的端口号
     */
    public NioServerHandleWriteable(int port) {
        try{
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；
            // 如果为 false，则此通道将被置于非阻塞模式
            serverChannel.configureBlocking(false);//开启非阻塞模式
            //绑定端口 backlog设为1024
            serverChannel.socket()
                    .bind(new InetSocketAddress(port),1024);
            //监听客户端连接请求
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            //标记服务器已开启
            started = true;
            System.out.println("服务器已启动，端口号：" + port);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        //循环遍历selector
        while(started){
            try{
                //阻塞,只有当至少一个注册的事件发生的时候才会继续.
				selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try{
                        handleInput(key);
                    }catch(Exception e){
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if(selector != null)
            try{
                selector.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
    }
    private void handleInput(SelectionKey key) throws IOException{
        System.out.println("当前通道的事件："+ key.interestOps());
        if(key.isValid()){
            //处理新接入的请求消息
            if(key.isAcceptable()){
                //获得关心当前事件的channel
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                //通过ServerSocketChannel的accept创建SocketChannel实例
                //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
                SocketChannel sc = ssc.accept();
                System.out.println("======socket channel 建立连接=======");
                //设置为非阻塞的
                sc.configureBlocking(false);
                //连接已经完成了，可以开始关心读事件了
                sc.register(selector, SelectionKey.OP_READ);
            }
            //读消息
            if(key.isReadable()){
                System.out.println("======socket channel 数据准备完成，" +
                        "可以去读==读取=======");
                SocketChannel sc = (SocketChannel) key.channel();
                //创建ByteBuffer，并开辟一个1M的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流，返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到字节，对字节进行编解码
                if(readBytes>0){
                    //将缓冲区当前的limit设置为position,position=0，
                    // 用于后续对缓冲区的读取操作
                    buffer.flip();
                    //根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String message = new String(bytes,"UTF-8");
                    System.out.println("服务器收到消息：" + message);
                    //处理数据
                    String result = response(message) ;
                    //发送应答消息
                    doWrite(sc,result);
                }
                //链路已经关闭，释放资源
                else if(readBytes<0){
                    key.cancel();
                    sc.close();
                }
            }
            if(key.isWritable()){
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = (ByteBuffer)key.attachment();
                if(buffer.hasRemaining()){
                    int count = sc.write(buffer);
                    System.out.println("write :"+count
                            +"byte, remaining:"+buffer.hasRemaining());
                }else{
                    /*取消对写的注册*/
                    key.interestOps(SelectionKey.OP_READ);
                }
            }
        }
    }
    //发送应答消息
    private void doWrite(SocketChannel channel,String response)
            throws IOException {
        //将消息编码为字节数组
        byte[] bytes = response.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        channel.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ,
                writeBuffer);
    }

    public void stop(){
        started = false;
    }

}
