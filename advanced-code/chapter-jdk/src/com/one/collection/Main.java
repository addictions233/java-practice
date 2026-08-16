package com.one.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // JDK9 创建不可变集合
        List<String> lists = List.of("zhangsan", "lisi", "wangwu");
        System.out.println(lists);


        Set<Integer> sets = Set.of(10, 20, 30);
        System.out.println(sets);

        Map<String, Integer> maps = Map.of("zhangsan", 23, "lisi", 24, "wangwu", 25);
        System.out.println(maps);

        // Map.ofEntries 创建大集合
        Map<String, Integer> largeMap = Map.ofEntries(Map.entry("Java", 1995),
                Map.entry("Kotlin", 2011),
                Map.entry("Scala", 2004),
                Map.entry("Groovy", 2003)
                // ...可以有更多entries
        );
        System.out.println(largeMap);
    }
}
