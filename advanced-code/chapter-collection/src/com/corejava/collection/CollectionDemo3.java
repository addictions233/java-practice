package com.corejava.collection;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author one
 *  线性表表迭代器 ListIterator, 注意和集合的迭代器 Iterator相区分
 *  ListIterator是一个功能更加强大的, 它继承于Iterator接口,只能用于各种List类型的访问
 */
public class CollectionDemo3 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");
        listIteratorTest(list);
    }

    /**
     * 使用链表迭代器: ListIterator: 只能用于各种List类型的访问,可以在遍历List集合中使用set()方法和add()方法
     * 对迭代器访问到元素进行修改或者在该元素前后添加元素
     * @param list
     */
    private static void listIteratorTest(ArrayList<String> list) {
        ListIterator<String> listIterator = list.listIterator();
        System.out.println("添加元素前的List集合:" + list);
        while(listIterator.hasNext()){
            // 链表迭代器解决了用迭代器遍历集合时不能对集合元素进行增删的问题
            // 使用add()方法在next()方法返回的元素之前或previous()方法返回的元素之后插入一个元素.
            listIterator.add("zhaoming");
            // 运行next()方法才是让迭代器指针往下移动的原因
            System.out.println(listIterator.next());

        }
        System.out.println("添加元素后的List集合:" + list);
    }
}
