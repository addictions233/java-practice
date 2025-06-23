package com.corejava.stream;

import java.util.ArrayList;

/**
 *  解决一个问题: 怎么将Stream流中的元素存储到 List,Set,Map等集合当中?
 */
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  1,Stream流中的collect()方法只管获取到流中的数据,不负责创建容器,也不负责向容器中添加数据,方法如下:
 *       <R,A> R collect​(Collector<? super T,A,R> collector)
 *         作用:使用 Collector对此流的元素执行 mutable reduction操作
 *  2,工具类 Collectors提供了具体的收集数据到容器的静态方法: toSet(),toList(),toMap(),分别将数据收集到Set,List,Map集合中
 *       将元素收集到 Map集合的toMap()方法如下:
 *          static <T,K,U> Collector<T,?,Map<K,U>> toMap​(Function<? super T,? extends K> keyMapper,
 *              Function<? super T,? extends U> valueMapper)
 *          // T是Stream流中元素的泛型类型变量, K是Map集合key的泛型类型变量,U是Map集合value的泛型类型变量
 *           作用:返回一个Collector类对象,它将元素累加到 Map其键和值是将提供的映射函数应用于输入元素的结果。
 *  3, interface Function<T,R>  T:函数输入的类型, R:函数返回的结果类型
 *       R apply​(T t)   //apply方法作用: 将T类型转换为R类型
 */
public class StreamDemo4 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三,23");
        list.add("李四,24");
        list.add("王五,25");
        /*
            需求: 将list集合中的元素按照姓名和年龄的方法存储到map集合中
         */
        HashMap<String,Integer> hm = new HashMap<>();
        for (String s : list) {
            String[] split = s.split(",");
            String name = split[0];
            String age = split[1];
            hm.put(name,Integer.parseInt(age));
        }
        System.out.println(hm);   // 输出: {李四=24, 张三=23, 王五=25}

        System.out.println("============");
        // 匿名内部类写法
//        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(new Function<String, String>() {
//            @Override
//            public String apply(String s) {  //这里的apply()方法是将Stream流中每个String元素处理之后加入到Map的key中
//                return s.split(",")[0];
//            }
//        }, new Function<String, Integer>() {
//
//            @Override
//            public Integer apply(String s) { //这里的apply()方法是将Stream流中的每个String元素处理之后加入到Map的value中
//                return Integer.parseInt(s.split(",")[1]);
//            }
//        }));
        // lambda表达式写法
        Map<String, Integer> collect = list.stream().collect(Collectors.
                toMap(s -> s.split(",")[0], s -> Integer.parseInt(s.split(",")[1])));

        System.out.println(collect);  // 输出: {李四=24, 张三=23, 王五=25}


    }
}
