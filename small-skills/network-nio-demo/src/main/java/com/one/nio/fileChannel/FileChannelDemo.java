package com.one.nio.fileChannel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author one
 * @description 比较mmp的零拷贝和传统IO的效率差别
 * @date 2024-7-4
 */
public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 200000000; i++) {
            buffer.append('a');
        }

        byte[] bytes = buffer.toString().getBytes(StandardCharsets.UTF_8);
        long startTime = System.currentTimeMillis();
        writeByStream(bytes, new File("nio-demo\\stream.txt"));
        long endTime = System.currentTimeMillis();
        System.out.println("传统IO拷贝时间:" + (endTime - startTime));
        startTime = endTime;
        writeByChannel(bytes, new File("nio-demo\\channel.txt"));
        endTime = System.currentTimeMillis();
        System.out.println("零拷贝文件耗时:" + (endTime - startTime));
    }

    private static void writeByChannel(byte[] bytes, File file) throws IOException {
        if (file.exists()) {
            file.createNewFile();
        }
        // rw是读写模式,既可以读, 也可以写
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
        // 文件是写到操作系统的缓存中, 操作系统会自动进行刷盘
        mappedByteBuffer.put(bytes);
        fileChannel.close();
        randomAccessFile.close();
    }

    private static void writeByStream(byte[] bytes, File file) throws IOException {
        if (file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
    }
}
