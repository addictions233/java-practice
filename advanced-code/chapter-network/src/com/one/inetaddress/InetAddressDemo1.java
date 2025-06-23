package com.one.inetaddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
 // 127.0.0.1
public class InetAddressDemo1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost = " + localHost);  // 输出:  localHost = LAPTOP-Q64UAEP5/192.168.67.207

        InetAddress name = InetAddress.getByName("192.168.67.207");
        System.out.println("name = " + name);  // 输出: name = LAPTOP-Q64UAEP5/192.168.67.207

    }
}
