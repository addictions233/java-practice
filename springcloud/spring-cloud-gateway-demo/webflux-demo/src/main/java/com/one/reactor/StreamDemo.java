package com.one.reactor;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stream所有数据和操作被组合成流管道流管道组成
 */
public class StreamDemo {

    public static void main(String[] args) {
        Stream<Integer> stream = Stream.of(10, 20, 30, 40, 50);
        List<Integer> list = stream
                .filter(i -> i > 40) // 过滤:进行过滤
                .map(i -> i + 100) // 映射:一对一的映射
                .flatMap(i -> Stream.of(i * 2, i * 3, i * 4)) // 散列:一对多, 将一个元素转换为一个stream流
                .toList();
        System.out.println(list);
    }
}
