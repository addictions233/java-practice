package com.corejava.lambda.functionalinterface;

import java.util.function.Consumer;

/**
 * @ClassName: ConsumerTest
 * @Description: Consumer接口: void accept(T t); 该方法就是让用户来给定该消费者如何消费传入的数据
 *      用法: 接受一个对象，不返回任何值, 用户数据的消费
 * @Author: one
 * @Date: 2022/03/18
 */
public class ConsumerTest {
    public static void main(String[] args) {
        // 将abc改成倒序,并打印在控制台
        method("abc", (str) -> {
            char[] chars = str.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (int i = chars.length - 1; i >= 0; i--) {
                sb.append(chars[i]);
            }
            System.out.println(sb);
        });
    }

    private static void method(String str, Consumer<String> consumer) {
        consumer.accept(str);
    }
}
