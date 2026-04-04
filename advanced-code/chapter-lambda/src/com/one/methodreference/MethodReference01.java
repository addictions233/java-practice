package com.one.methodreference;


import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * 方法引用是对lambda表达式的一种更加简便的写法
 *
 * 方法引用则是隐式借用已经存在的方法作为现成的执行逻辑，而不必在lambda表达式中显示调用该方法，或者重写这一部分代码。
 * 同理，构造器引用则是隐式借用某个类的构造函数创建对象的执行逻辑。
 *
 * 方法引用的核心思想类似于“指针”，它不直接执行方法，而是指向一个已存在的方法。
 * 这类似于现实中的“快捷方式”——用户不需要重复编写代码，只需通过引用直接调用已有功能。
 */
public class MethodReference01 {

    public static void main(String[] args) {

        // 我们需要简单打印一个字符串，可以按照如下方式进行显示调用引用的println()方法
        MethodReference01.accept("hello method reference",  t -> {
            System.out.println(t);
        });

        // 或者按照下面这种写法
        MethodReference01.accept("hello method reference", t -> {
            PrintStream out = System.out;
            synchronized (out) {
                out.print(t);
                out.print("\n\b");
            }
        });

        // 当我们发现 方法java.util.function.Consumer.accept(T t) 和 方法java.io.PrintStream.print(java.lang.String)
        // 具有相同的形参数量和返回值类型时, 我们就可以直接引用PrintStream.print(java.lang.String) 来替代 Consumer.accept(T t)方法
        // 相当于将System.out.println(String)方法等价替换了accept(T)方法，并使用了现有的执行逻辑。
        MethodReference01.accept("hello method reference", System.out::println);
    }

    /**
     * 定义一个静态方法, 它接收一个泛型参数t, 然后使用Consumer函数式接口进行动态处理
     * @param t 泛型参数
     * @param consumer Consumer函数式接口
     * @param <T> 泛型
     */
    public static <T> void accept(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
