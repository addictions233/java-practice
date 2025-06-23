package com.corejava.customexception;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *  在测试类中使用自己定义的
 */
public class FileFormatExceptionTest {
    public static void main(String[] args) throws IOException {
       BufferedReader bf = new BufferedReader(new FileReader("com.corejava.picture.png"));
       byte[] array = new byte[1024];
        int read = bf.read();

    }

}
