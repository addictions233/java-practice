package com.one.TCPtest2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        InetAddress adress = InetAddress.getByName("192.168.67.207");
        Socket socket = new Socket(adress, 11111);
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("C:\\Users\\one\\Pictures\\Camera Roll\\mn.jpg");
        byte[] bys = new byte[1024];
        int len;
        while((len = fis.read(bys))!= -1){
            os.write(bys,0,len);
        }

//        socket.shutdownOutput();

        os.close();
        socket.close();
    }
}
