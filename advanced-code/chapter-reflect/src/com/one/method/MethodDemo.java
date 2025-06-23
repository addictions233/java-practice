package com.one.method;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class MethodDemo {
    public static void main(String[] args) throws Exception{

        Class clazz = Class.forName("com.one.method.Dog");

        // 获取该类及其父类所有的public修饰的方法
        Method[] methods1 = clazz.getMethods();
        for (Method method : methods1) {
            System.out.println(method);
        }

        System.out.println("---------------------");

        // 获取方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
        //创建Dog对象
        Object dog = clazz.newInstance();

        //通过反射获取特定方法
        Method function = clazz.getDeclaredMethod("function");
        Object invoke = function.invoke(dog);
        System.out.println(invoke);

        // 通过参数指定获取某个方法
        Method method = clazz.getDeclaredMethod("eat", String.class);
        method.invoke(dog,"肉");

        System.out.println("-----------------------------------");
        // 获取方法名字
        System.out.println("方法名字:" + method.getName());
        // 获取方法参数
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println("方法参数:" + parameter);
        }
        // 获取返回值类
        Class<?> returnType = method.getReturnType();
        System.out.println("方法返回值类型:" + returnType);
        // 获取方法修饰符
        int modifiers = method.getModifiers();
        System.out.println("方法修饰符:" + Modifier.toString(modifiers));

        // 获取方法异常
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        for (Class<?> exceptionType : exceptionTypes) {
            System.out.println("方法异常:" + exceptionType);
        }

    }
}
