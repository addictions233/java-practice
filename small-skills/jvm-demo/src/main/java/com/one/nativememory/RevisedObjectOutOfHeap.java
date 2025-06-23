package com.one.nativememory;

import sun.misc.Unsafe;

/**
 * @author one
 * @description 如何释放本地内存
 * @date 2025-2-28
 */
public class RevisedObjectOutOfHeap {

    private long address = 0;


    private Unsafe unsafe = UnsafeFactory.getUnsafe();

    // 让对象占用堆内存,这样对象回收时会触发Full GC
    private byte[] bytes = null;

    public RevisedObjectOutOfHeap() {
        address = unsafe.allocateMemory(2 * 1024 * 1024);
        bytes = new byte[1024 * 1024];
    }

    /**
     * 覆盖了finalize方法，手动释放分配的堆外内存。如果堆中的对象被回收，那么相应的也会释放占用的堆外内存。
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize." + bytes.length);
        unsafe.freeMemory(address);
    }

    public static void main(String[] args) {
        while (true) {
            // 回收RevisedObjectOutOfHeap对象时，会触发Full GC，回收堆内存, 同时会释放堆外内存
            RevisedObjectOutOfHeap heap = new RevisedObjectOutOfHeap();
            System.out.println("memory address=" + heap.address);
        }
    }

}
