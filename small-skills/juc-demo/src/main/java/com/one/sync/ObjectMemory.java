package com.one.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author one
 * @description 测试一个对象占用的内存大小
 * @date 2024-4-16
 */
public class ObjectMemory {

    public static void main(String[] args) {
        Object obj = new Object();
        obj.hashCode();
        // 查看对象内部信息
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        TestObj testObj = new TestObj();
        System.out.println(ClassLayout.parseInstance(testObj).toPrintable());
    }

    static class TestObj {
        long value;
    }
}
