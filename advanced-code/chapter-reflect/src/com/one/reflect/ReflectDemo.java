package com.one.reflect;

/**
 * 获取字节码对象的四种方式:
 *   1.源文件阶段获取字节码对象, 调用Class#forname静态方法
 *   2.字节码文件阶段获取字节码对象 , 使用内置Class属性
 *   3.类加载阶段获取字节码对象, 调用ClassLoader#loadClass方法
 *   4.对象阶段获取字节码对象, 调用#getClass()方法获取
 */
public class ReflectDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        Student stu = new Student();

        //源文件阶段获取字节码对象
        Class<?> clazz1 = Class.forName("com.one.reflect.Student");

        //字节码文件阶段获取字节码对象  类名.class  类名调用属性, 内置Class属性
        Class<?> clazz2 = Student.class;

        // 类加载阶段获取字节码对象
        ClassLoader classLoader = ReflectDemo.class.getClassLoader();
        Class<?> clazz3 = classLoader.loadClass("com.one.reflect.Student");

        //对象阶段获取字节码对象 调用对象#getClass方法获取字节码对象
        Class<?> clazz4 = stu.getClass();



        //一个类有且只有一个字节码对象
        System.out.println(clazz1 == clazz2); //true
        System.out.println(clazz1 == clazz3); //true
        System.out.println(clazz1 == clazz4); //true

        // 获取运行时类的接口
        Class<?>[] interfaces = clazz1.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println("类实现的接口:" + anInterface);
        }

        // 获取类的父类的字节码对象
        Class<?> superclass = clazz1.getSuperclass();
        System.out.println("父类的字节码对象:" + superclass);

        // 获取类的包名
        Package aPackage = clazz1.getPackage();
        System.out.println(aPackage);
        System.out.println("类的包名:" + aPackage.getName());

    }
}
