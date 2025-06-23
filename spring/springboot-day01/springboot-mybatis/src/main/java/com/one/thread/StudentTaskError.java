package com.one.thread;

/**
 * @ClassName StudentTaskStatus
 * @Description 采用线程安全的同步方式
 * @Author one
 * @Date 2021/8/28 17:40
 * @Version 1.0
 */
public class StudentTaskError {
//    private volatile Boolean isError = false;
    private Boolean isError = false;

    public synchronized void setIsError() {
        isError = true;
    }

    /**
     *  用线程安全的方法, 保证 isError属性在多个线程中操作是可见的,且线程安全的
     *
     * @return isError
     */
    public synchronized boolean getIsError() {
        return isError;
    }
}
