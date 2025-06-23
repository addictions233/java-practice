package com.one.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * 键盘录入一个类,打印这个类完整的构造方法的方法声明
 */
public class ReflectDemo2 {
    public static void main(String[] args) throws ClassNotFoundException {
        String name;
        if(args.length>0){  //主方法中的字符串数组参数
            name = args[0];
        } else{  //从键盘录入类名
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入一个类名:");
            name = sc.next();
        }
        //通过全类名获取该类的字节码对象
        Class clazz = Class.forName(name);

        //获取类的访问修饰符
        String modifier = Modifier.toString(clazz.getModifiers());
        if(modifier.length()>0) System.out.print(modifier+" ");
        System.out.println(name+"{");
        printField(clazz);  //打印该类所有字段
        printConstructor(clazz);  // 打印该类所有构造方法
        printMethod(clazz);  // 打印该类所有成员方法
        System.out.println("}");
    }
    /**
     * 定义一个方法,该方法用来打印传入的字节码对象的所有字段
     * 字段的标准格式:  访问修饰符  数据类型  变量名
     */
    public static void printField(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
           //先获取访问修饰符
            String modifier = Modifier.toString(field.getModifiers());

            //获取数据类型
            Class type = field.getType();  //返回数据类型的类的字节码对象
            String typeName = type.getName();

            //获取变量名
            String name = field.getName();

            //当访问修饰符为默认修饰符时, 这modifier就是一个空白串,不打印
            if(modifier.length()>0){
                System.out.println(modifier+" "+ typeName +" "+ name);
            } else{
                System.out.println(typeName + " " + name);
            }
        }
    }

    /**
     * 定义一个方法打印传入的字节码对象的所有构造方法
     * 构造方法打印格式: 访问修饰符 方法名(参数类型 参数名...)
     */
    public static void printConstructor(Class clazz){
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            StringBuilder sb = new StringBuilder();
            //获取构造方法的访问修饰符
            String modifier = Modifier.toString(constructor.getModifiers());

            //获取构造方法的方法名
            String name = constructor.getName();
            //如果访问修饰符不为默认修饰符
            if(modifier.length()>0) sb.append(modifier).append(" ");

            sb.append(name).append("(");
            //获取构造方法的参数列表
            Class[] parameterTypes = constructor.getParameterTypes();

            for (int i = 0; i < parameterTypes.length; i++) {
               //拼接打印
                if(i ==0){
                    sb.append(parameterTypes[0].getName());
                } else{
                    sb.append(",").append(parameterTypes[i].getName());
                }
            }
            sb.append(")");
            System.out.println(sb.toString());
        }

    }

    /**
     * 定义一个方法用来打印传入的字节码对象的全部成员方法
     * 成员方法格式: 访问修饰符 返回值类型  方法名(参数类型 参数名)
     */
    public static void printMethod(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            StringBuilder sb = new StringBuilder();
            //获取访问修饰符
            String modifier = Modifier.toString(method.getModifiers());

            //获取返回值类型
            Class returnType = method.getReturnType();
            String returnTypeName = returnType.getName();

            //获取方法名
            String name = method.getName();

            //拼接
            if(modifier.length()>0) sb.append(modifier).append(" ");
            sb.append(returnTypeName).append(" ").append(name).append("(");

            //获取参数列表
            Class[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if(i == 0){
                    sb.append(parameterTypes[0].getName());
                } else{
                    sb.append(",").append(parameterTypes[i].getName());
                }
            }
            sb.append(")");
            System.out.println(sb.toString());
        }
    }
}
