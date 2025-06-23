package com.one.cas;

import sun.misc.Unsafe;

/**
 * @author one
 * @description 测试CAS自旋的底层实现
 * @date 2024-4-10
 */
public class CASTest {

    public static void main(String[] args) {
        Unsafe unsafe = UnsafeFactory.getUnsafe();
        Entity entity = new Entity();
        long offset = UnsafeFactory.getFieldOffset(unsafe, Entity.class, "x");
        boolean successFul;
        // compareAndSwap 只能保证原子性, 不能保证可见性
        // 4个参数分别是: 对象实例, 字段的内存偏移量, 字段比较期望值, 字段要更改后的值
        successFul = unsafe.compareAndSwapInt(entity, offset, 0, 3);
        System.out.println(successFul + "\t" + entity.x);

        successFul = unsafe.compareAndSwapInt(entity, offset, 3, 5);
        System.out.println(successFul + "\t" + entity.x);

        successFul = unsafe.compareAndSwapInt(entity, offset, 3, 8);
        System.out.println(successFul + "\t" + entity.x);
    }


    static class Entity {
        volatile int x;
    }
}
