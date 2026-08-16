package com.corejava.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 通过反射获取泛型类型
 */
public class ReflectionExample0 {

    public static void main(String[] args) {
        // MyClass 在编译时有泛型擦除, 并不能获取到String
        MyClass<String> myClass = new MyClass<String>();
        printType(myClass);

        System.out.println("-------------------------");

        // MyStrClass 是直接定义的String 泛型, 可以获取到String
        MyStrClass strClass = new MyStrClass();
        printType(strClass);

    }

    private static void printType(MyClass<String> myClass) {
        // 接口是多继承的，所以可以获取到多个泛型类型
        Type[] types = myClass.getClass().getGenericInterfaces();
        for (Type type : types) {
            System.out.println(type);
            if (type instanceof ParameterizedType parameterizedType) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments(); // 获取泛型参数
                for (Type t : typeArguments) {
                    System.out.println(t); // 输出泛型参数类型
                }
            }
        }

        Type supperClass = myClass.getClass().getGenericSuperclass();
        if (supperClass instanceof ParameterizedType type) {
            Type[] actualTypeArguments = type.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument);
            }
        }
    }


}
