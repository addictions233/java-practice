package com.one.optimize;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author one
 * @description 测试OOM情况下导出dump文件
 * @date 2024-12-8
 */
public class OOMTest {

    // JVM设置
    // -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\jvm.dump
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (true) {
            list.add(new User(i++, UUID.randomUUID().toString()));
            new User(j--, UUID.randomUUID().toString());
        }
    }
}
