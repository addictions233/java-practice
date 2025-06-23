package com.one.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * @author  Fox
 */
public class UnsafeFactory {

      // 直接这种方式获取Unsafe对象, 会抛出 java.lang.SecurityException: Unsafe
//    private static final Unsafe unsafe = Unsafe.getUnsafe();


    /**
     * 获取 Unsafe 对象
     * @return sun.misc.Unsafe
     */
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字段的内存偏移量
     * @param unsafe
     * @param clazz
     * @param fieldName
     * @return
     */
    public static long getFieldOffset(Unsafe unsafe, Class clazz, String fieldName) {
        try {
            return unsafe.objectFieldOffset(clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }


}
