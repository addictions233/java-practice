package com.corejava.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Outer<T> {
    // O<T> 代表一个名为 O 的泛型类型，其类型参数为 T，
    // 例如 O 可以是一个泛型类或接口，T 是占位符表示任意引用类型；
    // O<T>.I<S> 则表示 O<T> 的成员类型 I，它接受另一个类型参数 S，
    // 这里的 I 可以是内部类、内部接口或静态成员，且其泛型参数 S 与外部的 T 独立，
    //允许在实例化时分别指定具体类型。‌
    class Inner<S> {

        public S getValue() {
            return null;
        }
    }



    public static void main(String[] args) {
        Outer<String> outer = new Outer<>();
        Outer<String>.Inner<Integer> inner = outer.new Inner<Integer>();

        // 获取 Inner 类的泛型超类

    }
}