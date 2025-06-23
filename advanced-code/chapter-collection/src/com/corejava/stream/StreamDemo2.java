package com.corejava.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 方法引用,调方法,创建对象,调用静态方法
 * @author one
 */
public class StreamDemo2 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三丰");
        list.add("张无忌");
        list.add("赵本山");
        list.add("鲁智深");
        list.add("赵子龙");

        list.stream().filter(string->string.startsWith("赵")).forEach
                (string-> System.out.println(string));

        ArrayList list2 = new ArrayList();
        list2.add("张三");
        list2.add(100);

        /**
         * <R> Stream<R> map(Function<? super T, ? extends R> mapper)
         * Function<? supper T, ? extends R> mapper = new  Function<String,Object> ();
         */
        List<Object> collect = list.stream().map(new Function<String, Object>() {
            @Override
            public String apply(String s) {
                return s.substring(0, 2);
            }
        }).collect(Collectors.toList());
        System.out.println(collect);

    }
}
