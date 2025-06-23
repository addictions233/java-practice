package com.corejava.iterator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  iterator迭代器中的 remove()方法, 删除上一次调用 next()方法返回的元素
 *      注意: 迭代器对象调用remove()方法之前必须先调用next()方法获得上次遍历的元素,否则就没法删除
 */
public class IteratorDemo1 {
    public static void main(String[] args) {
        ArrayList<String> aList = new ArrayList<>();
        aList.add("hello");
        aList.add("world");
        aList.add("你好");
        System.out.println("aList = " + aList);  // 输出: aList = [hello, world, 你好]
        Iterator<String> iterator = aList.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
//            aList.remove(next);   // ConcurrentModificationException 不能用动态数组自己删除元素
            iterator.remove();  // 可以用迭代器中的remove()方法删除动态数组中的元素
        }
        System.out.println("aList = " + aList);  // 输出 aList = []
    }
}
