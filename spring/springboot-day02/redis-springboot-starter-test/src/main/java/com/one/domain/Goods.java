package com.one.domain;

/**
 * @ClassName: Goods
 * @Description: TODO
 * @Author: one
 * @Date: 2022/04/06
 */
public class Goods {
    String name;

    public Goods(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                '}';
    }
}
