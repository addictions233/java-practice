package com.corejava.genericclass;

/**
 * 定义泛型类型
 * @param <T> 此处可以随便写标识符号，T是type的简称
 */
public class Pair<T> {

    /**
     * first的类型由T指定, 即由外部指定
     */
    private T first;

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    private T second;

    public Pair() {
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * 返回值的类型也由外部指定
     * @return 泛型
     */
    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}