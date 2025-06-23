package com.one.fileoutputstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName: FileInputStreamDemo2
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/25
 */
public class FileInputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("My_Day12\\ddd.txt");
        byte[] bys = new byte[5];
        int read = fis.read(bys);
        System.out.println(Arrays.toString(bys));
        System.out.println(read);

        int read1 = fis.read(bys);
        System.out.println(Arrays.toString(bys));
        System.out.println(read1);
    }
}
