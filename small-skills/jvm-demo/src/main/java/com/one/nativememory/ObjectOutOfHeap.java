package com.one.nativememory;

import sun.misc.Unsafe;

/**
 * @author one
 * @description 使用Unsafe#allocateMemory方法创建本地内存对象
 * @date 2025-2-28
 */
public class ObjectOutOfHeap {

    private long address = 0;

    private Unsafe unsafe = UnsafeFactory.getUnsafe();

    public ObjectOutOfHeap() {
        // 使用Unsafe获取的堆外内存，必须由程序显示的释放，JVM不会帮助我们做这件事情。
        // 由此可见，使用Unsafe是有风险的，很容易导致内存泄露。
        address = unsafe.allocateMemory(2 * 1024 * 1024);
    }

    // Exception in thread "main" java.lang.OutOfMemoryError
    public static void main(String[] args) {
        while (true) { // 不断的创建对象，不受JVM控制，不会释放内存，循环执行会OOM
            ObjectOutOfHeap heap = new ObjectOutOfHeap();
            System.out.println("memory address=" + heap.address);
        }
    }


}
