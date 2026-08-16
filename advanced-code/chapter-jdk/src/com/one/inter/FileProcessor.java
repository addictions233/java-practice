package com.one.inter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * 接口中新增的方法类型:
 *     jdk8: public default方法, public static 静态方法
 *     jdk9: private 私有方法
 */
public interface FileProcessor {

    /**
     * 抽象方法: 子类必须重写的
     * @param path 文件路径
     */
    void process(Path path);

    /**
     * 接口中定义默认方法: 子类可以选择进行重写, 也可以直接使用
     * @param fileName 文件名称
     */
    default void processFile(String fileName) {
        // 调用接口中的私有方法
        validateFileName(fileName);
        try (Stream<Path> paths = Files.list(Path.of(fileName))) {
            paths.forEach(this::process);
        } catch (IOException e) {
            // 调用接口中的公共静态方法
            handleException(e);
        }
        // 调用接口中的私有静态方法
        log(fileName);
    }

    /**
     * 接口中定义 public static修饰的公共静态方法
     * @param e 异常信息
     */
    static void handleException(Exception e) {
        log("Error:" + e.getMessage());
    }

    /**
     * 接口中可以定义 私有方法
     * @param fileName 文件名称
     */
    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("fileName cannot be empty");
        }
    }

    /**
     * 接口中定义私有静态方法
     * @param message message
     */
    private static void log(String message) {
        System.out.println("[" + LocalDateTime.now() + "]" + message);
    }
}
