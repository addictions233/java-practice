package com.corejava.capturetongpeifu;

/**
 *  Pair<T>类中存放两个T类型的成员变量,并提供 getter和setter方法
 * @param <T>
 */
public class Pair<T>{
    private T first;
    private T second;

    public Pair() {
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }
    public T getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }


}