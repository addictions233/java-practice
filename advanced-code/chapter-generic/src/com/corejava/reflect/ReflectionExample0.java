package com.corejava.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 通过反射获取泛型类型
 */
public class ReflectionExample0 {

    public static void main(String[] args) {
        // 接口是多继承的，所以可以获取到多个泛型类型
        Type[] types = MyClass.class.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] typeArguments = parameterizedType.getActualTypeArguments(); // 获取泛型参数
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
