package com.corejava.genericclass;

/**
 * 测试静态泛型方法
 * 静态泛型方法的典型应用场景是在工具类中定义一些通用的方法，这些方法可以在不同的类型上进行操作。
 */
public class StaticGenericTest {

    public static <T> void printArray(T[] array){
        for (T t : array) {
            System.out.println(t);
        }
    }

    public static void main(String[] args) {
        // 静态泛型方法调用分两种：
        // 1. 直接调用，不指定类型参数
        StaticGenericTest.printArray(new Integer[]{1, 2, 3});
        // 2. 显示指定类型参数
        StaticGenericTest.<String>printArray(new String[]{"a", "b", "c"});
    }
}


/**
 * 静态泛型方法的核心在于其独立性, 它不能直接访问类的泛型参数, 因为静态方法与类的实例无关, 而泛型类的类型参数需要在实例化时指定。
 * 因此, 若需要在静态方法中使用泛型, 必须在方法自身声明类型参数, 而非依赖类级别的泛型, 以下定义是错误的
 * @param <T>
 */
//public class Box<T> {
//    public static T getValue() { // 编译错误！不能直接使用类泛型T
//        return null;
//    }
//}
