package com.one.constructor;

import com.one.reflect.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class ConstructorDemo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //通过全类名获取字节码对象
        Class<Student> clazz = (Class<Student>) Class.forName("com.one.reflect.Student");

        //获取私有的有参构造方法
        Constructor<Student> constructor = clazz.getDeclaredConstructor(String.class, int.class);

        //将私有构造方法设置为可访问
        constructor.setAccessible(true);

        //通过构造方法创建对象
        Student student = constructor.newInstance("zhangsan", 23);
        System.out.println("student对象: = " + student);

        // 获取构造器的参数
        Parameter[] parameters = constructor.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println("构造方法参数:" + parameter);
        }

    }
}
