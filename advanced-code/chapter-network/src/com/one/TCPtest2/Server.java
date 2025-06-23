package com.one.TCPtest2;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(11111);
        //用多线程实现接收
        ExecutorService es = Executors.newCachedThreadPool();
        while(true){
            /**
             * 注意: 阻塞语句 Socket accept = ss.accept() 一定不能放在线程里面,即不能写在es.submit()里面
             * 因为阻塞语句是判断是否创建新的线程的判断条件,如果把阻塞语句放在线程里面,那么无限循环将创建无数个
             * 新的线程(小于Integer的最大值),且所有线程的阻塞语句都在等待客户端建立TCP连接,此时有一个客户端请求TCP连接时
             * 这个客户端请求就会和多个服务器端线程建立TCP连接,每个多线程的服务器端都会往下执行
             * 但是当把阻塞语句写在创建线程上面时,如果没有客户端请求建立TCP连接,那么主线程就一直停在阻塞语句这里,不会往下执行
             * 也就不会创建新的线程,只有当有客户端请求建立TCP连接时,主线程才会往下走,建立一个新的线程,然后又在阻塞语句这里
             * 等待新的客户端请求
             */
            //阻塞
            Socket accept = ss.accept();
            es.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized (ss){
                        try {
                            //创建服务器端读取对象
                            InputStream is = accept.getInputStream();

                            //创建字节输出流对象
                            FileOutputStream fos = new FileOutputStream("C:\\Users\\one\\Desktop\\server\\"+ UUID.randomUUID()+".jpg");
                            byte[] bys = new byte[1024];
                            int lenth;
                            while((lenth = is.read(bys))!= -1){
                                fos.write(bys,0,lenth);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }


    }
}
