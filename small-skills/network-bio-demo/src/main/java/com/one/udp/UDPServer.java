package com.one.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        //创建服务器端数据报套接字
        DatagramSocket serverSocket = new DatagramSocket(10086);
        while (true){
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024],1024);
            serverSocket.receive(receivePacket);
            System.out.println("接收数据包长度:"+receivePacket.getLength());
            //从客户端发送来的数据包中获取客户端的ip地址
            InetAddress address = receivePacket.getAddress();
            //获取数据包中获取客户端的端口号
            int port = receivePacket.getPort();
            String line = new String(receivePacket.getData());
            String newLine = line.toUpperCase();
            byte[] bytes = newLine.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,address,port);
            serverSocket.send(sendPacket);
//            System.out.println("发送的数据包长度:"+sendPacket.getLength());

        }
    }
}
