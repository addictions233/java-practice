package com.one.reflect;
/**
 * 定义该类是为了测试反射类: Class,Constructor,Method,Field的各个方法
 */

import java.lang.reflect.Field;

public class ReflectDemo1 {
    public static void main(String[] args) {
        Class clazz = Student.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
            System.out.println(field.getName());
        }
    }
}
