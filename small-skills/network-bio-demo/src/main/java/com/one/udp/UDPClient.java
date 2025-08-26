package com.one.udp;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author one
 */
public class UDPClient {
    public static void main(String[] args) throws IOException {
        InetAddress ipAddress = InetAddress.getByName("LAPTOP-ONE");
//        System.out.println(ipAddress);
        //创建高效字符流对象,从控制台读取字符串
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String line = inFromUser.readLine();
        // 创建客户端数据包套接字对象
        DatagramSocket clientSocket = new DatagramSocket();
        byte[] bytes = line.getBytes();
        //创建客户端需要发送的数据包,必须指定目的地的ip地址和端口号
        DatagramPacket sendPacket = new DatagramPacket(bytes,bytes.length,ipAddress,10086);
        // 创建客户端用来接口服务器端发送的数据包的接收对象
        DatagramPacket receivePacket = new DatagramPacket(new byte[1024],1024);
        //数据报套接字发送数据包
        System.out.println("发送数据包长度:"+sendPacket.getLength());
        clientSocket.send(sendPacket);
        //接收数据报
        clientSocket.receive(receivePacket);
        //解析
        //System.out.println("接收数据包长度:"+receivePacket.getLength());
        String newLine = new String(receivePacket.getData());

        //控制台打印输出
        System.out.println("newLine:" + newLine);
        // 关流
        inFromUser.close();
        clientSocket.close();

    }
}
