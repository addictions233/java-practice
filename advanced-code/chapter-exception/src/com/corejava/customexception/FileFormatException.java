package com.corejava.customexception;

/**
 *  自定义一个异常类 FileFormatException
 */
public class FileFormatException extends Exception {
    //空参构造
    public FileFormatException() {
    }
    //有参构造
    public FileFormatException(String message) {
        super(message);
    }
}
