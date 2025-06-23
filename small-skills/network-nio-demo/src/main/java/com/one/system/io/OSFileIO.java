package com.one.system.io;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class OSFileIO {

    static byte[] data = "123456789\n".getBytes();
    static String path = "/root/testfileio/out.txt";

    public static void main(String[] args) throws Exception {
        switch (args[0]) {
            case "0":
                testBasicFileIO();
                break;
            case "1":
                testBufferedFileIO();
                break;
            case "2":
                testRandomAccessFileWrite();
            case "3":
//                whatByteBuffer();
            default:

        }
    }


    /**
     * 最基本的file写
     * 将数据写入内核空间的Page Cache, 形成脏页 dirty, 需要flush才会真正写入磁盘, 否则会丢失
     * 可以通过参数设置flush的触发时机
     */
    public static void testBasicFileIO() throws Exception {
        File file = new File(path);
        FileOutputStream out = new FileOutputStream(file);
        while (true) {
            Thread.sleep(10);
            out.write(data);

        }

    }


    /**
     * 测试buffer文件IO
     * jvm  8kB   syscall  write(8KB byte[])
     * 为什么使用Buffer IO比普通IO效率更高: 因为Buffer会在用户空间创建8KB的缓存, 只有当8KB缓存写满
     * 才会进行系统调用写入Page Cache, 所以可以减少系统调用次数, 减少用户态到内核态的切换次数来节省时间
     */
    public static void testBufferedFileIO() throws Exception {
        File file = new File(path);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            Thread.sleep(10);
            out.write(data);
        }
    }


    /**
     * 测试文件NIO New IO
     */
    public static void testRandomAccessFileWrite() throws Exception {


        RandomAccessFile raf = new RandomAccessFile(path, "rw");

        raf.write("hello mashibing\n".getBytes());
        raf.write("hello seanzhou\n".getBytes());
        System.out.println("write------------");
        System.in.read();

        raf.seek(4);
        raf.write("ooxx".getBytes());

        System.out.println("seek---------");
        System.in.read();

        FileChannel rafchannel = raf.getChannel();
        // mmap(memory-mapped) 使用的堆外内存和文件进行映射的ByteBuffer (not object)不是对象,所以不占用堆内内存
        // 只用文件才能进行映射map, 因为文件是块设备, 其他的流设备不能进行映射
        MappedByteBuffer map = rafchannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);

        // mmap不需要进行系统调用system call  但是数据会到达内核的Page Cache页缓存
        map.put("@@@".getBytes());

        // 曾经我们是需要out.write()  这样的系统调用system call，才能让应用程序的data进入内核的Page Cache
        // 曾经必须有用户态到内核态切换
        // mmap的内存映射，依然是内核的Page Cache体系所约束的！！！
        // 换言之，还是有可能因为脏页丢数据
        // mmap只是不用从用户态切换到内核态, 但是还是用的内核管理的Page Cache, 区别于Direct IO直接IO
        // 你可以去github上找一些 其他C程序员写的jni扩展库，使用linux内核的Direct IO
        // Direct IO是忽略linux的Page Cache
        // 是把Page Cache交给了程序自己开辟一个字节数组当作Page Cache，动用代码逻辑来维护一致性/dirty。。。一系列复杂问题
        System.out.println("map--put--------");
        System.in.read();

//        map.force(); //  flush


        raf.seek(0);

        ByteBuffer buffer = ByteBuffer.allocate(8192);
//        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        int read = rafchannel.read(buffer);   //buffer.put()
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);

        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print(((char) buffer.get(i)));
        }
    }


    /**
     * 测试ByteBuffer的使用: 本质上还是一个字节数组
     */
    @Test
    public void whatByteBuffer() {
        // 使用堆内内存的ByteBuffer
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 使用直接内存(堆外内存)的ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);


        System.out.println("postition: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("mark: " + buffer);

        buffer.put("123".getBytes());

        System.out.println("-------------put:123......");
        System.out.println("mark: " + buffer);

        buffer.flip();   //读写交替

        System.out.println("-------------flip......");
        System.out.println("mark: " + buffer);

        buffer.get();

        System.out.println("-------------get......");
        System.out.println("mark: " + buffer);

        buffer.compact();

        System.out.println("-------------compact......");
        System.out.println("mark: " + buffer);

        buffer.clear();

        System.out.println("-------------clear......");
        System.out.println("mark: " + buffer);

    }


}
