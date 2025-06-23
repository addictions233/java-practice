package com.corejava.interfacedemo.defaultmethod;

public interface Interface1 {
    public default void method(){
        System.out.println("调用接口1中的默认方法");
    }
}
