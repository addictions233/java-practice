package com.corejava.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionExample2 {

    public static void main(String[] args) {
        try {
            Method method = MyClass.class.getDeclaredMethod("myMethod", Object.class);
            // 获取方法的返回值类型
            Type returnType = method.getGenericReturnType();
            if (returnType instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) returnType;
                Type[] typeArguments = ptype.getActualTypeArguments(); // 获取泛型参数
                for (Type t : typeArguments) {
                    System.out.println(t); // 输出泛型参数类型
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    class MyClass {
        // 获取方法上的泛型类型
        public <T> List<T> myMethod(T t){
            System.out.println(t);
            return null;
        }
    }
}
