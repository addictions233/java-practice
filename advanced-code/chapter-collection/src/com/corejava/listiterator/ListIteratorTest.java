package com.corejava.listiterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *  链表迭代器ListIterator相比较于迭代器Iterator来说,在遍历集合可以用它自己的add(E element)方法向它的集合中修改元素
 *  同时链表迭代器ListIterator从它的父类Iterator中继承了remove()方法,可以删除它的集合中的元素
 */
public class ListIteratorTest {
    public static void main(String[] args) {
        //创建两个链表对象
        LinkedList<String> aList = new LinkedList<>();
        aList.add("Amy");
        aList.add("Carl");
        aList.add("Erica");

        LinkedList<String> bList = new LinkedList<>();
        bList.add("Bob");
        bList.add("Doug");
        bList.add("Frances");
        bList.add("Gloria");

        //将链表bList中的元素依次插入到链表aList中
        ListIterator<String> aListIterator = aList.listIterator();
        Iterator<String> bIterator = bList.iterator();
        while(bIterator.hasNext()){
            if(aListIterator.hasNext()) aListIterator.next(); // Amy,Bob,Carl,Doug,Erica,Frances,Gloria
            aListIterator.add(bIterator.next());
        }
        System.out.println("aList = " + aList);  // 输出: aList = [Amy, Bob, Carl, Doug, Erica, Frances, Gloria]

        //遍历bList链表,将该链表中的元素隔一个元素进行删除
        Iterator<String> bIt = bList.iterator();
        while(bIt.hasNext()){
            bIt.next();
            if(bIt.hasNext())
                bIt.next();
                bIt.remove();  // 删除了Doug,Gloria
        }
        System.out.println("bList = "+ bList);  //输出: bList = [Bob, Frances]

        // 将链表aList中的与链表bList重合的元素删除
        aList.removeAll(bList);  //删除了 Bob,Frances
        System.out.println("aList = " + aList); // aList = [Amy, Carl, Doug, Erica, Gloria]
    }


}
