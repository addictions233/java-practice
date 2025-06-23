package com.one.file;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: FileDemo4
 * @Description: File类: 1, file对象指向的文件或者文件夹可以不存在
 *      createNewFile()方法:  1, 如果file对象路径结尾指向的文件已经存在, 那么创建文件会失败, 抛IO异常
 *                            2, 如果file对象路径中的目录不存在,则创建文件对象会报错
 *      mkdir()方法: 创建单级文件夹 1, 如果创建单级文件夹中的目录不存在,则创建文件夹会失败,不抛异常
 *      mkDirs()方法: 创建多级文件夹 2, 创建多级文件夹的方法也可以创建多级文件夹,所以创建文件夹使用这个方法
 * @Author: one
 * @Date: 2021/11/27
 */
public class FileDemo4 {
    public static void main(String[] args) throws IOException {
//        demo();
        /**
         * 需求: 如果创建一个 My_Day11/src/com/itheima/file/aaa/bbb/ccc.txt 这个文件
         *    注意: aaa/bbb 这个文件夹不存在,  ccc.txt这个文件不存在
         *    createNewFile()方法只能创建文件,不能创建前面的目录
         *    mkdirs()只能创建目录,不能创建文键
         */
        File dirFile = new File("My_Day11/src/com/itheima/file/aaa/bbb");
        // 利用 new  File(File parent, String file)这个构造方法来创建file对象
        File file = new File(dirFile,"ccc.txt");
        // 先创建目录
        dirFile.mkdirs();
        // 目录存在后再创建文件
        file.createNewFile();
    }

    private static void demo() throws IOException {
        // 即使后面的路径并不存在,创建file对象也不会报错
        File file = new File("M\\aaa.txt");
        // 正常输出, 即是创建file对象的路径指向的文件或者文件夹不存在时,还能正常创建file对象
        System.out.println("file对象:" + file);
        // createNewFile() 方法: 创建文件
        // idea项目相对路径的起始路径默认是project工程的路径
        File file1 = new File("My_Day11/src/com/itheima/file11/ccc.txt");
        boolean newFile = file1.createNewFile();
        System.out.println(newFile);
    }
}
