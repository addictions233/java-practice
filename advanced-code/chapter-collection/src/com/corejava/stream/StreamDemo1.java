package com.corejava.stream;
/**
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @author one
 * 获取steam流的方式: 对单列集合,双列集合,数组,多个同类型元素转换为Stream流的方法
 * 只有四种情况可以创建steam流:
 *          1, 单列集合(list,set),  集合对象.steam()
 *          2, 双列集合(map不能直接转换为steam流,需要先转换为单列集合,再转换为steam流) 集合对象.keySet().stream()  或者 集合对象.entrySet().stream()
 *          3, 数组  Arrays.stream(arr)
 *          4, 同中类型的多个数据  Streams.of("aa","bb","cc")
 */
public class StreamDemo1 {
    public static void main(String[] args) {
//        method1();
        mehtod2();
//        mehod3();
//        method4();
    }

    /**
     * 使用Streams.of()方法同类型的多个数据
     */
    private static void method4() {
        Stream.of("zhangsan","lisi","wangwu","zhaoliu").forEach(s -> System.out.println(s));
    }

    /**
     * 使用 Arrays.stream(arr) 将数组转换为stream流
     */
    private static void mehod3() {
        String[] arr = {"zhangsan","lisi","wangwu","zhaoliu"};
        Arrays.stream(arr).forEach(s -> System.out.println(s));
        Arrays.stream(arr).filter(str -> str.length()>= 6).forEach(System.out::println);
    }

    /**
     * 将map集合转为为steam流: 双列集合不能直接转换为stream流,需要先转换为单列集合
     * lambda表达式的简写规则:
     *      1,参数的数据类型可以省略,如果有多个参数,省略时需要全部省略
     *      2, 如果方法只有一个形参,可以省略参数的小括号
     *      3, 如果方法体中只有一句代码,可以省略大括号
     *      4, 如果方法体中的唯一句语句且为返回语句,则可以省略大括号和return
     */
    private static void mehtod2() {
        HashMap<String,String> hm = new HashMap<>();
        hm.put("heima001","zhangsan");
        hm.put("heima002","lisit");
        hm.put("heima003","wangwu");
        hm.put("heima004","zhaoliu");
        // map集合所有的key是一个set集合
        hm.keySet().stream().forEach(s-> System.out.println("key:"+s+",value:"+hm.get(s)));
        // map集合所有的value是一个Collection单列集合
        hm.values().stream().forEach(s -> System.out.println(s.toUpperCase()));
        System.out.println(hm.values());
        // map的entry是一个key=value形式的单列集合
        hm.entrySet().stream().forEach(s-> System.out.println(s));
    }

    /**
     * 将list集合转换为steam流
     */
    private static void method1() {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("zhangsan");
        list1.add("lisi");
        list1.add("wangwu");
        list1.add("zhaoliu");
        list1.stream().forEach(s-> System.out.println(s));
    }
}
