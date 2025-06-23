package com.one.UDP;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPTest2 {
    public static void main(String[] args) throws IOException {
        int i = 0;
        //创建文件读取流
        FileInputStream fis = new FileInputStream("C:\\Users\\one\\Desktop\\截图.png");
        byte[] bys = new byte[1024];
        int len = fis.read(bys);
        //创建UDP发送对象
        DatagramSocket ds = new DatagramSocket();

        //获取广播地址的InetAdress对象
        InetAddress address = InetAddress.getByName("192.168.67.207");
            i=32;
            //创建要发送的字节数组
            String line = new String(bys,0,len);
            String newLine = appendLine(line, i);
            //创建UDP通信数据包
            byte[] bytes = newLine.getBytes("GBK");
            DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, address, 2425);
//            String line = "我在和你通信" + i;
//            byte[] bys = newLine.getBytes("GBK");
            //发送数据
            ds.send(packet);

//        }





        //关流
//        ds.close();
    }

    private static String appendLine(String line,int i) {
        // version:time:sender:ip:flag:content  版本:时间:发送者:发送者ip地址:信息表示:内容
        StringBuilder sb = new StringBuilder();
        sb.append("1:").append(System.currentTimeMillis()+":").append("one:").append("192.168.67.207:")
                .append(i+":").append(line);
        return sb.toString();
    }
}
