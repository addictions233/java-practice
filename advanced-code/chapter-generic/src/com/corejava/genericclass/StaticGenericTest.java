package com.corejava.genericclass;

public class StaticGenericTest {

    public static <T> void printArray(T[] array){
        for (T t : array) {
            System.out.println(t);
        }
    }

    public static void main(String[] args) {
        // 静态泛型方法调用分两种：
        // 1. 直接调用，不指定类型参数
        StaticGenericTest.printArray(new Integer[]{1, 2, 3});
        // 2. 显示指定类型参数
        StaticGenericTest.<String>printArray(new String[]{"a", "b", "c"});
    }
}
