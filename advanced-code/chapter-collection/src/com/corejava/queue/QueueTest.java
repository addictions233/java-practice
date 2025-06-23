package com.corejava.queue;

import java.util.Iterator;

/**
 *  测试自己写的 CircularArrayQueue类, 用数组实现队列Queue数据结构
 */
public class QueueTest {
    public static void main(String[] args) {
        CircularArrayQueue<String> queue = new CircularArrayQueue<>();
//        queue.remove();
        queue.add("张三");
        queue.add("李四");
        queue.add("王五");
        System.out.println(queue.size());
        Iterator<String> iterator = queue.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        queue.add("赵六");
        System.out.println(queue.size());
    }

}
