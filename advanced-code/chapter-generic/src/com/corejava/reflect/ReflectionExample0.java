package com.corejava.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionExample0 {

    public static void main(String[] args) {
        Type[] types = MyClass.class.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                Type[] typeArguments = ptype.getActualTypeArguments(); // 获取泛型参数
                for (Type t : typeArguments) {
                    System.out.println(t); // 输出泛型参数类型
                }
            }
        }
    }

    // 获取接口上的泛型类型
    class MyClass implements MyInterface<String> {
    }
}
