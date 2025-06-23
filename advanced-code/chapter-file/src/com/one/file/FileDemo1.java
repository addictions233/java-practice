package com.one.file;

import java.io.File;

/**
 * @author one
 *  File: 文件类, 计算机所用的内容要么是文件要么是文件夹(目录),linux中一切皆文件
 *  File对象指的是路径结尾所指向的文件或者文件夹, 而不是指向路径, jdk7新增的Path,Paths,Files等类用来操作文件
 *  所谓的IO流就是将数据从硬盘中读取到内存中操作(磁盘IO),或者将网络传输的数据进行操作(网络IO),
 *  也能将内存中的数据写到硬盘中,或者在网络传输数据, IO流是通道, 数组是容器, 两者共同对数据进行搬运
 */
public class FileDemo1 {
    public static void main(String[] args) {
        File file = new File("JFKA;LJDF\\My_Day10");
        getFileName(file);
    }

    /**
     * 利用递归的方式获取一个目录下所用的文件和目录名称
     * @param file 文件对象
     */
    public static void getFileName(File file) {
        // 获取传入目录下所用的文件或者目录
        File[] files = file.listFiles();
        for (File file1 : files) {
            // 如果file1对象是文件
            if (file1.isFile()) {
                // 打印文件名
                System.out.println(file1.getName());
            }
            // 如果file1对象是目录
            if (file1.isDirectory()) {
                System.out.println(file1.getName());
                //使用递归: 递归三要素, 1,定义方法,方法要有形参,2, 方法要有出口3,形参是朝着方法出口变化的
                getFileName(file1);
            }
        }
    }
}
