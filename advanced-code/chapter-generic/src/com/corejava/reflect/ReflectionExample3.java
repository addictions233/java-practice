package com.corejava.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionExample3 {

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = MyCalssWithNestedGeneric.class.getDeclaredMethod("myMethod");
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type rawType = parameterizedType.getRawType(); // 获取原始类型（例如 List）
            System.out.println(rawType);
            Type[] typeArguments = parameterizedType.getActualTypeArguments(); // 获取类型参数（例如 String）
            for (Type typeArgument : typeArguments) {
                System.out.println(typeArgument);
            }
        }
    }

    class MyCalssWithNestedGeneric {
        public List<String> myMethod(){
            return null;
        }
    }
}
