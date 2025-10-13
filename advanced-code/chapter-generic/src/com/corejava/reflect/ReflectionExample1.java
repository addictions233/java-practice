package com.corejava.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionExample1 {
    public static void main(String[] args) {
        Type type = MyGenericClass.class.getGenericSuperclass(); // 获取父类的泛型类型
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            Type[] typeArguments = ptype.getActualTypeArguments(); // 获取泛型参数
            for (Type t : typeArguments) {
                System.out.println(t); // 输出泛型参数类型
            }
        }
    }

    // 获取类上的泛型类型
    class MyGenericClass<T> extends MyClass<String> {
    }
}


