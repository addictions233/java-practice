package com.one.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author one
 * 用递归遍历F盘下的前端开发全套视频, 统计总共有多少个.ts结尾的视频文件
 */
public class FileTest2 {
    /**
     *  FileOutputStream 继承自OutputStream输出流
     */
    static FileOutputStream fos;

    static {
        try {
            // 对字节输出流对象进行初始化
            fos = new FileOutputStream("My_Day11\\目录.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static int count=0;

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\前端开发全套视频");
        getFile(file);
        System.out.println("count = " + count);

    }

    public static void getFile(File file) throws IOException {
        //获取file文件夹下的所有的文件和文件夹
        File[] files = file.listFiles();
        for (File file1 : files) {
            if(file1.isDirectory()){
                getFile(file1);
            } else {
                //将.ts视频文件打印出来
                String name = file1.getName();
                if (name.endsWith(".ts")){
                    // 每行打印了十个文件名称就进行换行
                    if((count++)%10==0){
                        fos.write("\r\n".getBytes());
                    }
                    // 输出流写出文件名称,并用空格隔开
                    fos.write(name.getBytes());
                    fos.write(" ".getBytes());
                }
            }
        }

    }
}
