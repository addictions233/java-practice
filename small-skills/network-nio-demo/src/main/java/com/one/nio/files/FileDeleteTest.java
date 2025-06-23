package com.one.nio.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @ClassName: FileDeleteTest
 * @Description: 使用walkFileTree()方法删除目录下所有的文件和文件夹
 * @Author: one
 * @Date: 2021/10/09
 */
public class FileDeleteTest {
    public static void main(String[] args) throws IOException {
        File file = new File("H:\\test");
//        Files.delete(Paths.get("H:\\test"));  // 直接删除目录,抛异常:  java.nio.file.DirectoryNotEmptyException
//        deleteMethod1(file);
//        deleteFiles(file);
        // 使用walkFileTree()方法删除带子项的目录
        Path path = Paths.get("H:\\copy");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.toString());
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                // 只有当目录中的所有子项都删除完了之后,才能删除该目录
                Files.delete(dir);
                System.out.println(dir.toString());
                return super.postVisitDirectory(dir, exc);
            }
        });

    }

    /**
     * 直接使用file.delete()方法只能删除空的目录或者文件,无法删除带有子项的非空目录
     */
    private static void deleteMethod1(File file) {
        boolean delete = file.delete();
        System.out.println(delete);
    }

    /**
     * 使用递归方式删除带子项的目录
     * @param file 文件
     */
    private static void deleteFiles(File file) {
        // 如果文件不存在,直接返回
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            // 如果是目录,获取所有子项
            File[] files = file.listFiles();
            for (File file1 : files) {
                // 递归调用
                deleteFiles(file1);
            }
        }
        // 删除完子项后,删除目录
        System.out.println("delete file:" + file.getName());
        file.delete();
    }
}
