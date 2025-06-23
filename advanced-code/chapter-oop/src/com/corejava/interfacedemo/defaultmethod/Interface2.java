package com.corejava.interfacedemo.defaultmethod;

public interface Interface2 {
    public default void method(){
        System.out.println("调用接口2中的默认方法");
    }
}
