package com.corejava.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: StreamTest2
 * @Description: TODO
 * @Author: one
 * @Date: 2021/06/02
 */
public class StreamTest2 {
    public static void main(String[] args) {
        ArrayList<String> man = new ArrayList<>();
        man.add("张三丰");
        man.add("谢逊");
        man.add("杨逍");
        man.add("白眉鹰王");
        man.add("张无忌");
        man.add("张翠山");

        ArrayList<String> women = new ArrayList<>(6);
        women.add("杨紫");
        women.add("杨幂");
        women.add("刘亦菲");
        women.add("刘诗诗");
        women.add("杨颖");
        women.add("郭采洁");

        List<String> list1 = man.stream().filter(name -> name.length() == 3).limit(2).collect(Collectors.toList());
        System.out.println(list1);
        List<String> list2 = women.stream().filter(name -> name.startsWith("杨")).skip(1).collect(Collectors.toList());
        System.out.println(list2);
        List<String> collect = Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList());
        System.out.println(collect);
    }
}
