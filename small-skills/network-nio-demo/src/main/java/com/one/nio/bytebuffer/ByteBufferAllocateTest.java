package com.one.nio.bytebuffer;

import com.one.nio.util.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @ClassName: ByteBufferAllocateTest
 * @Description: 测试ByteBuffer的allocate()方法: 分配内存空间和get()方法: 读取数据
 * @Author: one
 * @Date: 2021/09/20
 */
public class ByteBufferAllocateTest {
    public static void main(String[] args) {
        // 使用JVM的heap堆内存: 读写效率较低, 收到GC回收机制影响
        // 输出: class java.nio.HeapByteBuffer
        System.out.println(ByteBuffer.allocate(10).getClass());
        // 使用系统直接内存: 是堆外内存, 读写效率高, 少一次拷贝, 不受GC回收机制影响,
        // 缺点是: 分配内存效率低一些,需要调用系统函数,使用结束要合理地释放内存,否则会造成内存泄露
        // 输出: class java.nio.DirectByteBuffer
        System.out.println(ByteBuffer.allocateDirect(10).getClass());

        /*
         * ByteBuffer读取数据的方法: get(), get(int index), rewind()方法
         * get()方法会让position读指针往后走,读取一个字节,读指针往后移动一位,类似于迭代器
         * get(int index)方法获取指定索引index的内容,它不会移动读指针
         * rewind()方法将position读指针的位置重新置为0
         */
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(new byte[] {'a','b','c','d'});
        // 切换到到读模式
        byteBuffer.flip();
        System.out.println(byteBuffer.get(new byte[4]));
        //position读指针位置变为4
        ByteBufferUtil.debugAll(byteBuffer);
        // rewind()方法将position读指针的位置重置为0
        byteBuffer.rewind();
        // 输出: a
        System.out.println((char)byteBuffer.get());

        /*
         * mark()和reset()方法: 可以看做对rewind()方法的补充增强,
         * 让byteBuffer()的读指针返回到mark()方法标记的索引位置
         * mark()方法: 标志一个索引位置
         * reset()方法: 将position读指针切换到mark()方法标记的位置
         */
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
        byteBuffer1.put(new byte[] {'a','b','c','d','e'});
        byteBuffer1.flip();
        System.out.println((char)byteBuffer1.get());
        System.out.println((char)byteBuffer1.get());
        // 标志索引
        byteBuffer1.mark();
        System.out.println((char)byteBuffer1.get());
        System.out.println((char)byteBuffer1.get());
        // 将读指针重置为标记的索引位置
        byteBuffer1.reset();
        System.out.println((char)byteBuffer1.get());
        System.out.println((char)byteBuffer1.get());

        /*
         * get(int index): 获取指定索引的元素,不改变position读指针的位置
         */
        System.out.println((char)byteBuffer1.get(3));


    }
}
