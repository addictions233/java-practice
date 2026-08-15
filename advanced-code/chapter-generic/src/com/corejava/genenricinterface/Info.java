package com.corejava.genenricinterface;

/**
 * 定义泛型接口
 * @param <T> 泛型
 */
public interface Info<T> {
    /**
     * 定义抽象方法, 抽象方法的返回值就是泛型类型
     * @return 泛型类型
     */
    T getVar();
}
