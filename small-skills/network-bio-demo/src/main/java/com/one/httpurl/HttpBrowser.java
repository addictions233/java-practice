package com.one.httpurl;

import java.awt.*;
import java.net.URI;

/**
 * @author one
 * 通过系统默认的浏览器对指定的url路径发送http请求
 */
public class HttpBrowser {
    public static void main(String[] args) {
        String url = "http://localhost:8080/index.jsp";
//        for (int i = 0; i < 10; i++) {
//             runBrower(url);  //使用系统默认浏览器访问该url地址
//        }

        runBrowser(url);
    }

    public static void runBrowser(String url){
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                URI uri = null;
                try {
                    uri = new URI(url);
                    //使用系统默认的chrome浏览器执行这个url地址访问
                    desktop.browse(uri);
                    //线程等待1000ms后再往下执行
                    Thread.sleep(1000);
                    //关闭Chrome浏览器, 使用命令调用进程要防止命令注入
                    //Runtime.getRuntime().exec("taskkill  /IM chrome.exe");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}