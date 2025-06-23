package com.one.nio.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: WalkFileTreeTest
 * @Description: JDK7提供的Files工具类中有一个walkFileTree()方法用来所有的文件和文件夹
 * @Author: one
 * @Date: 2021/10/09
 */
public class WalkFileTreeTest {
    public static void main(String[] args) {
        try {
            // 对文件进行计数
            AtomicInteger fileCount = new AtomicInteger(0);
            // 对目录进行计数
            AtomicInteger dirCount = new AtomicInteger(0);
            // Files工具类和Paths工具类都是jdk7提供的用来操作文件和文件路径的工具类
            // 这里使用了访问器设计模式
            Files.walkFileTree(Paths.get("E:\\software\\jdk\\jdk1.8.0.251"), new SimpleFileVisitor<Path>() {
                /**
                 * 对访问到的文件进行操作的方法
                 * @param file 文件
                 * @param attrs 属性
                 * @return FileVisitResult
                 * @throws IOException
                 */
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 打印文件名
                    System.out.println(file.getFileName());
                    fileCount.incrementAndGet();
                    return super.visitFile(file, attrs);
                }

                /**
                 * 对访问到的目录进行操作的方法
                 * @param dir 目录
                 * @param attrs 属性
                 * @return FileVisitResult
                 * @throws IOException
                 */
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // 打印目录名称
                    System.out.println(dir.getFileName());
                    dirCount.incrementAndGet();
                    return super.preVisitDirectory(dir, attrs);
                }
            });
            System.out.println("fileCount:" + fileCount.get());
            System.out.println("dirCount:" + dirCount.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
