package com.corejava.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionExample3 {

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = MyClassWithNestedGeneric.class.getDeclaredMethod("myMethod");
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType(); // 获取原始类型（例如 List）
            System.out.println(rawType);
            Type[] typeArguments = parameterizedType.getActualTypeArguments(); // 获取类型参数（例如 String）
            for (Type typeArgument : typeArguments) {
                System.out.println(typeArgument);
            }
        }
    }

    static class MyClassWithNestedGeneric {
        public List<String> myMethod(){
            return List.of();
        }
    }
}
