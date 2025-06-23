package com.one.file;

import java.io.File;

/**
 * @ClassName: FileTest2
 * @Description: 循环删除一个文件夹(文件夹里面有文件或者文件夹)
 * @Author: one
 * @Date: 2021/07/20
 */
public class FileTest1 {
    public static void main(String[] args) {
        File file = new File("E:/IdeaProjects/Advanced_Code_119/My_Day11/src/com/itheima/file/ccc");
        deleteDir(file);

    }

    /**
     * 递归删除文件夹, 如果目录里面有文件或者文件夹, 是不能将该目录删除的,必须先将目录里面的内容删除之后
     * 才能删除空的目录
     * @param file
     */
    public static void deleteDir(File file){
        // 如果传入的是一个文件,直接将该文件删除
        if (file.isFile()) {
            boolean delete = file.delete();
            System.out.println(delete);
            return;
        }

        // 如果传入的是目录,就遍历目录下所用的文件或者文件夹
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                boolean delete = file1.delete();
                System.out.println(delete);
            } else if (file1.isDirectory()){
                deleteDir(file1);
            }
        }
        //删除完file对象里面所用的文件和文件夹,再将file对象删除
        boolean delete = file.delete();
        System.out.println(delete);
    }
}
