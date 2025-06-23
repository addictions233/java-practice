package com.one.iostream;

import java.io.*;

/**
 * @ClassName: ByteArrayOutputStream
 * @Description: BufferInputStream重写了父类FilterInputStream的mark和reset方法，其有支持 mark 和 reset 方法的能力。
 * 而FileInputStream则没有重写父类InputStream的这两个方法，其不具有mark和reset方法的能力。
 * @Author: one
 * @Date: 2021/12/25
 */
public class ByteArrayOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("xxxx");

//        boolean flag = inputStream.markSupported();
//        System.out.println(flag);

        // 创建字节数组输出流对象
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 创建 4kb的缓存数组
        byte[] bytes = new byte[4*1024];

        int length;
        while((length = inputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes,0, length);
            byteArrayOutputStream.flush();
        }

        // 赋值输入流
        InputStream copyInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

    }
}
