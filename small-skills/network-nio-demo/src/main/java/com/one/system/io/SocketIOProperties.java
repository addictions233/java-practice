package com.one.system.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: 马士兵教育
 * @create: 2020-05-17 05:34
 * BIO  多线程的方式
 */
public class SocketIOProperties {


    //server socket listen property:

    /**
     * 设置socket接收缓冲区大小 (输入缓冲区)
     */
    private static final int RECEIVE_BUFFER = 10;

    /**
     * 设置accept的等待超时时间
     */
    private static final int SO_TIMEOUT = 0;
    private static final boolean REUSE_ADDR = false;

    /**
     * BACK_LOG值的含义
     * 服务端不能接收无限多的TCP连接请求, 对于内核已经完成了TCP三次握手但是等待被用户进程
     * 接收处理的连接最多只能有2个, 如果还有多的连接请求, 服务端不做应答, 不完成握手
     */
    private static final int BACK_LOG = 2;

    /**
     * client socket listen property on server endpoint:
     * 开启TCP的keepAlive, client和server会互相发送心跳, 确定对方是否还存活
     */
    private static final boolean CLI_KEEPALIVE = false;
    private static final boolean CLI_OOB = false;

    /**
     * 设置Socket缓存的输入缓冲区的
     */
    private static final int CLI_REC_BUF = 20;
    private static final boolean CLI_REUSE_ADDR = false;
    private static final int CLI_SEND_BUF = 20;
    private static final boolean CLI_LINGER = true;
    private static final int CLI_LINGER_N = 0;

    /**
     * 设置客户端的超时时间
     */
    private static final int CLI_TIMEOUT = 0;
    private static final boolean CLI_NO_DELAY = false;
/*

    StandardSocketOptions.TCP_NODELAY
    StandardSocketOptions.SO_KEEPALIVE
    StandardSocketOptions.SO_LINGER
    StandardSocketOptions.SO_RCVBUF
    StandardSocketOptions.SO_SNDBUF
    StandardSocketOptions.SO_REUSEADDR

 */


    public static void main(String[] args) {

        ServerSocket server = null;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(9090), BACK_LOG);
            server.setReceiveBufferSize(RECEIVE_BUFFER);
            server.setReuseAddress(REUSE_ADDR);
            server.setSoTimeout(SO_TIMEOUT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server up use 9090!");
        try {
            while (true) {
                // System.in.read();  //分水岭：
                Socket clientSocket = server.accept();  //阻塞的，没有 -1  一直卡着不动  accept(4,
                System.out.println("client port: " + clientSocket.getPort());

                clientSocket.setKeepAlive(CLI_KEEPALIVE);
                clientSocket.setOOBInline(CLI_OOB);
                clientSocket.setReceiveBufferSize(CLI_REC_BUF);
                clientSocket.setReuseAddress(CLI_REUSE_ADDR);
                // 设置内核发送TCP数据的发送缓冲区大小
                clientSocket.setSendBufferSize(CLI_SEND_BUF);
                clientSocket.setSoLinger(CLI_LINGER, CLI_LINGER_N);
                clientSocket.setSoTimeout(CLI_TIMEOUT);
                // 是否发送的数据积攒到一定大小才发送TCP数据包, 如果设置为true, 那么内控收到数据达到缓冲区大小就会立即发送TCP数据包
                clientSocket.setTcpNoDelay(CLI_NO_DELAY);

                // 打印socket连接的发送缓冲区和接收缓冲区大小
                int receiveBufferSize = clientSocket.getReceiveBufferSize();
                int sendBufferSize = clientSocket.getSendBufferSize();
                System.out.println("Receive Buffer Size:" + receiveBufferSize);
                System.out.println("Send Buffer Size:" + sendBufferSize);

                //client.read   //阻塞   没有  -1 0
                new Thread(
                        () -> {
                            try {
                                InputStream in = clientSocket.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                char[] data = new char[1024];
                                while (true) {
                                    int num = reader.read(data);
                                    if (num > 0) {
                                        System.out.println("client read some data is :" + num + " val :" + new String(data, 0, num));
                                    } else if (num == 0) {
                                        System.out.println("client readed nothing!");
                                        continue;
                                    } else {
                                        System.out.println("client readed -1...");
                                        System.in.read();
                                        clientSocket.close();
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
