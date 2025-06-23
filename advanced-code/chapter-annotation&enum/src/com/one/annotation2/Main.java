package com.one.annotation2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 测试类: 获取方法上的注解
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        // 获取类的字节码对象, 三种方式: 1, Class.forName()  2, UseMyAnno2.class  3, 对象.getClass()
        Class<UseMyAnno2> clazz = (Class<UseMyAnno2>)Class.forName("com.one.annotation2.UseMyAnno2");

        //创建对象
//        UseMyAnno2 useMyAnno2 = new UseMyAnno2();
//        UseMyAnno2 useMyAnno2 = clazz.newInstance();
        Constructor<UseMyAnno2> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        UseMyAnno2 useMyAnno2 = constructor.newInstance();

        //获取该字节码对象的所有方法
        Method[] methods = clazz.getDeclaredMethods();

        // 遍历方法数组
        for (Method method : methods) {
            // 在使用方法之前先暴力反射
            method.setAccessible(true);

            //判断该方法是否添加了注解
            if(method.isAnnotationPresent(MyAnno2.class)){
                //启动添加了注解的方法
                method.invoke(useMyAnno2,1,"ZHANGSAN");
            }
        }
    }
}
