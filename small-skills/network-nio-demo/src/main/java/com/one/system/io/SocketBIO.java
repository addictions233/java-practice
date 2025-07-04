package com.one.system.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketBIO {


    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9090, 20);
        System.out.println("step1: new ServerSocket(9090) ");
        while (true) {
            // 使用main主线程接收连接请求
            Socket client = server.accept();  //阻塞1
            System.out.println("step2:client\t" + client.getPort());
            new Thread(new Runnable() {
                public void run() {
                    InputStream in = null;
                    try {
                        in = client.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while (true) {
                            // 使用异步线程处理请求建立之后的数据读取
                            String dataline = reader.readLine(); //阻塞2

                            if (null != dataline) {
                                System.out.println(dataline);
                            } else {
                                client.close();
                                break;
                            }
                        }
                        System.out.println("客户端断开");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }


}
