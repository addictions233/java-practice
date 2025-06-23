package com.one.iostream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author one
 * InputStream和OutputStream是字节输入输出流的根抽象类
 *   FileInputStream和FileOutputStream是上面两个字节流的实现类
 *   构造方法:  new FileOutputStream(String path) 或者 new  FileOutputStream(File file) 本质上第二种还是调用第一个构造方法
 *   特点: 1,如果输入流的构造方法中传入的文件路径不存在, 则会抛出 FileNotFoundException
 *         2,如果输出流只是路径结尾指向的文件不存在, 会在硬盘上创建这个文件 (注意: File类的构造方法不会创建文件, 要调用createNewFile()方法)
 *         3,如果输出流路径结尾指向的文件存在, 那么会覆盖之前的文件
 *   字节输入输出流只是对数据流动提供一个管道,并不能保存数据, 在内存中保存数据的容器是数组和集合
 *   在硬盘上保存数据的容器是文件
 */
public class InputStreamDemo {
    public static void main(String[] args) throws IOException {
        //创建输入流对象的路径结尾所指向的文件必须存在,否则会抛出FileNotFoundException
        FileInputStream fis = new FileInputStream("My_Day11\\交互桌面01.jpg");
        // 创建输出流的文件目录必须存储,但路径结尾所指向的文件可以不存在,因为创建文件输出流对象的时候会创建
        // 路径结尾指向的文件,即是该文件已经存在,也会在创建文件输出流对象的时候被重写
        FileOutputStream fos = new FileOutputStream("C:\\Users\\one\\Desktop\\美女.png");
        int byt;
        // 一次读取一个字节byte
        while((byt=fis.read())!= -1){
            fos.write(byt);
        }
        fos.close();
        fis.close();
        String changeLine = System.getProperty("line.separator");
        System.out.println("aaa" + changeLine + "bbb");

    }
}
