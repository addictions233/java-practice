package com.one.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *  案例演示:
 *      用IDEA2给飞秋发消息
 */
public class UDPTest {
    public static void main(String[] args) throws IOException {
        //创建UDP传输文件对象
        DatagramSocket ds = new DatagramSocket();
        //创建接收方InetAdress对象,通过名字获取,名字可以是主机名也可以是对方主机IP地址
        InetAddress ia = InetAddress.getByName("192.168.67.129");
        String line = "50下课,好人一生平安";
        byte[] bys = line.getBytes();
        DatagramPacket packet = new DatagramPacket(bys,0,bys.length,ia,10010);

        //用UDP协议发送信息
        ds.send(packet);

        //关流
        ds.close();
    }

}
