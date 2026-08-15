package com.corejava.genenricinterface;

/**
 * 定义泛型接口的子类
 * @param <T> 泛型类型
 */
public class InfoImpl<T> implements Info<T> {

    private T var;

    public InfoImpl(T var) {
        this.var = var;
    }

    @Override
    public T getVar() {
        return this.var;
    }
}
