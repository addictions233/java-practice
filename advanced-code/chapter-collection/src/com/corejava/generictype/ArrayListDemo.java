package com.corejava.generictype;
/**
 * 泛型的使用
 *      泛型约束: 只在编译时期进行检查,运行时就不再检查了
 *      编译期将.java的源码编译成.class字节码时就将泛型擦除了,字节码阶段泛型就已经别抛弃了
 *      通过反射创建对象或者对属性赋值就没有泛型约束
 */

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ArrayListDemo {
    public static void main(String[] args) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        System.out.println(list);

        Class clazz =  list.getClass();
        Method add = clazz.getDeclaredMethod("add",Object.class);
        add.setAccessible(true);
        add.invoke(list,999);
        add.invoke(list,100);
        System.out.println(list);
    }

}
