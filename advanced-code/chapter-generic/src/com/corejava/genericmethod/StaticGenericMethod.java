package com.corejava.genericmethod;

/**
 * 测试静态泛型方法
 * 静态泛型方法的典型应用场景是在工具类中定义一些通用的方法，这些方法可以在不同的类型上进行操作。
 */
public class StaticGenericMethod {

    public static <T> void printArray(T[] array){
        for (T t : array) {
            System.out.println(t);
        }
    }

    public static void main(String[] args) {
        // 静态泛型方法调用分两种：
        // 1. 直接调用，不指定类型参数, 编译器可以自动推断类型
        StaticGenericMethod.printArray(new Integer[]{1, 2, 3});

        // 2. 显示指定类型参数
        // Java 调用静态泛型方法时，可以通过在方法名前面添加尖括号和具体的类型来显式指定泛型，
        // 语法格式为 类名.<具体类型>方法名(参数)
        StaticGenericMethod.<String>printArray(new String[]{"a", "b", "c"});
    }

    /**
     * 静态泛型方法的核心在于其独立性, 它不能直接访问类的泛型参数, 因为静态方法与类的实例无关, 而泛型类的类型参数需要在实例化时指定。
     * 因此, 若需要在静态方法中使用泛型, 必须在方法自身声明类型参数, 而非依赖类级别的泛型, 以下定义是错误的
     * @param <T>
     */
    public static class Box<T> {

        /**
         * 因为泛型类中的泛型参数的实例化是在定义对象的时候指定的，而静态变量和静态方法不需要使用对象来调用。
         * 对象都没有创建，如何确定这个泛型参数是何种类型，所以当然是错误的。
         */
//        public static T one;  // 编译错误!

//        public static T getValue() { // 编译错误！不能直接使用类泛型T
//            return null;
//        }
    }
}



