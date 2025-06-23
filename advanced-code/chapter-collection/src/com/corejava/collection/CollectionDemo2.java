package com.corejava.collection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  用迭代器Iterator遍历集合
 */
public class CollectionDemo2 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        //增强for循环
        for(String name : list){
//            list.add("马七");  //  抛出 java.util.ConcurrentModificationException 并发修改异常
//            list.remove(1);
//            list.set(1,"马七");
            System.out.println(name);
        }
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            // 迭代器中是 next()方法让指针往下移动
            System.out.println(iterator.next());  // 抛出 java.util.NoSuchElementException
        }
    }
}
