package com.corejava.reflect;

/**
 * 定义泛型内部类
 * @param <T> 外部类的泛型
 */
public class Outer<T> {

    // Outer<T> 代表一个名为 Outer 的泛型类型，其类型参数为 T，
    // 例如 Outer 可以是一个泛型类或接口，T 是占位符表示任意引用类型；
    // Outer<T>.Inner<S> 则表示 Outer<T> 的成员类型 Inner，它接受另一个类型参数 S，
    // 这里的 Inner 可以是内部类、内部接口或静态成员，且其泛型参数 S 与外部的 T 独立，
    // 允许在实例化时分别指定具体类型。‌
    public class Inner<S> {

        public S getValue() {
            return null;
        }
    }


    public static void main(String[] args) {
        // 外部类的泛型是String类型
        Outer<String> outer = new Outer<>();

        // 普通内部类必须通过 outerInstance.new Inner() 创建，
        // 而静态内部类可以直接用 Outer.StaticInner 创建。
        // 内部类的泛型是Integer类型
        Outer<String>.Inner<Integer> inner = outer.new Inner<Integer>();

        // 获取 Inner 类的泛型超类

    }
}