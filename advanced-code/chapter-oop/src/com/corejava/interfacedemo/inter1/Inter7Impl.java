package com.corejava.interfacedemo.inter1;

/**
 * @author one
 * 接口的实现类受到接口的约束,必须重写它的抽象方法
 */
public class Inter7Impl implements Inter7 {
    @Override
    public void show() {
        System.out.println("调用了show方法");

    }

    @Override
    public int function() {
        System.out.println("调用function方法");
        return 100;
    }
}
