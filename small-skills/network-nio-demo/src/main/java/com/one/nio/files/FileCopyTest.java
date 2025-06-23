package com.one.nio.files;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @ClassName: FileCopyTest
 * @Description: 将一个文件夹批量拷贝到另外一个文件夹中
 * @Author: one
 * @Date: 2021/10/09
 */
public class FileCopyTest {
    public static void main(String[] args) {
        String source = "H:\\test1";
        String target = "H:\\copy";

        try {
            // 遍历所有的目录和文件
            Files.walk(Paths.get(source)).forEach(path -> {
                // 获取拷贝后的保存路径, 用目标保存路径替换源路径
                String targetPath = path.toString().replace(source, target);
                if (Files.isDirectory(path)) {
                    // 如果是目录,就创建目录
                    try {
                        Files.createDirectories(Paths.get(targetPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (Files.isRegularFile(path)) {
                    // 如果是文件,就拷贝文件
                    try {
                        Files.copy(path,Paths.get(targetPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
