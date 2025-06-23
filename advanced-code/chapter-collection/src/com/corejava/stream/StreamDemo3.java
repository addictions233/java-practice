package com.corejava.stream;

import java.util.stream.Stream;

/**
 * @author one
 * stream流的出厂方法:
 *      1, count()方法
 *      2, forEach()方法
 *      3, collect()方法  将stream流中的对象重新保存在集合中
 */
public class StreamDemo3 {
    public static void main(String[] args) {
        Stream<String> stream = Stream.of("赵云", "赵子龙", "古力娜扎", "爱新觉罗屿", "爱新觉罗溥仪");
        stream.filter(string->string.length()>3).forEach(System.out::println);
    }
}
