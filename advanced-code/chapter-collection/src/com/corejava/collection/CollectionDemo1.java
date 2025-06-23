package com.corejava.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author one
 * Collection是所有单列集合的根接口, 这里的单列集合包括 List和Set
 * Map 是双列集合的根接口
 */
public class CollectionDemo1 {
    public static void main(String[] args) {
        Collection<String> col =  new ArrayList<String>();
        col.add("张三");
        col.add("李四");
        col.add("王五");
        col.add("赵六");
        //用迭代器遍历集合 : 迭代器遍历集合元素时,可以对元素属性进行修改
        Iterator<String> iterator = col.iterator();
        while(iterator.hasNext()){
//            ((ArrayList<String>) col).set(1,"马七");
            String next = iterator.next();
            System.out.println(next);
        }
        System.out.println("------------");

//        //用foreach遍历
//        for (String s : col) {
//            System.out.println(s);
//        }
    }
}
